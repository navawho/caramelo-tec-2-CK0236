package com.api.caramelo.services;

import com.api.caramelo.controllers.dtos.CreateUserDTO;
import com.api.caramelo.controllers.dtos.UpdateUserDTO;
import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.User;
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
public class TestUserService {

    @SpyBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    public void testCreateUserWithSucess() {
        CreateUserDTO userDTO = CreateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("123456")
                .phone("85998348765")
                .build();
        User user = new User();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.create(userDTO);

        assertEquals(savedUser, user);
    }

    @Test
    public void testCreateUserWithEmailThatAlreadyExists() {
        CreateUserDTO userDTO = CreateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("123456")
                .phone("85998348765")
                .build();
        User userWithSameEmail = User.builder().email("email@email.com").build();

        Mockito.when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(userWithSameEmail);

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.create(userDTO));

        assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
    }

    @Test
    public void testCreateUserWithUsernameThatAlreadyExists() {
        CreateUserDTO userDTO = CreateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("123456")
                .phone("85998348765")
                .build();
        User userWithSameUsername = User.builder().email("username").build();

        Mockito.when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.ofNullable(userWithSameUsername));

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.create(userDTO));

        assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
    }

    @Test
    public void testCreateUserWithPhoneThatAlreadyExists() {
        CreateUserDTO userDTO = CreateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("123456")
                .phone("85998348765")
                .build();
        User userWithSamePhone = User.builder().email("85998348765").build();

        Mockito.when(userRepository.findByPhone(userDTO.getPhone())).thenReturn(userWithSamePhone);

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.create(userDTO));

        assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
    }

    @Test
    public void testCreateUserWithWrongPasswordConfirmation() {
        CreateUserDTO userDTO = CreateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("1234567")
                .phone("85998348765")
                .build();

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.create(userDTO));

        assertEquals(exception.getMessage(), "Senhas não batem.");
    }

    @Test
    public void testUpdateUserWithSucess() {
        Long userId = 1l;
        User user = new User();
        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("1234567")
                .phone("85998348765")
                .build();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.update(userDTO, userId);

        assertEquals(updatedUser, user);
    }

    @Test
    public void testUpdateUserThatDoesNotExists() {
        Long userId = 1l;
        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("1234567")
                .phone("85998348765")
                .build();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.update(userDTO, userId));

        assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
    }

    @Test
    public void testUpdateUserWithEmailThatAlreadyExists() {
        Long userId = 1l;
        User user = new User();
        User userThatAlreadyExists = new User();
        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("1234567")
                .phone("85998348765")
                .build();

        Mockito.when(userRepository.findByEmail(userDTO.getEmail())).thenReturn(userThatAlreadyExists);

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.update(userDTO, userId));

        assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
    }

    @Test
    public void testUpdateUserWithUsernameThatAlreadyExists() {
        Long userId = 1l;
        User user = new User();
        User userThatAlreadyExists = new User();
        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("1234567")
                .phone("85998348765")
                .build();

        Mockito.when(userRepository.findByUsername(userDTO.getUsername())).thenReturn(Optional.of(userThatAlreadyExists));

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.update(userDTO, userId));

        assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
    }

    @Test
    public void testUpdateUserWithPhoneThatAlreadyExists() {
        Long userId = 1l;
        User user = new User();
        User userThatAlreadyExists = new User();
        UpdateUserDTO userDTO = UpdateUserDTO.builder()
                .username("username")
                .email("email@email.com")
                .password("123456")
                .confirmPassword("1234567")
                .phone("85998348765")
                .build();

        Mockito.when(userRepository.findByPhone(userDTO.getPhone())).thenReturn(userThatAlreadyExists);

        Throwable exception = assertThrows(BusinessRuleException.class, () -> userService.update(userDTO, userId));

        assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
    }
}
