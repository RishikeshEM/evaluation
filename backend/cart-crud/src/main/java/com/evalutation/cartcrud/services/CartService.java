package com.evalutation.cartcrud.services;

import com.evalutation.cartcrud.dto.OrderDto;
import com.evalutation.cartcrud.model.Cart;
import com.evalutation.cartcrud.model.Product;
import com.evalutation.cartcrud.model.User;
import com.evalutation.cartcrud.repository.CartRepository;
import com.evalutation.cartcrud.repository.ProductRepository;
import com.evalutation.cartcrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public List<Cart> getAllOrders() {
        return cartRepository.findAll();
    }

    public Cart createOrder(OrderDto dto){
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("user  not found"));
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("product not found"));

        if(product.getStock() < dto.getQuantity()){
            throw new RuntimeException("stock is less");
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setOrderQuantity(dto.getQuantity());
        cart.setTotalPrice(dto.getQuantity() * product.getPrice());
        cartRepository.save(cart);
        product.setStock(product.getStock()  - dto.getQuantity());
        productRepository.save(product);

        return cart;
    }

    public Cart updateOrder(Long id, int qty) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new RuntimeException("no cart found"));
        Product product = productRepository.findById(cart.getProduct().getId()).orElseThrow(()-> new RuntimeException("no product"));

        if(product.getStock() < qty){
            throw new RuntimeException("stock is less");
        }

        cart.setOrderQuantity(qty);
        cart.setTotalPrice(qty * product.getPrice());
        product.setStock(product.getStock()-qty);
        System.out.println(product.getStock()-qty);


        productRepository.save(product);
        cartRepository.save(cart);
        return cart;
    }

    public Product cancelOrder(Long id){
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new RuntimeException("cart not found"));
        Product product = productRepository.findById(cart.getProduct().getId()).orElseThrow(()-> new RuntimeException("product not found"));
        product.setStock(cart.getOrderQuantity() + product.getStock());

        cartRepository.delete(cart);
        productRepository.save(product);

        return product;

    }
}
