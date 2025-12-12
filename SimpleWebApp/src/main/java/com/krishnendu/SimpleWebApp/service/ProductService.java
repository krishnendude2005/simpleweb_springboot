package com.krishnendu.SimpleWebApp.service;

import com.krishnendu.SimpleWebApp.model.Product;
import com.krishnendu.SimpleWebApp.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    public byte[] getImage(int prodId) throws IOException {
        return repo.findById(prodId).get().getImageData();
    }
}
