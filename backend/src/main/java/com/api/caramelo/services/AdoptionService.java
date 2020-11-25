package com.api.caramelo.services;

import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.Adoption;
import com.api.caramelo.models.Pet;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.AdoptionRepository;
import com.api.caramelo.repositories.PetRepository;
import com.api.caramelo.repositories.UserRepository;
import com.api.caramelo.services.interfaces.IAdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdoptionService implements IAdoptionService {

    private final AdoptionRepository adoptionRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Override
    public Adoption update(Long adoptionId) {
        Optional<Adoption> adoptionOptional = adoptionRepository.findById(adoptionId);

        if (adoptionOptional.isEmpty()) {
            throw new BusinessRuleException("Adoção não existe.");
        }

        Adoption adoption = adoptionOptional.get();
        adoption.setReturned(true);

        Pet pet = adoption.getPet();
        pet.setAvailable(true);
        petRepository.save(pet);

        return adoptionRepository.save(adoption);
    }

    @Override
    public List<Adoption> search(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        return adoptionRepository.findByUserAndReturnedIsFalse(user.get());
    }
}
