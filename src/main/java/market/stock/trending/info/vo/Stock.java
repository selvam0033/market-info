package market.stock.trending.info.vo;

import java.time.LocalTime;
public class Stock {
    private String name;
    private Float price;
    private LocalTime trendingTime;
    private String trending;
    private String currentDate;
    private String average;


    public Stock(String name, Float price, LocalTime trendingTime, String trending, String currentDate,String average) {
        this.name = name;
        this.price = price;
        this.trendingTime = trendingTime;
        this.trending = trending;
        this.currentDate = currentDate;
        this.average = average;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalTime  getTrendingTime() {
        return trendingTime;
    }

    public void setTrendingTime(LocalTime  trendingTime) {
        this.trendingTime = trendingTime;
    }

    public String getTrending() {
        return trending;
    }

    public void setTrending(String trending) {
        this.trending = trending;
    }


    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }
}
