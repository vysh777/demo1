package com.example.demo.controller;

import com.example.demo.ProductService;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.dto.ErrorDto;
import java.util.List;

@RestController
public class ProductController {


    private ProductService productservice;

    public ProductController(@Qualifier("selfProductService") ProductService productservice) {
        this.productservice = productservice;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productservice.createProduct(product);
        if (createdProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } else {
            return ResponseEntity.badRequest().build(); // Return bad request if product creation failed
        }
    }

    @GetMapping("/products/{id}")
    //public Product getProduct(@PathVariable("id") Long productid){
        public ResponseEntity<Product> getProduct(@PathVariable("id") Long productid) throws ProductNotFoundException
       {
           Product currentProduct = productservice.getSingleProduct(productid);

           ResponseEntity<Product> res = new ResponseEntity<>(
                   currentProduct, HttpStatus.OK);

           //throw new RuntimeException();
           return res;
       }

    //}

    @GetMapping("/products")
    public List<Product> getAllProducts(){

        return productservice.getAllProducts();
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productservice.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productservice.deleteProduct(id);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception e) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(e.getMessage());

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

}
