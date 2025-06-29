package com.liftbro.auth.controller;

import com.liftbro.auth.entity.OAuthUser;
import com.liftbro.auth.service.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuthUserService oAuthUserService;

    @GetMapping("/auth/me")
    public OAuthUser getUser(Principal principal) {
        if (!(principal instanceof OAuth2AuthenticationToken authenticationToken)) {
            throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
        }

        OAuth2User oauth2User = authenticationToken.getPrincipal();

        // Get provider name like "google"
        String provider = authenticationToken.getAuthorizedClientRegistrationId();

        // Extract attributes from the OAuth2User
        String providerId = oauth2User.getAttribute("sub");         // Unique user ID from Google
        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        return oAuthUserService.findOrCreate(provider, providerId, email, name);
    }

    @GetMapping("/")
    public String getDashboard() {
        return "<h1>Your Dashboard</h1>";
    }
}
