package com.service;


import com.dao.OrderRequest;
import com.entity.Cart;
import com.entity.Order;
import com.entity.Product;
import com.entity.User;
import com.model.CartRequest;
import com.repository.CartRepo;
import com.security.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class CartService {

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    public Cart find(Long id) {
                return this.cartRepo.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found")
                );
    }

    @Transactional
    public Order createOrderFromCart(Long cartId, Long userId) {
        Cart cart = this.find(cartId);
        OrderRequest request = new OrderRequest();
        request.setQty(cart.getQty());
        request.setProductId(cart.getProduct().getId());
        request.setUserId(cart.getUser().getId());
        request.setDestinationAddr("No where"); //hard code
        cartRepo.delete(cart);
        return orderService.addNewOrder(request);
    }

    public List<Cart> getAllCartByCurrentUser(Long userId) {
        List<Cart> carts =  cartRepo.findAllByUser_Id(userId);

        if (carts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found");
        }

        return carts;
    }

    @Transactional
    public Cart store(CartRequest request)  {
        log.info("Cart re {}", request);
        Product product = productService.find(request.getProductId());
        User user = userService.find(request.getCurrentUser());
        // hard code // error db cause user may be not present in table users
        Cart cartCurrent = cartRepo.findByUserIdAndProductId(user.getId(), request.getProductId());
        if  (cartCurrent == null)  {
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setQty(request.getQty()); // setting di db default value
            return cartRepo.save(cart);
        } else {
            Integer currentQty = cartCurrent.getQty();
            cartCurrent.setQty(currentQty + request.getQty());
            return cartRepo.save(cartCurrent);
        }
    }

    public void delete(Long id) {
        cartRepo.deleteById(id);
    }

    public void deleteByUserId(Long userId) {
        cartRepo.deleteById(userId);
    }
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        cartRepo.deleteByUserIdAndProductId(userId, productId);
    }
}
