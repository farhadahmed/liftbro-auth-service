package com.liftbro.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "oauth_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "providerId"})
})
public class OAuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;

    private String provider;      // e.g., "google"
    private String providerId;    // e.g., Google sub claim

    private Instant createdAt;
    private Instant lastLogin;

    @PrePersist
    protected void onCreate() {
        this.createdAt =  Instant.now();
        this.lastLogin = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastLogin = Instant.now();
    }
}
