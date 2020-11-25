package com.api.caramelo.controllers.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Builder
public class CreateUserDTO {
    @NotBlank(message = "Username é obrigatório.")
    @Size(min = 4, message = "Username deve ter no mínimo 4 caracteres.")
    private String username;

    @NotBlank(message = "Senha é obrigatória.")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres.")
    private String password;

    @NotBlank(message = "Confirmação de senha é obrigatória.")
    private String confirmPassword;

    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @NotBlank(message = "Telefone é obrigatório.")
    @Pattern(message = "Telefone inválido.", regexp = "^(([\\d]{4}[-.\\s]{1}[\\d]{2,3}[-.\\s]{1}[\\d]{2}[-.\\s]{1}[\\d]{2})|([\\d]{4}[-.\\s]{1}[\\d]{3}[-.\\s]{1}[\\d]{4})|((\\(?\\+?[0-9]{2}\\)?\\s?)?(\\(?\\d{2}\\)?\\s?)?\\d{4,5}[-.\\s]?\\d{4}))$")
    private String phone;
}
