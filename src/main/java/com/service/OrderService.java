package com.service;

import com.dao.OrderRequest;
import com.entity.Order;
import com.entity.OrderDetail;
import com.entity.Product;
import com.entity.User;
import com.repository.OrderRepository;
import com.util.Util;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

@Autowired
private OrderRepository orderRepository;

@Autowired
private UserService userService;

@Autowired
private ProductService productService;

@Autowired
private OrderDetailService orderDetailService;


    @Transactional
    public Order addNewOrder(OrderRequest req) {
        User user = userService.find(req.getUserId());
        Product product = productService.find(req.getProductId());

        Order order = new Order();
        order.setUser(user);
        order.setOrderTime(Util.getCurrentDate());
        order.setDestinationAddr(req.getDestinationAddr());
        Order saveOrder =  orderRepository.save(order);

        int qty = req.getQty();
        double price = product.getPrice();
        double totalPrice  = Util.calculateTotal(qty, price);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(saveOrder);
        orderDetail.setProduct(product);
        orderDetail.setQty(qty);
        orderDetail.setTotalPrice(totalPrice);
        orderDetailService.addNew(orderDetail);
        return order;
    }

    public Order find(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found!")
        );
    }

    public Order findDetailOrder(Long id) {
        return orderRepository.findOrderWithUserAndOrderDetail(id);
    }


    public List<Order> getAllOrderByUserId(Long userId) {
        return orderRepository.findAllByUser_Id(userId);
    }
}
