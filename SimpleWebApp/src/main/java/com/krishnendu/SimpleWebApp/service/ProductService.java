package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    List<Product> products = new ArrayList<>(Arrays.asList(new Product(101, "Iphone", 50000), new Product(102, "Android", 60000), new Product(103, "canon camera", 100000)));
    public List<Product> getProducts() {

        return products;
    }

    public Product getProductById(int prodId) {
        return products.stream()
                .filter(prod -> prod.getProdId() == prodId)
                .findFirst().get();
    }
    public void addProduct(Product product) {
        List<Product> updatedList = getProducts();
        updatedList.add(product);
        System.out.println("Product added successfully");
    }
}
