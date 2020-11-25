package com.api.caramelo.services.interfaces;

import com.api.caramelo.controllers.dtos.CreateUserDTO;
import com.api.caramelo.controllers.dtos.UpdateUserDTO;
import com.api.caramelo.models.User;

public interface IUserService {

    User search(Long userId);

    User create(CreateUserDTO userDTO);

    User update(UpdateUserDTO userDTO, Long userId);

    void delete(Long userId);
}
