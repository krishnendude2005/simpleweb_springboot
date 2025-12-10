package com.krishnendu.SimpleWebApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Entity
@Data
@NoArgsConstructor   // JPA requires a no-args constructor
@AllArgsConstructor
public class Product {
    @Id
    private int prodId;
    private String prodName;
    private int price;
}
