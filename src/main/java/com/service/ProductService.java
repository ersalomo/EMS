package com.service;


import com.dao.ProductParamReq;
import com.entity.Merchant;
import com.entity.Product;
import com.model.ProductRequest;
import com.model.ProductUpdateReq;
import com.repository.ProductRepository;
import com.util.GenerateUniqueCode;
import com.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
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
                () ->  new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found")
        );
    }


    public Product create(ProductRequest productReq) {
        Merchant merchant = merchantService.findById(productReq.getMerchantId());
        Product product =  new Product();
        product.setProductCode("product-" + GenerateUniqueCode.generateUniqueCode() + UUID.randomUUID());
        product.setProductName(productReq.getProductName());
        product.setPrice(productReq.getPrice());
        product.setMerchant(merchant);
        return productRepository.save(product);
    }


    public Product show(Long id) {
        return find(id);
    }
    public void delete(Long id) {
        Product product =  find(id);
        product.setDeletedAt(new Date());
        productRepository.save(product);
    }

    public Page<Product> get(ProductParamReq req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Specification<Product> specification = ((root, query, builder) ->
                query.where(
                        builder.and(
                                StringUtils.isNotEmpty(req.getName()) ?
                                        builder.like(
                                                builder.lower(root.get("productName")), "%" +req.getName().toLowerCase()+ "%"
                                        ) : builder.and()
                        )).getRestriction());
     return productRepository.findAll(specification, pageable);
    }
    public Product update(Long id, ProductUpdateReq req) {
        Product product = find(id);
        product.setProductName(req.getProductName());
        product.setPrice(req.getPrice());
        return productRepository.save(product);
    }

    public Product createOrUpdate(Long id, ProductRequest productReq) {
       return null;
    }

}
