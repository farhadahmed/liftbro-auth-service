package com.liftbro.auth.service;

import com.liftbro.auth.entity.OAuthUser;
import com.liftbro.auth.repository.OAuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OAuthUserService {

    private final OAuthUserRepository repository;

    public OAuthUser findOrCreate(String provider, String providerId, String email, String name) {
        return repository.findByProviderAndProviderId(provider, providerId)
                .map(user -> {
                    user.setLastLogin(Instant.now());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    OAuthUser newUser = OAuthUser.builder()
                            .provider(provider)
                            .providerId(providerId)
                            .email(email)
                            .name(name)
                            .build();
                    return repository.save(newUser);
                });
    }
}
