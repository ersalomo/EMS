package com.service;


import com.entity.Cart;
import com.entity.Product;
import com.entity.User;
import com.model.CartRequest;
import com.repository.CartRepo;
import com.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CartService {

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductService productService;
    public void store(CartRequest request)  {
        Product product = productService.find(request.getProductId());
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,  "Product Not Found");
        }

        // hard code // error db cause user may be not present in table users
        Cart cartCurrent = cartRepo.findByUserIdAndProductId(1L, request.getProductId());
        if  (cartCurrent == null)  {
            User user = new User();
            user.setId(1L);
            user.setUsername("Test-12345");
            user.setEmail("test-tester@gmail.com");
            user.setPassword(BCrypt.hashpw("12345678", BCrypt.gensalt()));
            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setUser(user);
            cart.setQty(request.getQty()); // setting di db default value
            cartRepo.save(cart);
        } else {
            Long currentQty = cartCurrent.getQty();
            cartCurrent.setQty(currentQty + request.getQty());
            cartRepo.save(cartCurrent);
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
