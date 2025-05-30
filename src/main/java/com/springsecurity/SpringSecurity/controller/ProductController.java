package com.springsecurity.SpringSecurity.controller;

import com.springsecurity.SpringSecurity.entity.UserInfo;
import com.springsecurity.SpringSecurity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome to product page";
    }

    @GetMapping("/product")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String product(){
        return "to product page";
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String products(){
        return " products page";
    }
    @PostMapping("/new")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return productService.addUser(userInfo);
    }

}
