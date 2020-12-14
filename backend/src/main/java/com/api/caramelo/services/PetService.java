package com.api.caramelo.services;

import com.api.caramelo.controllers.dtos.CreatePetDTO;
import com.api.caramelo.controllers.dtos.UpdatePetDTO;
import com.api.caramelo.controllers.dtos.UpdateUserDTO;
import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.exceptions.error.ErrorObject;
import com.api.caramelo.models.Pet;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.PetRepository;
import com.api.caramelo.repositories.UserRepository;
import com.api.caramelo.services.interfaces.IPetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class PetService implements IPetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    @Override
    public Pet create(CreatePetDTO petDTO, Long userId) {
        this.checkIfAlreadyExists(petDTO.getName());

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        Pet pet = Pet.builder()
                .user(user.get())
                .name(petDTO.getName())
                .description(petDTO.getDescription())
                .imageUrl(petDTO.getImageUrl())
                .sex(petDTO.getSex())
                .type(petDTO.getType())
                .birthDate(petDTO.getBirthDate())
                .available(true)
                .port(petDTO.getPort()).build();

        return petRepository.save(pet);
    }

    @Override
    public Pet update(UpdatePetDTO petDTO, Long petId, Long userId) {
        this.checkIfAlreadyExists(petDTO.getName());

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isEmpty()) {
            throw new BusinessRuleException("Impossível atualizar as informações de um Pet inexistente.");
        }

        Pet pet = optionalPet.get();

        if (userId != pet.getUser().getId()) {
            throw new BusinessRuleException("Um usuário não pode atualizar um Pet de outro usuário");
        }

        if (nonNull(petDTO.getName()) && !(petDTO.getName().equals(pet.getName()))) {
            pet.setName(petDTO.getName());
        }

        if (nonNull(petDTO.getImageUrl())) {
            pet.setImageUrl(petDTO.getImageUrl());
        }

        if (nonNull(petDTO.getDescription())) {
            pet.setDescription(petDTO.getDescription());
        }

        if (nonNull(petDTO.getPort())) {
            pet.setPort(petDTO.getPort());
        }

        if (nonNull(petDTO.getSex())) {
            pet.setSex(petDTO.getSex());
        }

        if (nonNull(petDTO.getAvailable()) && pet.getAvailable()) {
            pet.setAvailable(petDTO.getAvailable());
        }

        return petRepository.save(pet);
    }

    @Override
    public void delete(Long petId, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isEmpty()) {
            throw new BusinessRuleException("Impossível deletar um Pet inexistente.");
        }

        Pet pet = optionalPet.get();

        if (userId != pet.getUser().getId()) {
            throw new BusinessRuleException("Um usuário não pode deletar um Pet de outro usuário");
        }

        petRepository.deleteById(petId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pet> search(Long userId, String name, String port, String type, String sex) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        return petRepository.findPetsToRequest(userId, name, port, type, sex);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pet> searchMyPets(Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        return petRepository.findMyPets(userId);
    }

    public void checkIfAlreadyExists(String name) {
        BusinessRuleException businessRuleException = new BusinessRuleException("Requisição possui campos inválidos.");

        if (nonNull(name) && nonNull(petRepository.findPetByName(name))) {
            businessRuleException.addError(new ErrorObject("Já existe um Pet com esse nome.", "name", name));
        }

        if (businessRuleException.checkHasSomeError()) {
            throw businessRuleException;
        }
    }

}
