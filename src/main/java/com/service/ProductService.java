package com.service;


import com.entity.Merchant;
import com.entity.Product;
import com.model.ProductRequest;
import com.model.ProductUpdateReq;
import com.repository.ProductRepository;
import com.util.GenerateUniqueCode;
import com.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
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
        return productRepository.findById(id).orElseThrow(
                () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found user")
        );
    }


    public void create(ProductRequest productReq) {
        Merchant merchant = merchantService.findById(productReq.getMerchantId());
        if (merchant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found");
        }
        Product product =  new Product();
        product.setProductCode("product-" + GenerateUniqueCode.generateUniqueCode());
        product.setProductName(productReq.getProductName());
        product.setPrice(productReq.getPrice());
        product.setMerchant(merchant);
        productRepository.save(product);
    }
    public void show(Long id) {

    }
    public void delete(Long id) {
        Product product =  productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not Found"));

        product.setDeletedAt(Util.getCurrentDate());

    }
    public List<Product> get() {
     return productRepository.findAll();
    }
    public void update(Long id, ProductUpdateReq productReq) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product Not found"));
        product.setProductName(productReq.getProductName());
        product.setPrice(product.getPrice());
        productRepository.save(product);

    }

}
