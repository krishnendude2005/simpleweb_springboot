package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.model.Product;
import com.krishnendu.SimpleWebApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // used for accessing this host url from outside
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;


    @GetMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/product/{prodId}")
    public Product getProductById(@PathVariable int prodId) {
        return service.getProductById(prodId);
    }
    @PostMapping("/products")
    public void addProduct(@RequestBody List<Product> products) {
        service.addProduct(products);
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
