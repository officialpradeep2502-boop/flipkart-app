package com.flipkartclone.backend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkartclone.backend.entity.User;
import com.flipkartclone.backend.security.JwtUtil;
import com.flipkartclone.backend.service.UserService;
import jdk.jfr.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    //constructor injection
    public AuthController(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User user){
        return userService.register(user);

    }

    @PostMapping(value="/login",consumes = "application/json",
    produces = "application/json"
            )
    public ResponseEntity<Map<String,String>>  login(@RequestBody String input) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> inputMap = mapper.readValue(input, HashMap.class);
        String email = inputMap.get("email");
        String password = inputMap.get("password");
        User user = userService.findByEmail(email);

        if (user != null && userService.checkPassword(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            Map<String,String> resp = new HashMap<>();
            resp.put("token", token);
            return ResponseEntity.ok(resp); // return JSON { "token": "..." }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, secured world!";
    }

}
