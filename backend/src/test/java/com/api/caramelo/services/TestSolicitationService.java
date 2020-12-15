package com.api.caramelo.services;

import com.api.caramelo.controllers.dtos.UpdatePetDTO;
import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.Pet;
import com.api.caramelo.models.Solicitation;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.AdoptionRepository;
import com.api.caramelo.repositories.PetRepository;
import com.api.caramelo.repositories.SolicitationRepository;
import com.api.caramelo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TestSolicitationService {
        @SpyBean
        SolicitationService solicitationService;

        @MockBean
        SolicitationRepository solicitationRepository;

        @MockBean
        UserRepository userRepository;

        @MockBean
        PetRepository petRepository;

        @MockBean
        AdoptionRepository adoptionRepository;

    /*Teste de sucesso: Cria a solicitação com sucesso
    Teste de erro: Usuário com tal id não existe
    Teste de erro: Pet com tal id não existe*/

        @Test
        public void testCreateSolicitationSuccess() {

            long userId = 1l;
            long petId = 1l;

            User user = new User();
            Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

            Pet pet = new Pet();
            Mockito.when(petRepository.findById(petId)).thenReturn(java.util.Optional.of(pet));

            Solicitation solicitation = new Solicitation();
            Mockito.when(solicitationRepository.save(Mockito.any(Solicitation.class))).thenReturn(solicitation);

            Solicitation createdSolicitation = solicitationService.create(userId, petId);

            assertEquals(createdSolicitation, solicitation);

        }

        @Test
        public void testUserThatDoesNotExists() {

            Mockito.when(userRepository.findById(1l)).thenReturn(java.util.Optional.empty());

            Throwable exception = assertThrows(BusinessRuleException.class, () -> solicitationService.create(1l, 1l));
            assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
        }

        @Test
        public void testPetThatDoesNotExists() {

            Mockito.when(userRepository.findById(1l)).thenReturn(java.util.Optional.of(new User()));
            Mockito.when(petRepository.findById(1l)).thenReturn(java.util.Optional.empty());

            Throwable exception = assertThrows(BusinessRuleException.class, () -> solicitationService.create(1l, 1l));
            assertEquals(exception.getMessage(), "Pet não existe.");
        }
/*
    Teste de sucesso: Atualiza a solicitação com sucesso
    Teste de sucesso: Caso a solicitação seja aceita, cria uma adoção com o solicitante e o pet e seta a disponibilidade do pet como falsa
    Teste de erro: Usuário com tal id não existe */


    @Test
    public void testUpdateSolicitationService() {
        long solicitationId = 1l;

        Solicitation solicitation = new Solicitation();
        Mockito.when(solicitationRepository.findById(solicitationId)).thenReturn(java.util.Optional.of(solicitation));

        Solicitation solicitationUpdate = solicitationService.update(1l, true);

        assertEquals(solicitationUpdate.getAccepted(), true);
    }

    @Test
    public void testSolicitationDoesNotExist() {
        long solicitationId = 1l;
        UpdatePetDTO petDTO = UpdatePetDTO.builder().build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> solicitationService.update(solicitationId, true));

        assertEquals(exception.getMessage(), "Solicitação não existe.");
    }

    @Test
    public void testDeleteUserWithSucess() {
        User user = User.builder().id(1l).build();

        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> solicitationService.delete(1l, 1l));
    }

    @Test
    public void testDeleteSolicitationWithSucess() {
        User user = User.builder().id(1l).build();

        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> solicitationRepository.deleteById(1l));
    }

    @Test
    public void testFindSolicitationById() {
        User user = User.builder().id(1l).build();

        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> solicitationRepository.findSolicitationsByUserId(1l));
    }

    @Test
    public void testNotFindSolicitationById() {
        User user = User.builder().id(1l).build();

        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Throwable exception = assertThrows(BusinessRuleException.class, () -> solicitationService.search(2l));
        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testCannotFindPetSolicitationsUser() {
        User user = User.builder().id(1l).build();

        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Throwable exception = assertThrows(BusinessRuleException.class, () -> solicitationService.searchPetSolicitations(2l, 1l));
        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testCannotFindPetSolicitationsPet() {
        User user = User.builder().id(1l).build();
        Pet pet = Pet.builder().id(1l).build();
        Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
        Mockito.when(petRepository.findById(1l)).thenReturn(Optional.of(pet));

        Throwable exception = assertThrows(BusinessRuleException.class, () -> solicitationService.searchPetSolicitations(1l, 2l));
        assertEquals(exception.getMessage(), "Pet não existe.");
    }

    @Test
    public void testSearchPetSolicitation() {
        long userId = 1l;
        long petId = 1l;

        User user = new User();
        Mockito.when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        Pet pet = new Pet();
        Mockito.when(petRepository.findById(petId)).thenReturn(java.util.Optional.of(pet));

        Solicitation solicitation = new Solicitation();
        Mockito.when(solicitationRepository.save(Mockito.any(Solicitation.class))).thenReturn(solicitation);

        Solicitation createdSolicitation = (Solicitation) solicitationService.searchPetSolicitations(1l, 1l);

        assertEquals(createdSolicitation, solicitation);
    }






}
