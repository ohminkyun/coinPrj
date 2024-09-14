package com.project.coinPrj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import com.project.coinPrj.vo.DailyPriceVO;
import com.project.coinPrj.vo.MarketInfoVO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class UpbitApiList {

    // 업비트 마켓 리스트를 가져오는 함수
    public static MarketInfoVO getUpbitMarketList() throws Exception {
        /*if (coinName == null || coinName.isEmpty()) {
            throw new IllegalArgumentException("코인 이름이 null이거나 비어 있습니다.");
        }*/

        String url = "https://api.upbit.com/v1/market/all";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONArray marketList = new JSONArray(response.toString());

        int rowCount = 0;

        for (int i = 0; i < marketList.length(); i++) {
                JSONObject market = marketList.getJSONObject(i);
                String coinEngName = market.getString("english_name");
                String coinKorName = market.getString("korean_name");
                String marketCode  = market.getString("market");
                String marketInfo = "";
                String coinCode = "";

                // market 코드가 "KRW-BTC" 형식인지 확인하고 분리
                if (marketCode != null && marketCode.contains("-")) {
                    String[] parts = marketCode.split("-");
                    if (parts.length == 2) {
                        marketInfo = parts[0];   // "KRW" 저장
                        coinCode = parts[1]; // "BTC" 저장
                    }
                }
                
                //못가져옴 ㅠㅠ
            //String listingDate = getFirstDayOfCoin(marketCode);

                // 마켓 정보 저장
            databaseManager.saveMarketInfo(marketCode, marketInfo, coinCode, coinEngName, coinKorName);
            rowCount++;
            getPrevClosingPrice(marketCode);
        }
        System.out.println(rowCount + "건이 저장 되었습니다.");
        return null;
    }

    /*// 특정 코인의 상장일을 가져오는 함수
    public static String getFirstDayOfCoin(String marketCode) throws Exception {
        if (marketCode == null) return null;

        System.out.println("탔냐3");

        String url = "https://api.upbit.com/v1/candles/days?market=" + marketCode + "&count=1";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONArray jsonArray = new JSONArray(response.toString());

        String formattedDate = null;
        if (jsonArray.length() > 0) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String listingDate = jsonObject.getString("candle_date_time_utc");
            System.out.println("탔냐4" + listingDate);
            // candle_date_time_utc를 LocalDateTime으로 변환
            LocalDateTime dateTime = LocalDateTime.parse(listingDate);

            // YYYYMMDD 형태로 변환할 포맷터 설정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            // 날짜를 YYYYMMDD 형식의 문자열로 변환
            formattedDate = dateTime.format(formatter);

            //return new CoinListingVO(marketCode, marketInfo, coinCode, listingDate);  // VO 객체로 반환
            //databaseManager.updateListingInfo(marketCode, formattedDate);
        }
        return formattedDate;
    }*/

    /**
     * 주어진 마켓의 전일 종가를 반환하는 함수
     *
     * @param market 마켓 코드 (예: "KRW-BTC", "KRW-ETH")
     * @return 전일 종가
     * @throws Exception HTTP 요청 또는 JSON 처리 중 오류 발생 시
     */
    public static DailyPriceVO getPrevClosingPrice(String market) throws Exception {
        // 업비트 API URL 설정 (주어진 마켓에 대해)
        String apiURL = "https://api.upbit.com/v1/ticker?markets=" + market;
        URL url = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // GET 요청 설정
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        // 응답 코드 확인
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) { // 성공적인 응답
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // JSON 응답 처리
            JSONArray priceList = new JSONArray(response.toString());
            JSONObject jsonObject = priceList.getJSONObject(0);

            int rowCount = 0;

            // 현재 날짜 가져오기
            LocalDate today = LocalDate.now();

            // 날짜를 "YYYYMMDD" 형식으로 포맷
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            // 현재 날짜에서 하루를 뺀 어제 날짜를 구합니다.
            LocalDate yesterday = LocalDate.now().minusDays(1);

            for (int i = 0; i < priceList.length(); i++) {
                JSONObject dailyPrice = priceList.getJSONObject(i);

                String marketCode  = dailyPrice.getString("market");
                String trade_date = today.format(formatter);
                String marketInfo = "";
                String coinCode = "";
                Double opening_price = dailyPrice.getDouble("opening_price");
                Double high_price = dailyPrice.getDouble("high_price");
                Double low_price = dailyPrice.getDouble("low_price");
                String prev_date = yesterday.format(formatter);
                Double prev_closing_price = dailyPrice.getDouble("prev_closing_price");
                Double acc_trade_price = dailyPrice.getDouble("acc_trade_price");
                Double acc_trade_price_24h = dailyPrice.getDouble("acc_trade_price_24h");
                Double acc_trade_volume = dailyPrice.getDouble("acc_trade_volume");
                Double acc_trade_volume_24h = dailyPrice.getDouble("acc_trade_volume_24h");
                Double highest_52_week_price = dailyPrice.getDouble("highest_52_week_price");
                String highest_52_week_date = dailyPrice.getString("highest_52_week_date");
                // '-'를 제거한 문자열 생성
                highest_52_week_date = highest_52_week_date.replace("-", "");
                Double lowest_52_week_price = dailyPrice.getDouble("lowest_52_week_price");
                String lowest_52_week_date = dailyPrice.getString("lowest_52_week_date");
                // '-'를 제거한 문자열 생성
                lowest_52_week_date = lowest_52_week_date.replace("-", "");

                // market 코드가 "KRW-BTC" 형식인지 확인하고 분리
                if (marketCode != null && marketCode.contains("-")) {
                    String[] parts = marketCode.split("-");
                    if (parts.length == 2) {
                        marketInfo = parts[0];   // "KRW" 저장
                        coinCode = parts[1]; // "BTC" 저장
                    }
                }

                // 마켓 정보 저장
                databaseManager.saveDailyPriceInfo(marketCode, trade_date, marketInfo, coinCode, opening_price, high_price, low_price, prev_date, prev_closing_price, acc_trade_price, acc_trade_price_24h, acc_trade_volume, acc_trade_volume_24h, highest_52_week_price, highest_52_week_date, lowest_52_week_price, lowest_52_week_date);
                rowCount++;
            }

            // 전일 종가 가져오기
            return null;
        } else {
            throw new Exception("Error: 응답 코드 " + responseCode);
        }
    }

    // 메인 메서드
    public static void main(String[] args) {
        try {

            // 전체 코인의 마켓 정보를 가져옴
            MarketInfoVO marketInfo = getUpbitMarketList();
            /*if (!marketInfo.isEmpty()) {
                System.out.println("들어옴");
                CoinListingVO coinListing = getFirstDayOfCoin(marketInfo);
                System.out.println("상장일 : " + coinListing);
            } else {
                System.out.println("코인을 찾을 수 없습니다.");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

