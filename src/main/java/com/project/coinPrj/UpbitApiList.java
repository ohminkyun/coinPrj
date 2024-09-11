package com.project.coinPrj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.project.coinPrj.vo.MarketInfoVO;
import org.json.JSONArray;
import org.json.JSONObject;

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
        }
        System.out.println(rowCount + "건이 저장 되었습니다.");
        return null;
    }

    // 특정 코인의 상장일을 가져오는 함수
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

