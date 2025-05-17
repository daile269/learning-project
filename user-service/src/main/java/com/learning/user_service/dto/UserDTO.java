package com.learning.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 50 characters ")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, max = 100, message = "Password must be between 5 and 50 characters ")
    private String password;
    private String role;
}
