package com.krishnendu.SimpleWebApp.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class PayemntURL {

    @GetMapping("/success")
    public String success(){
        return "success";
    }

    @GetMapping
    public String cancel(){
        return "cancel";
    }
}
