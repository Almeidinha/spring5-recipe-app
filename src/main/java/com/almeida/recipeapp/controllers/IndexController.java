package com.almeida.recipeapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping()
public class IndexController {

    @RequestMapping({"", "/", "/index"})
    public String getIndex() {
        System.out.println("sup!!!!");
        return "index";
    }
}
