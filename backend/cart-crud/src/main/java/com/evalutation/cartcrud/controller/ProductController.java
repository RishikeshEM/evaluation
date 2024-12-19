package com.evalutation.cartcrud.controller;

import com.evalutation.cartcrud.model.Product;
import com.evalutation.cartcrud.response.Api;
import com.evalutation.cartcrud.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<Api> getAllProducts(){
        try{
            return ResponseEntity.ok(new Api("all products", productService.getAllProduct()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Api> addProduct(@RequestBody Product product){
        try{
            return ResponseEntity.ok(new Api("new product added", productService.createProduct(product)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Api> addProduct(@PathVariable Long id,@RequestBody Product product){
        try{
            return ResponseEntity.ok(new Api("product updated", productService.updateProduct(id, product)));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Api> deleteProduct(@PathVariable Long id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok(new Api("product deleted",null ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Api(e.getMessage(), null));
        }
    }

}
