package com.api.caramelo.controllers.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class UpdateUserDTO {
    @Size(min = 4, message = "Username deve ter no mínimo 4 caracteres.")
    private String username;

    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres.")
    private String password;

    private String confirmPassword;

    private String oldPassword;

    @Email(message = "E-mail inválido.")
    private String email;

    @Pattern(regexp = "^(([\\d]{4}[-.\\s]{1}[\\d]{2,3}[-.\\s]{1}[\\d]{2}[-.\\s]{1}[\\d]{2})|([\\d]{4}[-.\\s]{1}[\\d]{3}[-.\\s]{1}[\\d]{4})|((\\(?\\+?[0-9]{2}\\)?\\s?)?(\\(?\\d{2}\\)?\\s?)?\\d{4,5}[-.\\s]?\\d{4}))$")
    private String phone;
}
