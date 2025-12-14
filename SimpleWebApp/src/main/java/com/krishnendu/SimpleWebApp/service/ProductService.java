package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.dto.response.ProductWithImageResponse;
import com.krishnendu.SimpleWebApp.exception.ResourceNotFoundException;
import com.krishnendu.SimpleWebApp.model.Product;
import com.krishnendu.SimpleWebApp.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

//    List<Product> products = new ArrayList<>(Arrays.asList(new Product(101, "Iphone", 50000), new Product(102, "Android", 60000), new Product(103, "canon camera", 100000)));
    public List<Product> getProducts() {

        return repo.findAll();
    }

    public Product getProductById(int prodId) {
        return repo.findById(prodId).orElse(null);
    }
    public Boolean addProduct(Product product, MultipartFile productImageFile) {
       try{
           if(product.getStockQuantity() >= 1) {
               product.setAvailable(true);
           }

           product.setImageName(productImageFile.getOriginalFilename());
           product.setImageType(productImageFile.getContentType());
           product.setImageData(productImageFile.getBytes());
            repo.save(product);
           return true;
       }catch(Exception e){
           return false;
       }
    }

    public Boolean changeProduct(Product product) {
        if(product == null) {
            return false;
        } else {
            repo.save(product);
            return true;
        }

    }

    public void deleteProduct(int prodId) {
        repo.deleteById(prodId);
    }

    public byte[] getImage(int prodId) {
        Product product = repo.findById(prodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id : " + prodId)
                        );

        if(product.getImageData() == null) {
            throw new ResourceNotFoundException("Image for Product with id : " + prodId + "not found");

        }

        return product.getImageData();
    }

    public Product updateProduct(Product product, MultipartFile imageFile, int prodId) {
        try {
            Product p = getProductById(prodId);
            p.setImageData(imageFile.getBytes());
            p.setImageName(imageFile.getOriginalFilename());
            p.setImageType(imageFile.getContentType());
            return repo.save(p);
        }catch(Exception e){
            throw new ResourceNotFoundException("Some Issue to update image of Product with id : " + prodId);
        }

    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
