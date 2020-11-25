package com.api.caramelo.services.interfaces;

import com.api.caramelo.models.User;

public interface ISessionService {
    Long validateCredentials(String username, String password);
    User loggedUser(Long userId);
}
