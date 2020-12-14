package com.api.caramelo.services;

import com.api.caramelo.controllers.dtos.CreatePetDTO;
import com.api.caramelo.controllers.dtos.UpdatePetDTO;
import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.Pet;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.PetRepository;
import com.api.caramelo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TestPetService {

    @SpyBean
    PetService petService;

    @MockBean
    PetRepository petRepository;

    @MockBean
    UserRepository userRepository;

    @Test
    public void testCreatePetWithSucess() {
        Date date = new Date();
        CreatePetDTO petDTO = CreatePetDTO.builder()
                .name("Rex")
                .sex("Macho")
                .imageUrl("url")
                .description("descrição")
                .port("Pequeno")
                .type("Cachorro")
                .birthDate(date)
                .build();
        Optional<User> user = Optional.of(new User());
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

        Mockito.when(userRepository.findById(1l)).thenReturn(user);
        Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenReturn(pet);

        Pet savedPet = petService.create(petDTO, 1l);

        assertEquals(savedPet, pet);
    }

    @Test
    public void testUserThatDoesNotExistsAtCreate() {
        CreatePetDTO petDTO = CreatePetDTO.builder().build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> petService.create(petDTO, 1l));

        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testUpdatePetNameWithSucess() {
        User user = User.builder().id(1l).build();
        Pet pet = Pet.builder().id(1l).name("Rex").user(user).build();
        UpdatePetDTO petDTO = UpdatePetDTO.builder().name("Rick").build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Mockito.when(petRepository.findById(1l)).thenReturn(Optional.of(pet));
        Mockito.when(petRepository.save(Mockito.any(Pet.class))).thenReturn(pet);

        Pet updatedPet = petService.update(petDTO, 1l, 1l);

        assertEquals(updatedPet.getName(), petDTO.getName());
    }

    @Test
    public void testUserThatDoesNotExistsAtUpdate() {
        UpdatePetDTO petDTO = UpdatePetDTO.builder().build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> petService.update(petDTO, 1l, 1l));

        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testTryToUpdateAPetThatDoesNotBelongsToMe() {
        User user1 = User.builder().id(1l).build();
        User user2 = User.builder().id(2l).build();
        Pet petFromUser1 = Pet.builder().id(1l).name("Rex").user(user1).build();
        UpdatePetDTO petDTO = UpdatePetDTO.builder().name("Rick").build();
        Mockito.when(userRepository.findById(2l)).thenReturn(Optional.of(user2));
        Mockito.when(petRepository.findById(1l)).thenReturn(Optional.of(petFromUser1));

        Throwable exception = assertThrows(BusinessRuleException.class, () -> petService.update(petDTO, 1l, 2l));

        assertEquals(exception.getMessage(), "Um usuário não pode atualizar um Pet de outro usuário");
    }

    @Test
    public void testPetThatDoesNotExistsAtUpdate() {
        UpdatePetDTO petDTO = UpdatePetDTO.builder().build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(new User()));
        Mockito.when(petRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> petService.update(petDTO, 1l, 1l));

        assertEquals(exception.getMessage(), "Impossível atualizar as informações de um Pet inexistente.");
    }

    @Test
    public void testDeletePetWithSucess() {
        User user = User.builder().id(1l).build();
        Pet pet = Pet.builder().user(user).build();
        Mockito.when(petRepository.findById(1l)).thenReturn(Optional.of(pet));
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> petService.delete(1l, 1l));
    }

    @Test
    public void testPetThatDoesNotExistsAtDelete() {
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(new User()));
        Mockito.when(petRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> petService.delete(1l, 1l));

        assertEquals(exception.getMessage(), "Impossível deletar um Pet inexistente.");
    }

    @Test
    public void testUserThatDoesNotExistsAtDelete() {
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> petService.delete(1l, 1l));

        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testTryToDeleteAPetThatDoesNotBelongsToMe() {
        User user1 = User.builder().id(1l).build();
        User user2 = User.builder().id(2l).build();
        Pet petFromUser1 = Pet.builder().user(user1).build();
        Mockito.when(petRepository.findById(1l)).thenReturn(Optional.of(petFromUser1));
        Mockito.when(userRepository.findById(2l)).thenReturn(Optional.of(user2));

        Throwable exception = assertThrows(BusinessRuleException.class, () -> petService.delete(1l, 2l));

        assertEquals(exception.getMessage(), "Um usuário não pode deletar um Pet de outro usuário");
    }

    @Test
    public void testSearchPetsWithSucess() {
        User user = User.builder().id(1l).build();
        List<Pet> pets = new ArrayList<>();
        Mockito.when(petRepository.findPetsToRequest(1l, "", "", "", "")).thenReturn(pets);
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        List<Pet> filteredPets = petService.search(1l, "", "", "", "");

        assertEquals(pets, filteredPets);
    }

    @Test
    public void testUserThatDoesNotExistsAtSearch() {
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () ->  petService.search(1l, "", "", "", ""));

        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testSearchMyPetsWithSucess() {
        User user = User.builder().id(1l).build();
        List<Pet> pets = new ArrayList<>();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Mockito.when(petRepository.findMyPets(1l)).thenReturn(pets);

        List<Pet> myPets = petService.searchMyPets(1l);

        assertEquals(pets, myPets);
    }

    @Test
    public void testUserThatDoesNotExistsAtSearchMyPets() {
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () ->  petService.searchMyPets(1l));

        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testPetNameThatAlreadyExists() {
        Pet pet = Pet.builder().name("Rick").build();
        Mockito.when(petRepository.findPetByName("Rick")).thenReturn(pet);

        Throwable exception = assertThrows(BusinessRuleException.class, () ->  petService.checkIfAlreadyExists("Rick"));

        assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
    }

    @Test
    public void testPetNameThatDoesNotExists() {
        Mockito.when(petRepository.findPetByName("Rick")).thenReturn(null);

        assertDoesNotThrow(() -> petService.checkIfAlreadyExists("Rick"));
    }
}
