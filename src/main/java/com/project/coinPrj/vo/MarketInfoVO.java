package com.project.coinPrj.vo;

import org.json.JSONArray;

import java.util.function.Consumer;

// MarketInfoVO.java
// MarketInfoVO.java
public class MarketInfoVO extends JSONArray {

    private String marketCode;     // 마켓 코드
    private String marketInfo;         // 마켓 코드
    private String coinCode;       // 마켓 코드
    private String coinEngName;    // 코인 영문명
    private String coinKorName;    // 코인 한글명

    // 기본 생성자
    public MarketInfoVO(String marketCode, String marketInfo, String coinCode, String coinEngName, String coinKorName) {}

    // 파라미터를 받는 생성자
    public MarketInfoVO(String marketCode, String coinEngName, String coinKorName) {
        this.marketCode = marketCode;
        this.marketInfo = marketInfo;
        this.coinCode = coinCode;
        this.coinEngName = coinEngName;
        this.coinKorName = coinKorName;
    }

    // Getter 및 Setter
    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMarketInfo() {
        return marketInfo;
    }

    public void setMarketInfo(String marketInfo) {
        this.marketInfo = marketInfo;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

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

    // toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "MarketInfoVO{" +
                "marketCode='" + marketCode + '\'' +
                ", marketInfo='" + marketInfo + '\'' +
                ", coinCode='" + coinCode + '\'' +
                ", coinEngName='" + coinEngName + '\'' +
                ", coinKorName='" + coinKorName + '\'' +
                '}';
    }

    @Override
    public void forEach(Consumer<? super Object> action) {
        super.forEach(action);
    }
}


