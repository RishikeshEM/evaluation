package com.evalutation.cartcrud.controller;

import com.evalutation.cartcrud.dto.OrderDto;
import com.evalutation.cartcrud.model.Product;
import com.evalutation.cartcrud.response.Api;
import com.evalutation.cartcrud.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping("/getOrders")
    public ResponseEntity<Api> getAllProducts(){
        try{
            return ResponseEntity.ok(new Api("all orderes", cartService.getAllOrders()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}/getOrders")
    public ResponseEntity<Api> getByUser(@PathVariable Long id){
        try{
            return ResponseEntity.ok(new Api("all products", cartService.getAllOrders()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Api> addProduct(@RequestBody OrderDto dto){
        try{
            return ResponseEntity.ok(new Api("new product added", cartService.createOrder(dto)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Api> updateOrder(@PathVariable Long id, @RequestParam int qty){
        try{
            return ResponseEntity.ok(new Api("product updated", cartService.updateOrder(id,  qty )));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

    @PutMapping("/cancelOrder/{id}")
    public ResponseEntity<Api> cancelOrder(@PathVariable Long id){
        try{
            return ResponseEntity.ok(new Api("product updated", cartService.cancelOrder(id )));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Api> deleteProduct(@PathVariable Long id){
//        try{
//            productService.deleteProduct(id);
//            return ResponseEntity.ok(new Api("product deleted",null ));
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
//        }
//    }
}
