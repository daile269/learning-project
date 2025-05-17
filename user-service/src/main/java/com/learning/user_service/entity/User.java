package com.learning.user_service.entity;

import com.learning.user_service.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user",
        uniqueConstraints = {@UniqueConstraint(name = "user_name_unique", columnNames = "user_name")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", updatable = false, length = 50)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = Role.USER;
        }
    }


}
