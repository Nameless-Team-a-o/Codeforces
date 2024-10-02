package com.nameless.dto.auth;

import com.nameless.entity.user.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {

    @NotBlank(message = "Username cannot be null or blank")
    private String username;

    @NotBlank(message = "Email cannot be null or blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Role cannot be null")
    private Role role;

}