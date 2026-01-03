package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.model.Product;
import com.krishnendu.SimpleWebApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin // used for accessing this host url from outside
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

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
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok(product);
        }
    }
    @GetMapping("/product/{prodId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int prodId) {
        Product product = service.getProductById(prodId); // may throw exception
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageFile);
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
    @PutMapping("/product/{prodId}")
    public ResponseEntity<String> updateProduct(@PathVariable int prodId,
                                                @RequestPart("product") Product product,
                                                @RequestPart(value = "imageFile", required = false) MultipartFile productImageFile) {

        Product product1 = service.updateProduct(product, productImageFile, prodId);
        if(product1 != null) {
            return new ResponseEntity<>("Updated",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update Product", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/products/search")
public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        if(products != null) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
}
    @DeleteMapping("/product/{prodId}")
    public void deleteProduct(@PathVariable int prodId) {
        service.deleteProduct(prodId);
    }
}
