package market.stock.trending.info.controller;
import market.stock.trending.info.services.DocService;
import market.stock.trending.info.vo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class Controller {
    @Autowired
    DocService docService;

    @GetMapping("/trending/stocks")
    public List<Trade> getTrendingStock() {
        return docService.getStocks();
    }



    @GetMapping("/trending/crypto")
    public List<Trade> getTrendingCrypto() {
        return docService.getCrypto();
    }

    @GetMapping("/trending/average/{id}")
    public String getAverage(@PathVariable String id) {
        return docService.getAverage(id);
    }

}