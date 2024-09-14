package com.project.coinPrj.vo;

// DailyCandlePriceVO.java
public class DailyCandlePriceVO {

    private String marketCode;
    private String marketInfo;
    private String coinCode;
    private String candle_date_kst;
    private String candle_date_utc;
    private Double opening_price;
    private Double high_price;
    private Double low_price;
    private Double trade_price;
    private Double candle_acc_trade_price;
    private Double candle_acc_trade_volume;
    private String prev_date;
    private Double prev_closing_price;
    private Double change_price;
    private Double change_rate;
    private Double converted_trade_price;


    // 기본 생성자 위치
    public DailyCandlePriceVO() {}

    // 파라미터를 받는 생성자
    public DailyCandlePriceVO(String marketCode, String marketInfo, String coinCode, String candle_date_kst, String candle_date_utc, Double opening_price, Double high_price, Double low_price, Double trade_price, Double candle_acc_trade_price, Double candle_acc_trade_volume, String prev_date, Double prev_closing_price, Double change_price, Double change_rate, Double converted_trade_price) {
        this.marketCode              = marketCode;
        this.marketInfo              = marketInfo;
        this.coinCode                = coinCode;
        this.candle_date_kst         = candle_date_kst;
        this.candle_date_utc         = candle_date_utc;
        this.opening_price	         = opening_price;
        this.high_price              = high_price;
        this.low_price               = low_price;
        this.trade_price             = trade_price;
        this.candle_acc_trade_price  = candle_acc_trade_price;
        this.candle_acc_trade_volume = candle_acc_trade_volume;
        this.prev_date               = prev_date;
        this.prev_closing_price      = prev_closing_price;
        this.change_price            = change_price;
        this.change_rate             = change_rate;
        this.converted_trade_price   = converted_trade_price;
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

    public void setMarketinfo(String marketInfo) {
        this.marketInfo = marketInfo;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCandle_date_kst() {
        return candle_date_kst;
    }

    public void setCandle_date_kst(String candle_date_kst) {
        this.candle_date_kst = candle_date_kst;
    }

    public String getCandle_date_utc() {
        return candle_date_utc;
    }

    public void setCandle_date_utc(String candle_date_utc) {
        this.candle_date_utc = candle_date_utc;
    }

    public Double getOpening_price() {
        return opening_price;
    }

    public void setOpening_price(Double opening_price) {
        this.opening_price = opening_price;
    }

    public Double getHigh_price() {
        return high_price;
    }

    public void setHigh_price(Double high_price) {
        this.high_price = high_price;
    }

    public Double getLow_price() {
        return low_price;
    }

    public void setLow_price(Double low_price) {
        this.low_price = low_price;
    }

    public Double getTrade_price() {
        return trade_price;
    }

    public void setTrade_price(Double trade_price) {
        this.trade_price = trade_price;
    }

    public Double getCandle_acc_trade_price() {
        return candle_acc_trade_price;
    }

    public void setCandle_acc_trade_price(Double candle_acc_trade_price) {
        this.candle_acc_trade_price = candle_acc_trade_price;
    }

    public Double getCandle_acc_trade_volume() {
        return candle_acc_trade_volume;
    }

    public void setCandle_acc_trade_volume(Double candle_acc_trade_volume) {
        this.candle_acc_trade_volume = candle_acc_trade_volume;
    }

    public String getPrev_date() {
        return prev_date;
    }

    public void setPrev_date(String prev_date) {
        this.prev_date = prev_date;
    }

    public Double getPrev_closing_price() {
        return prev_closing_price;
    }

    public void setPrev_closing_price(Double prev_closing_price) {
        this.prev_closing_price = prev_closing_price;
    }

    public Double getChange_price() {
        return change_price;
    }

    public void setChange_price(Double change_price) {
        this.change_price = change_price;
    }

    public Double getChange_rate() {
        return change_rate;
    }

    public void setChange_rate(Double change_rate) {
        this.change_rate = change_rate;
    }

    public Double getConverted_trade_price() {
        return converted_trade_price;
    }

    public void setConverted_trade_price(Double converted_trade_price) {
        this.converted_trade_price = converted_trade_price;
    }

    // toString 메서드(디버깅용)
    @Override
    public String toString() {
        return "DailyCandlePriceVO{" +
                "marketcode'" + marketCode + '\'' +
                ",marketinfo'" + marketInfo + '\'' +
                ",coincode'" + coinCode + '\'' +
                ",candle_date_kst'" + candle_date_kst + '\'' +
                ",candle_date_utc'" + candle_date_utc + '\'' +
                ",opening_price	'" + opening_price + '\'' +
                ",high_price'" + high_price + '\'' +
                ",low_price'" + low_price + '\'' +
                ",trade_price'" + trade_price + '\'' +
                ",candle_acc_trade_price'" + candle_acc_trade_price + '\'' +
                ",candle_acc_trade_volume'" + candle_acc_trade_volume + '\'' +
                ",prev_date'" + prev_date + '\'' +
                ",prev_closing_price'" + prev_closing_price + '\'' +
                ",change_price'" + change_price + '\'' +
                ",change_rate'" + change_rate + '\'' +
                ",converted_trade_price'" + converted_trade_price + '\'' +
                '}';
    }
}

