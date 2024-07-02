package com.example.HNG_Stage_1.controller;

import com.example.HNG_Stage_1.model.Visitor;
import com.example.HNG_Stage_1.model.WeatherData;
import com.example.HNG_Stage_1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<Visitor> greeting(@RequestParam(name = "visitor_name") String visitor_name, HttpServletRequest request){
        return userService.greetings(visitor_name, request);
    }

}
