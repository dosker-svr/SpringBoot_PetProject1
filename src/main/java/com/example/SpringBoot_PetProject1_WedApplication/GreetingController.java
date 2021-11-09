package com.example.SpringBoot_PetProject1_WedApplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @GetMapping("/greeting")
    public String getGreeting(@RequestParam(required=false, defaultValue="WRLD") String name,
                              Map<String, Object> model) { //model - это то, куда складываем данные, возвращаемые пользователю
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String baseBase(Map<String, Object> model) {
        model.put("info", "From GetMapping 'baseBase'");
        return "base";
    }
}
