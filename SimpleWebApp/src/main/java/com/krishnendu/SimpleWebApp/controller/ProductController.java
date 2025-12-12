package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.model.Product;
import com.krishnendu.SimpleWebApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin // used for accessing this host url from outside
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;


    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        List<Product> products = service.getProducts();

        if(products == null || products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{prodId}")
    public ResponseEntity<?> getProductById(@PathVariable int prodId) {
        Product product = service.getProductById(prodId);

        if(product == null) {
            return ResponseEntity.noContent()   .build();
        }else {
            return ResponseEntity.ok(product);
        }
    }
    @GetMapping("/product/{prodId}/image")
    public ResponseEntity<?> getImage(@PathVariable int prodId) {
       try{
           return ResponseEntity.ok(service.getImage(prodId));
       }catch(Exception e) {
           return ResponseEntity.noContent().build();
       }
    }
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart("product") Product product,
                                        @RequestPart("imageFile") MultipartFile productImageFile) {
        Boolean added = service.addProduct(product, productImageFile);
        if(!added) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/products")
    public void changeProduct(@RequestBody Product product) {
        service.changeProduct(product);
    }

    @DeleteMapping("/products/{prodId}")
    public void deleteProduct(@PathVariable int prodId) {
        service.deleteProduct(prodId);
    }
}
