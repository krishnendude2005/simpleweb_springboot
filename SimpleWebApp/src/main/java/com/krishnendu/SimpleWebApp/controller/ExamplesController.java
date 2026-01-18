package com.krishnendu.SimpleWebApp.controller;

import com.krishnendu.SimpleWebApp.exception.CustomCheckedException;
import com.krishnendu.SimpleWebApp.exception.CustomUncheckedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExamplesController {

    @GetMapping("/checked-exception")
    public CustomCheckedException checkedException(){
        try {
            return new CustomCheckedException("checked exception");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/uncheked-esception")
    public CustomUncheckedException unchekedException(){
        return new CustomUncheckedException("uncheked exception");
    }
}
