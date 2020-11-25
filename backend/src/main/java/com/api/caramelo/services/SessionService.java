package com.api.caramelo.services;

import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.UserRepository;
import com.api.caramelo.services.interfaces.ISessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.mindrot.jbcrypt.BCrypt.checkpw;

@Service
@RequiredArgsConstructor
public class SessionService implements ISessionService {

    private final UserRepository repository;

    @Override
    public Long validateCredentials(String username, String password) {
        Optional<User> user = repository.findByUsername(username);

        if(user.isEmpty()) {
            throw new BusinessRuleException("Usuário não existe");
        }

        if (!checkpw(password, user.get().getPassword())) {
            throw new BusinessRuleException("Credenciais inválidas.");
        }

        return user.get().getId();
    }

    @Override
    public User loggedUser(Long userId) {
        Optional<User> optionalUser = repository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        return optionalUser.get();
    }
}
