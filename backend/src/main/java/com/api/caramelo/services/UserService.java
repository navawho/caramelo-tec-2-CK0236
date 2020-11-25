package com.api.caramelo.services;

import com.api.caramelo.controllers.dtos.CreateUserDTO;
import com.api.caramelo.controllers.dtos.UpdateUserDTO;
import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.exceptions.error.ErrorObject;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.UserRepository;
import com.api.caramelo.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.mindrot.jbcrypt.BCrypt.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository repository;

    @Override
    public User create(CreateUserDTO userDTO) {
        this.checkIfAlreadyExists(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPhone());

        String hashedPassword = this.validateAndHashPassword(userDTO.getPassword(), userDTO.getConfirmPassword());

        User user = User.builder()
                .username(userDTO.getUsername())
                .password(hashedPassword)
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone()).build();

        return repository.save(user);
    }

    @Override
    public User update(UpdateUserDTO userDTO, Long userId) {
        this.checkIfAlreadyExists(userDTO.getUsername(), userDTO.getEmail(), userDTO.getPhone());

        Optional<User> optionalUser = repository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        User user = optionalUser.get();

        if (nonNull(userDTO.getOldPassword())) {
            if (!checkpw(userDTO.getOldPassword(), user.getPassword())) {
                throw new BusinessRuleException("Senha antiga inválida.");
            }

            String hashedPassword = this.validateAndHashPassword(userDTO.getPassword(), userDTO.getConfirmPassword());

            user.setPassword(hashedPassword);
        }

        if (nonNull(userDTO.getUsername())) {
            user.setUsername(userDTO.getUsername());
        }

        if (nonNull(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        }

        if (nonNull(userDTO.getPhone())) {
            user.setPhone(userDTO.getPhone());
        }

        return repository.save(user);
    }

    @Override
    public User search(Long userId) {
        Optional<User> user = repository.findById(userId);

        if (user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        return user.get();
    }

    @Override
    public void delete(Long userId) {
        Optional<User> user = repository.findById(userId);

        if (user.isEmpty()) {
            throw new BusinessRuleException("Usuário não existe.");
        }

        repository.delete(user.get());
    }

    private String validateAndHashPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new BusinessRuleException("Senhas não batem.");
        }

        return hashpw(password, gensalt(12));
    }

    private void checkIfAlreadyExists(String username, String email, String phone) {
        BusinessRuleException businessRuleException = new BusinessRuleException("Requisição possui campos inválidos.");

        if (nonNull(username) && repository.findByUsername(username).isPresent()) {
            businessRuleException.addError(new ErrorObject("Já existe usuário com esse Username.", "username", username));
        }

        if (nonNull(email) && nonNull(repository.findByEmail(email))) {
            businessRuleException.addError(new ErrorObject("Já existe usuário com esse E-mail.", "email", email));
        }

        if (nonNull(phone) && nonNull(repository.findByPhone(phone))) {
            businessRuleException.addError(new ErrorObject("Já existe usuário com esse Telefone.", "phone", phone));
        }

        if (businessRuleException.checkHasSomeError()) {
            throw businessRuleException;
        }
    }

}
