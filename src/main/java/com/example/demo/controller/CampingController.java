package com.example.demo.controller;

import com.example.demo.domain.Camping;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CampingController {

    @Autowired
    private ProductService productService;

}
