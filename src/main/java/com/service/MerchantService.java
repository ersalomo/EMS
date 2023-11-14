package com.service;

import com.controller.MerchantController;
import com.dao.MerchantParamReq;
import com.entity.Merchant;
import com.model.MerchantRequest;
import com.repository.MerchantRepository;
import com.util.GenerateUniqueCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public Merchant findById(Long id) {
        return merchantRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant Not Found")
        );
    }

    public Merchant add(MerchantRequest merchantDao) {
        Merchant merchant = new Merchant();
        merchant.setMerchantCode(GenerateUniqueCode.generateUniqueCode());
        merchant.setMerchantName(merchantDao.getMerchantName());
        merchant.setMerchantLocation(merchantDao.getMerchantLocation());
        return merchantRepository.save(merchant);
    }

    @Transactional
    public Merchant updateStatus(Long id, boolean status) {
         Merchant merchant = findById(id);
         merchant.setOpen(status);
         return merchantRepository.save(merchant);
    }
    public Page<Merchant> findAll(MerchantParamReq req) {
        int page = Math.max(req.getPage(), 0);
        int size = req.getSize();
        Pageable pageable = PageRequest.of(page, size);
        Specification<Merchant> specification = (root, criteria, builder) ->
                criteria.where(
                        builder.and(
                                StringUtils.isNotEmpty(req.getName()) ?
                                builder.like(builder.lower(root.get("merchantName")), "%"+ req.getName().toLowerCase() + "%") : builder.and(),
                                StringUtils.isNotEmpty(req.getLocation()) ?
                                builder.like(builder.lower(root.get("merchantLocation")), "%"+ req.getLocation().toLowerCase() + "%") : builder.and()
                        ),
                        StringUtils.isNotEmpty(req.getOpen()) ?
                        builder.and(
                                builder.and(builder.equal(root.get("isOpen"), Objects.equals(req.getOpen(), MerchantController.OpenStatus.TRUE.toString())))
                        ) : builder.and()
          ).getRestriction();
        Page<Merchant> merchants = merchantRepository.findAll(specification,pageable);
        log.info("merchant [] {} {}", pageable);
        return merchants;
    }

    public boolean exists(Long id) {
       return merchantRepository.existsById(id);
    }
    public void delete(Long id) {
        Merchant merchant = findById(id);
        merchant.setDeletedAt(new Date());
        merchantRepository.save(merchant);
    }


}
