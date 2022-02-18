package com.team7.propertypredict.controller;

import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/mobile/auth")
public class AuthMobileRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> loginFromMobile (@RequestBody User user) {
        Map<String, String> success = new HashMap<>();
        User result = userService.findUserByEmail(user.getEmail());

        if (userService.authenticate(user.getEmail(), user.getPassword()) != null) {
            success.put("login", "1");
            success.put("id", userService.authenticate(user.getEmail(), user.getPassword()).getUserId().toString());
            if (result.getUsername() == null) {
                success.put("name", user.getEmail());
                return success;
            }
            else {
                success.put("name", result.getUsername());
                return success;
            }
        }
        else {
            success.put("login", "0");
            return success;
        }
    }

    @PostMapping("/register")
    public Map<String, Integer> registerFromMobile (@RequestBody User newUser) {
        Map<String, Integer> success = new HashMap<>();

        if (userService.findUserByEmail(newUser.getEmail()) == null) {
            userService.save(newUser);
            success.put("register", 1);
            return success;
        }
        else {
            success.put("register", 0);
            return success;
        }
    }

//    @PostMapping("/name")
//    public Map<String, String> getUserFirstName (@RequestBody User user) {
//        Map<String, String> success = new HashMap<>();
//
//        User result = userService.findUserByEmail(user.getEmail());
//
//        if (result.getUsername() == null) {
//            success.put("name", user.getEmail());
//            return success;
//        }
//        else {
//            success.put("name", result.getUsername());
//            return success;
//        }
//    }

}
