package com.krishnendu.SimpleWebApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentURL {

    @GetMapping("/success")
    public String success(){
        return "Payment done ✅";
    }

    @GetMapping("/cancel")
    public String cancel(){
        return "Payment cancel ❌";
    }
}
