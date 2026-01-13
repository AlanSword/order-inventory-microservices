package com.example.apigateway;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class home {


    @GetMapping
    public String home() {
        return "Home";
    }


}
