package com.api.caramelo.controllers.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
@Builder
public class UpdatePetDTO {

    @Size(min = 2, message = "O nome do Pet deve ter pelo menos 2 letras!")
    private String name;

    private String description;

    private String imageUrl;

    private String sex;

    private String port;

    private Boolean available;
}
