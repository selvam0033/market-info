package market.stock.trending.info.controller;
import market.stock.trending.info.alert.Alert;
import market.stock.trending.info.util.Constants;
import market.stock.trending.info.vo.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@RestController
public class Controller {
    Map<String, Stock> mapStock = new HashMap<>();
    Map<String, Stock> mapCrypto = new HashMap<>();
    LocalTime currentTime = LocalTime.now();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @GetMapping("/trending/stocks")
    public List<Stock> getTrendingStock() {
        Document doc = null;
        try {
            doc = Jsoup.connect(Constants.stock).get();
            Elements table = doc.select("tbody");
            Elements rows = table.select("tr");
            for (org.jsoup.nodes.Element row : rows) {
                Elements column = row.select("th,td");
                String[] val = column.get(3).text().split(" ");
                String name = column.get(0).text();
                float price = Float.parseFloat(val[0].replace(",",""));
                if (price < 10) {
                    if (!mapStock.containsKey(name)) {
                        mapStock.put(name, new Stock(name, price, currentTime, "New", sdf.format(new Date())));
                        Thread thread = new Thread(() -> Alert.play(name,"stock"));
                        thread.start();
                    } else {
                        Stock stock = mapStock.get(name);
                        int current = currentTime.getHour();
                        int previous = stock.getTrendingTime().getHour();
                        if (current - previous >= 1)
                            mapStock.get(name).setTrending("");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(mapStock.isEmpty())
            return new ArrayList<>();

       return new ArrayList<>(mapStock.values());
    }



    @GetMapping("/trending/crypto")
    public List<Stock> getTrendingCrypto() {
        Document doc = null;
        try {
            doc = Jsoup.connect(Constants.crypto).get();
            Elements table = doc.select("tbody");
            Elements rows = table.select("tr");
            for (org.jsoup.nodes.Element row : rows) {
                Elements column = row.select("th,td");
                String[] val = column.get(3).text().split(" ");
                String name = column.get(0).text();
                float price = Float.parseFloat(val[0].replace(",",""));
                if (price < 1) {
                    if (!mapCrypto.containsKey(name)) {
                        mapCrypto.put(name, new Stock(name, price, currentTime, "New", sdf.format(new Date())));
                        Thread thread = new Thread(() -> Alert.play(name,"crypto"));
                        thread.start();
                    } else {
                        Stock stock = mapCrypto.get(name);
                        int current = currentTime.getHour();
                        int previous = stock.getTrendingTime().getHour();
                        if (current - previous >= 1)
                            mapCrypto.get(name).setTrending("");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(mapCrypto.isEmpty())
            return new ArrayList<>();
        return new ArrayList<>(mapCrypto.values());
    }

}