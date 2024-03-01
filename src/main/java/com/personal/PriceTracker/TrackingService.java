package com.personal.PriceTracker;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.personal.PriceTracker.ConstantText.*;

@Service
public class TrackingService {

    @Autowired
    private DataBaseService dataBaseService;

    public void saveToDB(long chatId, String url, String enteredPrice){
        int price = HelperClass.fetchNumber(enteredPrice);
        dataBaseService.saveToDB(chatId, url, price);
    }

    public String getProductsForUser(long chatId) {

        List<ProductEntity> productEntities = dataBaseService.getProductsForUser(chatId);
        if (productEntities.isEmpty()) return NO_PRODUCTS_TO_TRACK_TEXT;
        StringBuilder message = new StringBuilder();
        message.append(TRACKING_PRODUCTS_INITIAL_TEXT);

        for (ProductEntity productEntity: productEntities){
            Document doc = HelperClass.getDom(productEntity.getUrl());
            if (doc == null) return PRODUCT_FETCH_FAILURE_TEXT;
            String name = HelperClass.fetchName(doc);
            int currentPrice = HelperClass.fetchCurrentPrice(doc);
            int requestedPrice = productEntity.getAmount();

            if (currentPrice == -1) return PRODUCT_FETCH_FAILURE_TEXT;

            String text = PRODUCT_NAME_TEXT + name + "\n" + CURRENT_PRICE_TEXT + currentPrice + "\n" + REQUESTED_PRICE_TEXT + requestedPrice +
                    "\n" + STOP_TRACKING_TEXT + productEntity.getId() + "\n";

            if (productEntity.getFlag().equals("Y")){
                text += RESUME_TRACKING_TEXT + productEntity.getId() + "\n";
            }
            text += "\n";
            message.append(text);
        }
        return message.toString();
    }

    public void resumeProduct(long chatId, String value){
        int id = HelperClass.fetchNumber(value);
        dataBaseService.resumeProduct(chatId, id);
    }

    public void deleteProduct(long chatId, String value){
        int id = HelperClass.fetchNumber(value);
        dataBaseService.deleteProduct(chatId, id);
    }
}
