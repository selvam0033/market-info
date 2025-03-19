package market.stock.trending.info.services;
import market.stock.trending.info.alert.Alert;
import market.stock.trending.info.util.Constants;
import market.stock.trending.info.vo.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

@Service
public class DocService {
    Map<String, Stock> mapStock = new HashMap<>();
    Map<String, Stock> mapCrypto = new HashMap<>();
    LocalTime currentTime = LocalTime.now();
    Integer showBelow =1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public List<Stock> getStocks() {
        Document doc = getDocument(Constants.stock);
        Elements table = doc.select("tbody");
        Elements rows = table.select("tr");
        for (org.jsoup.nodes.Element row : rows) {
            Elements column = row.select("th,td");
            String[] val = column.get(3).text().split(" ");
            String name = column.get(0).text();
            float price = Float.parseFloat(val[0].replace(",",""));
            if (price <= showBelow) {
                if (!mapStock.containsKey(name)) {
                    mapStock.put(name, new Stock(name, price,currentTime,"NEW",sdf.format(new Date()),getAverage(name)));
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
        if(mapStock.isEmpty())
            return new ArrayList<>();

        return new ArrayList<>(mapStock.values());
    }


    public String getAverage(String name){
        String url = "https://finance.yahoo.com/quote/"+name+"/";
        Document doc = getDocument(url);
        Elements div = doc.getElementsByClass("container yf-1jj98ts");
        Elements lists = div.select("li");
        String [] val=
                lists.get(4).text().replace("Day's Range ","").split("-");
        float average = (Float.parseFloat(val[0])+Float.parseFloat(val[1]))/2;
        return String.valueOf(average);
    }

    public List<Stock> getCrypto() {
        Document doc = getDocument(Constants.crypto);
        Elements table = doc.select("tbody");
        Elements rows = table.select("tr");
        for (org.jsoup.nodes.Element row : rows) {
            Elements column = row.select("th,td");
            String[] val = column.get(3).text().split(" ");
            String name = column.get(0).text();
            float price = Float.parseFloat(val[0].replace(",",""));
            if (price <= showBelow) {
                if (!mapCrypto.containsKey(name)) {
                    mapCrypto.put(name, new Stock(name, price,currentTime,"NEW",sdf.format(new Date()),getAverage(name)));
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
        if(mapCrypto.isEmpty())
            return new ArrayList<>();
        return new ArrayList<>(mapCrypto.values());
    }

    private Document getDocument(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

}
