package com.krishnendu.SimpleWebApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestStripe {
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
}
