package com.personal.PriceTracker;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import static com.personal.PriceTracker.ConstantText.*;

@Component
@EnableScheduling
public class SchedulerClass {

    @Autowired
    private DataBaseService dataBaseService;

    @Value("${BOT_TOKEN}")
    private String token;

    @Scheduled(fixedDelay = 600000)
    public void startScheduler() {
        List<ProductEntity> products = dataBaseService.getAllProducts();
        if (products.isEmpty()) return;

        for (ProductEntity product: products){
            long chatId = product.getUserid();
            String productUrl = product.getUrl();
            Document doc = HelperClass.getDom(productUrl);
            if (doc == null) continue;
            String productName = HelperClass.fetchName(doc);
            int currentPrice = HelperClass.fetchCurrentPrice(doc);
            int expectedPrice = product.getAmount();

            if (currentPrice == -1 || currentPrice > expectedPrice) continue;

            String initialText = SCHEDULER_TEXT1 + SCHEDULER_TEXT2 + PRODUCT_NAME_TEXT + productName + SCHEDULER_TEXT3 + currentPrice + SCHEDULER_TEXT4 + productUrl;
            String text = HelperClass.updateUrl(initialText);

            String urlString = String.format(MESSAGE_TEXT, token, chatId, text);
            try {
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();
                InputStream is = new BufferedInputStream(conn.getInputStream());
                dataBaseService.pauseProduct(product);
            } catch (IOException e) {
                return;
            }
        }
    }
}
