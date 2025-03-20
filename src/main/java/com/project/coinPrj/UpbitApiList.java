package com.project.coinPrj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import com.project.coinPrj.vo.DailyCandlePriceVO;
import com.project.coinPrj.vo.DailyPriceVO;
import com.project.coinPrj.vo.MarketInfoVO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
        // 마켓 정보 삭제
        databaseManager.updateMarketInfo();

        for (int i = 0; i < marketList.length(); i++) {
            JSONObject market = marketList.getJSONObject(i);
            String coinEngName = market.getString("english_name");
            String coinKorName = market.getString("korean_name");
            String marketCode = market.getString("market");
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
            //getPrevClosingPrice(marketCode);
        }
        System.out.println(rowCount + "건이 저장 되었습니다.");
        return null;
    }

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

                String marketCode = dailyPrice.getString("market");
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

    // 업비트 API에서 특정 코인의 과거 종가 데이터를 가져오는 함수
    public static DailyCandlePriceVO getDailyCandlePrice(String market, String toDate, int count) throws Exception {

        //중복처리 예외를 위한 가장 마지막 일자 조회
        databaseManager dao = new databaseManager();
        //toDate = dao.getLastDay(market, toDate);

        // 입력된 문자열이 YYYYMMDD 형식임을 가정
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // LocalDate로 변환
        LocalDate dateTrans = LocalDate.parse(toDate, inputFormatter);

        // 변환된 날짜에 시간 정보 추가 (00:00:00으로 설정)
        LocalDateTime dateTime = dateTrans.atStartOfDay();

        // 출력 포맷 정의 (yyyy-MM-dd HH:mm:ss)
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        // 포맷팅된 문자열 반환
        String changeToDate = dateTime.format(outputFormatter);

        String apiUrl = String.format("https://api.upbit.com/v1/candles/days?market=%s&count=%d&to=%s", market, count, changeToDate);

        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // GET 요청 설정
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // JSON 데이터를 파싱
            JSONArray dailyCandlePriceList = new JSONArray(response.toString());

            //Json 수신 결과 있을 때
            if (dailyCandlePriceList.length() > 0) {
                JSONObject jsonObject = dailyCandlePriceList.getJSONObject(0);

                int rowCount = 0;

                // 현재 날짜 가져오기
                LocalDate today = LocalDate.now();

                // 입력된 문자열이 YYYYMMDD 형식임을 가정
                DateTimeFormatter inputFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

                // 출력 포맷 정의 (YYYYMMDD)
                DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("yyyyMMdd");

                // 날짜를 "YYYYMMDD" 형식으로 포맷
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                for (int i = 0; i < dailyCandlePriceList.length(); i++) {
                    JSONObject dailyCandlePrice = dailyCandlePriceList.getJSONObject(i);
                    String marketCode = dailyCandlePrice.getString("market");
                    String marketInfo = "";
                    String coinCode = "";
                    LocalDate dateTrans1 = LocalDate.parse(dailyCandlePrice.getString("candle_date_time_kst"), inputFormatter1);
                    String candle_date_kst = dateTrans1.format(outputFormatter1);
                    dateTrans1 = LocalDate.parse(dailyCandlePrice.getString("candle_date_time_utc"), inputFormatter1);
                    String candle_date_utc = dateTrans1.format(outputFormatter1);
                    Double opening_price = dailyCandlePrice.getDouble("opening_price");
                    Double high_price = dailyCandlePrice.getDouble("high_price");
                    Double low_price = dailyCandlePrice.getDouble("low_price");
                    Double trade_price = dailyCandlePrice.getDouble("trade_price");
                    Double candle_acc_trade_price = dailyCandlePrice.getDouble("candle_acc_trade_price");
                    Double candle_acc_trade_volume = dailyCandlePrice.getDouble("candle_acc_trade_volume");

                    // 입력된 날짜를 LocalDate로 변환
                    DateTimeFormatter inputFormatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");

                    // LocalDate로 변환
                    LocalDate date = LocalDate.parse(candle_date_kst, inputFormatter2);

                    // 하루 전 날짜 계산
                    LocalDate previousDate = date.minusDays(1);

                    String prev_date = previousDate.format(outputFormatter1);
                    //Double prev_closing_price = dailyCandlePrice.getDouble("prev_closing_price");
                    Double prev_closing_price = dailyCandlePrice.optDouble("prev_closing_price", 0.0);
                    Double change_price = dailyCandlePrice.optDouble("change_price", 0.0);
                    Double change_rate = dailyCandlePrice.optDouble("change_rate", 0.0);
                    Double converted_trade_price = Double.valueOf(0);

                    // market 코드가 "KRW-BTC" 형식인지 확인하고 분리
                    if (marketCode != null && marketCode.contains("-")) {
                        String[] parts = marketCode.split("-");
                        if (parts.length == 2) {
                            marketInfo = parts[0];   // "KRW" 저장
                            coinCode = parts[1]; // "BTC" 저장
                        }
                    }

                    // 마켓 정보 저장
                    databaseManager.saveDailyCandlePrice(marketCode, marketInfo, coinCode, candle_date_kst, candle_date_utc, opening_price, high_price, low_price, trade_price, candle_acc_trade_price, candle_acc_trade_volume, prev_date, prev_closing_price, change_price, change_rate, converted_trade_price
                    );
                    rowCount++;
                }

                String nextToDate = dao.getLastDay(market, toDate);

                getDailyCandlePrice(market, nextToDate, count);
                // 전일 종가 가져오기
                return null;

                // 수신결과 없으면 끝난 것으로 간주하고 코인마스터에 상장일 정보 업데이트
            } else {
                databaseManager.updateCoinMaster(market, toDate);
                return null;
            }
        } else {
            throw new Exception("Error: 응답 코드 " + responseCode);
        }
    }



    // 메인 메서드
    public static void main(String[] args) {
        try {

            // 전체 코인의 마켓 정보를 가져옴
            MarketInfoVO marketInfo = getUpbitMarketList();
            databaseManager dao = new databaseManager();
            List<MarketInfoVO> marketCodes = dao.getMarketCodes();

            //getDailyCandlePrice("BTC-USDS", "20241101", 200);


            // VO 객체 리스트 출력
            for (MarketInfoVO marketInfoVO : marketCodes) {
                System.out.println("코인이름은 : " + marketInfoVO.getCoinEngName() + " -- " + marketInfoVO.getMarketCode());
                getDailyCandlePrice(marketInfoVO.getMarketCode(), "20250201", 11);

                //특정 코인 수신 제외할 경우
                /*if(marketInfoVO.getMarketCode().equals("KRW-BOUNTY")){
                    System.out.println("스킵 : " + marketInfoVO.getMarketCode());
                }else {
                    System.out.println("코인이름은 : " + marketInfoVO.getCoinEngName() + " -- " + marketInfoVO.getMarketCode());
                    getDailyCandlePrice(marketInfoVO.getMarketCode(), "20250121", 1);
                    //getDailyCandlePrice("KRW-1INCH", "20240918", 7);
                }*/
            }

            //getDailyCandlePrice("KRW-1INCH", "20240918", 7);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

