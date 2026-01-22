package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.model.ProductRequestStripe;
import com.krishnendu.SimpleWebApp.model.StripeResponse;
import com.krishnendu.SimpleWebApp.service.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class StripeController {


    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkoutProducts(@RequestBody ProductRequestStripe productRequestStripe) {
        StripeResponse response = stripeService.checkoutProducts(productRequestStripe);

        if (response != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
}
