package com.api.caramelo.services.interfaces;

import com.api.caramelo.models.Adoption;

import java.util.List;


public interface IAdoptionService {

    Adoption update(Long adoptionId);

    List<Adoption> search(Long userId);
}
