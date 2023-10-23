package com.service;


import com.entity.Product;
import com.model.ProductRequest;
import com.repository.ProductRepository;
import com.util.GenerateUniqueCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantService merchantService;


    public boolean productExists(Long id) {
        return productRepository.existsById(id);
    }

    public Product find(Long id) {

        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }


    public void create(ProductRequest productReq) {
        boolean existsMerchant = merchantService.exists(productReq.getMerchantId());
        if (!existsMerchant) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found");
        }
        Product product =  new Product();
        product.setProductCode("product-" + GenerateUniqueCode.generateUniqueCode());
        product.setProductName(product.getProductName());
        product.setPrice(productReq.getPrice());
        productRepository.save(product);
    }
    public void show(Long id) {

    }
    public void delete(Long id) {

    }
    public List<Product> get() {
     return productRepository.findAll();
    }
    public void update(ProductRequest productReq) {

    }

}
