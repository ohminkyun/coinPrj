package com.project.coinPrj.vo;

// CoinListingVO.java
public class CoinListingVO {

    private String coinEngName;        // 코인 영문명
    private String coinKorName;        // 코인 한글명
    private String listingDate;        // 상장일 (첫 거래일)

    // 기본 생성자
    public CoinListingVO() {}

    // 파라미터를 받는 생성자
    public CoinListingVO(String coinEngName, String coinKorName, String listingDate) {
        this.coinEngName = coinEngName;
        this.coinKorName = coinKorName;
        this.listingDate = listingDate;
    }

    // Getter 및 Setter
    public String getCoinEngName() {
        return coinEngName;
    }

    public void setCoinEngName(String coinEngName) {
        this.coinEngName = coinEngName;
    }

    public String getCoinKorName() {
        return coinKorName;
    }

    public void setCoinKorName(String coinKorName) {
        this.coinKorName = coinKorName;
    }

    public String getListingDate() {
        return listingDate;
    }

    public void setListingDate(String listingDate) {
        this.listingDate = listingDate;
    }

    // toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "CoinListingVO{" +
                "coinKorName='" + coinKorName + '\'' +
                ", coinEngName='" + coinEngName + '\'' +
                ", listingDate='" + listingDate + '\'' +
                '}';
    }
}
