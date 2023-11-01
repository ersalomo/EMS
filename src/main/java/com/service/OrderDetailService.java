package com.service;

import com.entity.OrderDetail;
import com.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    @Transactional
    public OrderDetail addNew(OrderDetail orderDetail){
        return  orderDetailRepository.save(orderDetail);
    }
}
