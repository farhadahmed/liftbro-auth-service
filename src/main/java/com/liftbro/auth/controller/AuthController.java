package com.liftbro.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

public class AuthController {

    // Returns the logged-in user's basic info once login successful
    @GetMapping("/auth/me")
    public Principal getUser(Principal principal) {
        return principal;
    }
}
