package com.project.coinPrj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

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

    // 마켓정보 저장
    public static void saveMarketInfo(String marketCode, String marketInfo, String coinCode, String coinEngName, String coinKorName) {

        String sql = "INSERT INTO coinmaster (marketcode, marketinfo, coincode, coinengname, coinkorname) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "ON CONFLICT (marketcode, marketinfo, coincode) DO UPDATE " +
                "SET coinengname = EXCLUDED.coinengname, " +
                "coinkorname = EXCLUDED.coinkorname ";

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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // 상장일 정보 저장 못 사용함 ㅠㅠ
    public static void updateListingInfo(String marketCode, String listingDate) {

        // SQL UPDATE 쿼리
        String sql = "UPDATE coinmaster SET listingdate = ? WHERE marketcode = ?";

        try (Connection conn = connect();  // 데이터베이스 연결
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // PreparedStatement에 값을 설정
            pstmt.setString(1, listingDate);  // 첫 번째 파라미터: 새로운 listingdate
            pstmt.setString(2, marketCode);   // 두 번째 파라미터: marketcode

            // 쿼리 실행
            int rowsUpdated = pstmt.executeUpdate();

            // 업데이트된 행의 수 출력
            if (rowsUpdated > 0) {
                System.out.println("성공적으로 업데이트되었습니다. 업데이트된 행 수: " + rowsUpdated);
            } else {
                System.out.println("업데이트할 행을 찾지 못했습니다.");
            }

        } catch (SQLException e) {
            System.out.println("업데이트 중 오류 발생: " + e.getMessage());
        }
    }
}

