package com.project.coinPrj.vo;

// DailyPriceVO.java
public class DailyPriceVO {

    private String marketCode;
    private String trade_date;
    private String marketInfo;
    private String coinCode;
    private Double opening_price;
    private Double high_price;
    private Double low_price;
    private String prev_date;
    private Double prev_closing_price;
    private Double acc_trade_price;
    private Double acc_trade_price_24h;
    private Double acc_trade_volume;
    private Double acc_trade_volume_24h;
    private Double highest_52_week_price;
    private String highest_52_week_date;
    private Double lowest_52_week_price;
    private String lowest_52_week_date;

    // 기본 생성자
    public DailyPriceVO() {}

    // 파라미터를 받는 생성자
    public DailyPriceVO(String marketCode, String trade_date, String marketInfo, String coinCode, Double opening_price, Double high_price, Double low_price, String prev_date, Double prev_closing_price, Double acc_trade_price, Double acc_trade_price_24h, Double acc_trade_volume, Double acc_trade_volume_24h, Double highest_52_week_price, String highest_52_week_date, Double lowest_52_week_price, String lowest_52_week_date) {
        this.marketCode = marketCode;
        this.trade_date = trade_date;
        this.marketInfo = marketInfo;
        this.coinCode = coinCode;
        this.opening_price = opening_price;
        this.high_price = high_price;
        this.low_price = low_price;
        this.prev_date = prev_date;
        this.prev_closing_price = prev_closing_price;
        this.acc_trade_price = acc_trade_price;
        this.acc_trade_price_24h = acc_trade_price_24h;
        this.acc_trade_volume = acc_trade_volume;
        this.acc_trade_volume_24h = acc_trade_volume_24h;
        this.highest_52_week_price = highest_52_week_price;
        this.highest_52_week_date = highest_52_week_date;
        this.lowest_52_week_price = lowest_52_week_price;
        this.lowest_52_week_date = lowest_52_week_date;
    }

    // Getter 및 Setter


    // toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "DailyPriceVO{" +
                "marketCode='" + marketCode + '\'' +
                ",trade_date='" + trade_date + '\'' +
                ",marketInfo='" + marketInfo + '\'' +
                ",coinCode='" + coinCode + '\'' +
                ",opening_price='" + opening_price + '\'' +
                ",high_price='" + high_price + '\'' +
                ",low_price='" + low_price + '\'' +
                ",prev_date='" + prev_date + '\'' +
                ",prev_closing_price='" + prev_closing_price + '\'' +
                ",acc_trade_price='" + acc_trade_price + '\'' +
                ",acc_trade_price_24h='" + acc_trade_price_24h + '\'' +
                ",acc_trade_volume='" + acc_trade_volume + '\'' +
                ",acc_trade_volume_24h='" + acc_trade_volume_24h + '\'' +
                ",highest_52_week_price='" + highest_52_week_price + '\'' +
                ",highest_52_week_date='" + highest_52_week_date + '\'' +
                ",lowest_52_week_price='" + lowest_52_week_price + '\'' +
                ",lowest_52_week_date='" + lowest_52_week_date + '\'' +
                '}';
    }
}
