package com.api.caramelo.services.interfaces;

import com.api.caramelo.models.Solicitation;

import java.util.List;


public interface ISolicitationService {

    void delete(Long solicitationId, Long userId);

    Solicitation create(Long userId, Long petId);

    Solicitation update(Long solicitationId, Boolean accepted);

    List<Solicitation> search(Long userId);

    List<Solicitation> searchPetSolicitations(Long userId, Long petId);
}
