package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.model.RazorpayOrderRequest;
import com.krishnendu.SimpleWebApp.model.RazorpayResponse;
import com.krishnendu.SimpleWebApp.service.RazorpayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/razorpay")
public class RazorpayController {


    private final RazorpayService razorpayService;

    public RazorpayController(RazorpayService razorpayService) {
        this.razorpayService = razorpayService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutProduct(@RequestBody RazorpayOrderRequest razorpayOrderRequest) {

        RazorpayResponse razorpayResponse = razorpayService.checkoutProduct(razorpayOrderRequest);
       if(razorpayResponse == null) {
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body("Razorpay response is null");
       }

       return ResponseEntity.status(HttpStatus.OK)
               .body(razorpayResponse);

    }
}
