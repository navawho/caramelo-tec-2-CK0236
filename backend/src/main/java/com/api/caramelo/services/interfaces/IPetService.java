package com.api.caramelo.services.interfaces;

import com.api.caramelo.controllers.dtos.CreatePetDTO;
import com.api.caramelo.controllers.dtos.UpdatePetDTO;
import com.api.caramelo.models.Pet;

import java.util.List;

public interface IPetService {

    List<Pet> search(Long userId, String name, String port, String type, String sex);

    List<Pet> searchMyPets(Long userId);

    Pet create(CreatePetDTO petDTO, Long userId);

    Pet update(UpdatePetDTO petDTO, Long petId, Long userId);

    void delete(Long petId, Long userId);
}
