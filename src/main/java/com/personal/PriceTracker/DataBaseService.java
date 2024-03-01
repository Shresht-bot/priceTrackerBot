package com.personal.PriceTracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DataBaseService {

    @Autowired
    private ProductRepository productRepository;

    public void saveToDB(long chatId, String url, int price){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setUserid(chatId);
        productEntity.setUrl(url);
        productEntity.setAmount(price);
        productEntity.setFlag("N");
        productRepository.save(productEntity);
    }

    public List<ProductEntity> getAllProducts() {
        Optional<List<ProductEntity>> productEntities = productRepository.findAllByFlagEquals("N");
        return productEntities.orElseGet(ArrayList::new);
    }

    public List<ProductEntity> getProductsForUser(long chatId) {
        Optional<List<ProductEntity>> productEntities = productRepository.findAllByUseridIs(chatId);
        return productEntities.orElseGet(ArrayList::new);
    }

    public void resumeProduct(long chatId, int id){
        Optional<ProductEntity> p = productRepository.findByIdAndUserid(id, chatId);
        if (p.isEmpty()) return;
        ProductEntity productEntity = p.get();
        productEntity.setFlag("N");
        productRepository.save(productEntity);
    }

    public void pauseProduct(ProductEntity productEntity){
        productEntity.setFlag("Y");
        productRepository.save(productEntity);
    }

    public void deleteProduct(long chatId, int id){
        productRepository.deleteByIdAndUserid(id, chatId);
    }
}
