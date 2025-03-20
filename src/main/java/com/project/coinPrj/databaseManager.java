package com.project.coinPrj;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.project.coinPrj.vo.MarketInfoVO;
import com.project.coinPrj.vo.CoinListingVO;

public class databaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // DB URL
    private static final String USER = "postgres"; // DB 사용자명
    private static final String PASSWORD = "Roqkf1208!"; // DB 비밀번호

    // PostgreSQL 데이터베이스 연결 함수
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // 마켓정보 삭제
    public static void updateMarketInfo() {

        String sql = "UPDATE coinmaster SET listing_yn = 'R' ";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 쿼리 실행
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 마켓정보 저장
    public static void saveMarketInfo(String marketCode, String marketInfo, String coinCode, String coinEngName, String coinKorName) {

        String sql = "INSERT INTO coinmaster (marketcode, marketinfo, coincode, coinengname, coinkorname, listing_yn) " +
                "VALUES (?, ?, ?, ?, ?, 'C') " +
                "ON CONFLICT (marketcode, marketinfo, coincode) DO UPDATE " +
                "SET coinengname = EXCLUDED.coinengname, " +
                "coinkorname = EXCLUDED.coinkorname, " +
                "listing_yn = 'C' ";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marketCode);
            pstmt.setString(2, marketInfo);
            pstmt.setString(3, coinCode);
            pstmt.setString(4, coinEngName);
            pstmt.setString(5, coinKorName);

            // 쿼리 실행
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 마켓정보 저장
    public static void saveDailyPriceInfo(String marketCode, String trade_date, String marketInfo, String coinCode, Double opening_price, Double high_price, Double low_price, String prev_date, Double prev_closing_price, Double acc_trade_price, Double acc_trade_price_24h, Double acc_trade_volume, Double acc_trade_volume_24h, Double highest_52_week_price, String highest_52_week_date, Double lowest_52_week_price, String lowest_52_week_date) {

        // SQL 저장 쿼리 작성
        String sql = "INSERT INTO dailyprice (marketcode, trade_date, marketinfo, coincode, opening_price, high_price, low_price, prev_date, prev_closing_price, acc_trade_price, acc_trade_price_24h, acc_trade_volume, acc_trade_volume_24h, highest_52_week_price, highest_52_week_date, lowest_52_week_price, lowest_52_week_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (marketcode, trade_date) DO UPDATE " +
                "SET opening_price = EXCLUDED.opening_price, " +
                "high_price = EXCLUDED.high_price, " +
                "low_price = EXCLUDED.low_price, " +
                "prev_date = EXCLUDED.prev_date, " +
                "prev_closing_price = EXCLUDED.prev_closing_price, " +
                "acc_trade_price = EXCLUDED.acc_trade_price, " +
                "acc_trade_price_24h = EXCLUDED.acc_trade_price_24h, " +
                "acc_trade_volume = EXCLUDED.acc_trade_volume, " +
                "acc_trade_volume_24h = EXCLUDED.acc_trade_volume_24h, " +
                "highest_52_week_price = EXCLUDED.highest_52_week_price, " +
                "highest_52_week_date = EXCLUDED.highest_52_week_date, " +
                "lowest_52_week_price = EXCLUDED.lowest_52_week_price, " +
                "lowest_52_week_date = EXCLUDED.lowest_52_week_date ";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marketCode);
            pstmt.setString(2, trade_date);
            pstmt.setString(3, marketInfo);
            pstmt.setString(4, coinCode);
            pstmt.setDouble(5, opening_price);
            pstmt.setDouble(6, high_price);
            pstmt.setDouble(7, low_price);
            pstmt.setString(8, prev_date);
            pstmt.setDouble(9, prev_closing_price);
            pstmt.setDouble(10, acc_trade_price);
            pstmt.setDouble(11, acc_trade_price_24h);
            pstmt.setDouble(12, acc_trade_volume);
            pstmt.setDouble(13, acc_trade_volume_24h);
            pstmt.setDouble(14, highest_52_week_price);
            pstmt.setString(15, highest_52_week_date);
            pstmt.setDouble(16, lowest_52_week_price);
            pstmt.setString(17, lowest_52_week_date);

            // 쿼리 실행
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 일봉 캔들 기준 가격정보 저장
    public static void saveDailyCandlePrice(String marketCode, String marketInfo, String coinCode, String candle_date_kst, String candle_date_utc, Double opening_price, Double high_price, Double low_price, Double trade_price, Double candle_acc_trade_price, Double candle_acc_trade_volume, String prev_date, Double prev_closing_price, Double change_price, Double change_rate, Double converted_trade_price) {
        String sql = "INSERT INTO dailycandleprice (marketCode, marketInfo, coinCode, candle_date_kst, candle_date_utc, opening_price, high_price, low_price, trade_price, candle_acc_trade_price, candle_acc_trade_volume, prev_Date, prev_closing_price, change_price, change_rate, converted_trade_price) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (marketCode, candle_date_kst) DO UPDATE " +
                "SET candle_date_utc = EXCLUDED.candle_date_utc, " +
                "opening_price = EXCLUDED.opening_price, " +
                "high_price = EXCLUDED.high_price, " +
                "low_price = EXCLUDED.low_price, " +
                "trade_price = EXCLUDED.trade_price, " +
                "candle_acc_trade_price = EXCLUDED.candle_acc_trade_price, " +
                "candle_acc_trade_volume = EXCLUDED.candle_acc_trade_volume, " +
                "prev_date = EXCLUDED.prev_date, " +
                "prev_closing_price = EXCLUDED.prev_closing_price, " +
                "change_price = EXCLUDED.change_price, " +
                "change_rate = EXCLUDED.change_rate, " +
                "converted_trade_price = EXCLUDED.converted_trade_price ";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marketCode);
            pstmt.setString(2, marketInfo);
            pstmt.setString(3, coinCode);
            pstmt.setString(4, candle_date_kst);
            pstmt.setString(5, candle_date_utc);
            pstmt.setDouble(6, opening_price);
            pstmt.setDouble(7, high_price);
            pstmt.setDouble(8, low_price);
            pstmt.setDouble(9, trade_price);
            pstmt.setDouble(10, candle_acc_trade_price);
            pstmt.setDouble(11, candle_acc_trade_volume);
            pstmt.setString(12, prev_date);
            pstmt.setDouble(13, prev_closing_price);
            pstmt.setDouble(14, change_price);
            pstmt.setDouble(15, change_rate);
            pstmt.setDouble(16, converted_trade_price);

            // 쿼리 실행
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException e) {
        System.out.println(e.getMessage());
        }
    }

    // marketcode 컬럼 가져오기 메서드
    public List<MarketInfoVO> getMarketCodes() {
        List<MarketInfoVO> marketCodeList = new ArrayList<>();
        // SQL 쿼리
        String sql = "SELECT marketcode FROM coinmaster WHERE listing_yn in ('C') and marketinfo = 'KRW'";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery(); {

                // 결과 처리
                while (rs.next()) {
                    String marketCode = rs.getString("marketcode");

                    MarketInfoVO marketInfoVO = new MarketInfoVO(marketCode, "", "", "", "");
                    marketCodeList.add(marketInfoVO);

                }

            }} catch (SQLException e) {
            e.printStackTrace();
        }
        return marketCodeList;
    }

    // 가장 예전 날짜 컬럼 가져오기 메서드
    public String getLastDay(String marketCode, String toDate) {
        // SQL 쿼리
        String sql = "SELECT COALESCE(min(candle_date_kst), '99991231') candle_date_kst FROM dailycandleprice WHERE marketcode = ? ";
        String candle_date_kst = "";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, marketCode);

            ResultSet rs = pstmt.executeQuery(); {

                // 결과 처리
                while (rs.next()) {
                    candle_date_kst = rs.getString("candle_date_kst");
                    if(candle_date_kst == "99991231"){
                        candle_date_kst = toDate;
                    }

                }

            }} catch (SQLException e) {
            e.printStackTrace();
        }
        return candle_date_kst;
    }

    // 테이블의 특정 marketcode에 대해 listing_date와 listing_yn 업데이트 메서드
    public static void updateCoinMaster(String marketCode, String listingDate) {
        // SQL UPDATE 쿼리
        String sql = "UPDATE coinmaster SET listingdate = ?, listing_yn = ? WHERE marketcode = ?";

        // 데이터베이스 연결 및 쿼리 실행
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // PreparedStatement에 파라미터 바인딩
            pstmt.setString(1, listingDate);   // listing_date 값 설정
            pstmt.setString(2, "Y");     // listing_yn 값 설정
            pstmt.setString(3, marketCode);    // marketcode 값을 조건으로 설정

            // 업데이트 실행
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("아래 코인의 일봉 종가 정보 수신을 완료하였습니다.: " + marketCode);
            } else {
                System.out.println("아래 코인 코인마스터 테이블 마감 업데이트 중 오류 발생: " + marketCode);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

