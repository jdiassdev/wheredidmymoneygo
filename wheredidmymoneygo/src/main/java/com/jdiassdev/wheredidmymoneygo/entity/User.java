package com.jdiassdev.wheredidmymoneygo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-icrement
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Column(name = "monthly_salary", nullable = false)
    private BigDecimal monthlySalary = BigDecimal.ZERO;

    @Column(name = "expensive_threshold", nullable = false)
    private BigDecimal expensiveThreshold = BigDecimal.ZERO;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
