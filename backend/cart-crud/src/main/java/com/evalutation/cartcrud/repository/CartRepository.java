package com.evalutation.cartcrud.repository;

import com.evalutation.cartcrud.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
