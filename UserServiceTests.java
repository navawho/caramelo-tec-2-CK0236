package com.api.caramelo.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.api.caramelo.controllers.dtos.CreateUserDTO;
import com.api.caramelo.controllers.dtos.UpdateUserDTO;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
class UserServiceTests {
	
	@SpyBean
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	
	User user = User.builder()
		.id(1l)
		.username("aaa")
		.password("password")
		.email("email@email.com")
		.phone("phone")
		.build();
	
	CreateUserDTO userDTO = CreateUserDTO.builder()
		.username(user.getUsername())
		.password(user.getPassword())
		.email(user.getEmail())
		.phone(user.getPhone())
		.confirmPassword(user.getPassword())
		.build();
	
	@Test
	void testCreateWithSucess() {
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		
		assertEquals(user, userService.create(userDTO));
	}
	
	@Test
	void testSearchWithSucess() {
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		User soughtUser = userService.search(user.getId());
		
		assertEquals(soughtUser, user);
	}
	
	@Test
	void testSearchWithFailure() throws Exception {
		Long userId = null;
		
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.search(userId);
		});

		 assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
	}

	@Test
	void testUpdateWithSucess() {
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		Mockito.when(userRepository.findById(1l)).thenReturn(Optional.of(user));
		String newUsername = "ismayle";
		UpdateUserDTO userDTO = UpdateUserDTO.builder()
			.username(newUsername)
			.build();
		User alteredUser = userService.update(userDTO, 1l);
		
		assertEquals(alteredUser.getUsername(), newUsername);
	}
	
	@Test
	void testUpdateWithFailure() throws Exception {
		Long userId = null;
		UpdateUserDTO userDTO = UpdateUserDTO.builder()
				.username("username")
				.build();
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.update(userDTO, userId);
		});

		 assertEquals(exception.getMessage(), "Usuário com esse token não existe.");
	}
	
	@Test
	void testDeleteWithFailure() throws Exception {
		Long userId = null;
		
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.delete(userId);
		});

		 assertEquals(exception.getMessage(), "Usuário não existe.");
	}
	
	@Test
	void testValidateAndHashPasswordWithFailure() throws Exception {
		String password = "a";
		String confirmPassword = "b";
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.validateAndHashPassword(password, confirmPassword);
		});
		
		 assertEquals(exception.getMessage(), "Senhas não batem.");
	}
	
	@Test
	void testCheckIfAlreadyExistsWithSucess() {
		userService.checkIfAlreadyExists(user.getUsername(), user.getEmail(), user.getPhone());
	}
	
	@Test
	void testCheckIfAlreadyExistsWithFailure()  throws Exception {
		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
		Mockito.when(userRepository.findByPhone(user.getPhone())).thenReturn(user);
		
		String email = null;
		String phone = null;
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.checkIfAlreadyExists(user.getUsername(), user.getEmail(), user.getPhone());
		});
		
		 assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
	}
	
	@Test
	void testCheckIfAlreadyExistsWithFailureUsername()  throws Exception {
		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
		
		String email = "test@test.failureusername";
		String phone = "phone@failureusername";
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.checkIfAlreadyExists(user.getUsername(), email, phone);
		});
		
		 assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
	}
	
	@Test
	void testCheckIfAlreadyExistsWithFailureEmail()  throws Exception {
		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
		
		String username = "failureEMail";
		String phone = "phone@failureEMail";
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.checkIfAlreadyExists(username, user.getEmail(), phone);
		});
		
		 assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");	
	}
	
	@Test
	void testCheckIfAlreadyExistsWithFailurePhone()  throws Exception {
		Mockito.when(userRepository.findByPhone(user.getPhone())).thenReturn(user);
		
		String username = "failurePhone";
		String email = "test@test.FailurePhone";
		Throwable exception = assertThrows(Exception.class, () -> {
			userService.checkIfAlreadyExists(username, email, user.getPhone());
		});
		
		 assertEquals(exception.getMessage(), "Requisição possui campos inválidos.");
	}

}
