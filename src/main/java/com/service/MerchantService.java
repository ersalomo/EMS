package com.service;

import com.entity.Merchant;
import com.model.MerchantRequest;
import com.repository.MerchantRepository;
import com.util.GenerateUniqueCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public void add(MerchantRequest merchantDao) {
        Merchant merchant = new Merchant();
        merchant.setMerchantCode(GenerateUniqueCode.generateUniqueCode());
        merchant.setMerchantName(merchantDao.getMerchantName());
        merchant.setMerchantLocation(merchantDao.getMerchantLocation());
        merchantRepository.save(merchant);
    }

    public void updateStatus(Long id, boolean status) {
         Optional<Merchant> merchant = merchantRepository.findById(id);
        if (!merchant.isPresent()){
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant not found");
        }
        merchantRepository.updateStatus(id, status);
    }

    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    public boolean exists(Long id) {
       return merchantRepository.existsById(id);
    }
    public void delete(Long id) {
        merchantRepository.deleteById(id);
    }
}
