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

