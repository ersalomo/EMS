package com.service;

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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    @PersistenceContext
    private EntityManager em;

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

    public Page<Merchant> findAll(Pageable pageable) {
        return merchantRepository.findAll(pageable);
    }
    public List<Merchant> findAll(MerchantParamReq req) {

        int page = Math.max(req.getPage(), 1);
        int size = req.getSize();
        int offset = (page - 1) * size;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Merchant> criteriaQuery = criteriaBuilder.createQuery(Merchant.class);
        Root<Merchant> root = criteriaQuery.from(Merchant.class);
        criteriaQuery.select(root);

        List<Predicate> andPredicates = new ArrayList<>();
        List<Predicate> orPredicates = new ArrayList<>();

        if (req.getName() != null) {
            Expression<String> nameExpression = criteriaBuilder.lower(root.get("merchantName"));
            orPredicates.add(criteriaBuilder.like(nameExpression, "%" + req.getName().toLowerCase() + "%"));
        }

        if (req.getLocation() != null) {
            Expression<String> locationExpression = criteriaBuilder.lower(root.get("merchantLocation"));
            orPredicates.add(criteriaBuilder.like(locationExpression, "%" + req.getLocation().toLowerCase() + "%"));
        }

        if (req.getOpen() == 1 || req.getOpen() == 0) {
            boolean isOpen = req.getOpen() == 1;
            andPredicates.add(criteriaBuilder.equal(root.get("isOpen"), isOpen));
        }


        Predicate andPredicate = criteriaBuilder.and(andPredicates.toArray(new Predicate[0]));
        Predicate orPredicate = criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));
        Predicate finalPredicate = criteriaBuilder.and(andPredicate, orPredicate);

        criteriaQuery.where(finalPredicate);
        log.info("Query" + " criteriaQuery " + criteriaQuery.toString() + " finalPredicate " + finalPredicate.toString() );
        TypedQuery<Merchant> query = em.createQuery(criteriaQuery);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }
    public List<Merchant> findAllBy(MerchantParamReq req) {
        int size = req.getSize();
        int offset = (req.getPage() - 1) * size;
        String queryString = "SELECT m FROM Merchant m";
        if (req.getName() != null || req.getLocation() != null || req.getOpen() == 1) {
            queryString += "WHERE m.merchantCode LIKE %?1% OR m.merchantLocation LIKE %?2%";
        }

        TypedQuery<Merchant> query = em.createQuery(queryString, Merchant.class);
        query.setFirstResult(offset);
        query.setMaxResults(size);

        return query.getResultList();
    }

    public boolean exists(Long id) {
       return merchantRepository.existsById(id);
    }
    public void delete(Long id) {
        Merchant merchant = findById(id);
        merchantRepository.save(merchant);
    }



    public void generateReportingMerchant() {

    }
}
