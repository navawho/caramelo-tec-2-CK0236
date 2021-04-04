package com.api.caramelo.services;

import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.Adoption;
import com.api.caramelo.models.Pet;
import com.api.caramelo.models.Solicitation;
import com.api.caramelo.models.User;
import com.api.caramelo.repositories.AdoptionRepository;
import com.api.caramelo.repositories.PetRepository;
import com.api.caramelo.repositories.SolicitationRepository;
import com.api.caramelo.repositories.UserRepository;
import com.api.caramelo.services.interfaces.ISolicitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SolicitationService implements ISolicitationService {

    private final SolicitationRepository solicitationRepository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final AdoptionRepository adoptionRepository;

    @Override
    public Solicitation create(Long userId, Long petId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        User user = optionalUser.get();

        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isEmpty()) {
            throw new BusinessRuleException("Pet não existe.");
        }

        Pet pet = optionalPet.get();

        if (pet.getUser() == user) {
            throw new BusinessRuleException("Você não pode adotar seu próprio pet.");
        }

        Solicitation solicitation = Solicitation.builder().user(user).pet(pet).build();

        return solicitationRepository.save(solicitation);
    }

    @Override
    public Solicitation update(Long solicitationId, Boolean accepted) {
        Optional<Solicitation> solicitationOptional = solicitationRepository.findById(solicitationId);

        this.checkIfSolicitationExists(solicitationOptional);

        Solicitation solicitation = solicitationOptional.get();
        solicitation.setAccepted(accepted);

        if (accepted) {
            this.createNewAdoption(solicitation);
        }

        return solicitationRepository.save(solicitation);
    }

    public void createNewAdoption(Solicitation solicitation) {
        Adoption adoption = new Adoption();

        Pet pet = solicitation.getPet();

        adoption.setPet(pet);
        adoption.setUser(solicitation.getUser());
        adoption.setReturned(false);

        pet.setAvailable(false);
        petRepository.save(pet);

        adoptionRepository.save(adoption);
    }

    public void checkIfSolicitationExists(Optional<Solicitation> solicitationOptional) {
        if (solicitationOptional.isEmpty()) {
            throw new BusinessRuleException("Solicitação não existe.");
        }
    }

    @Override
    public void delete(Long solicitationId, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        solicitationRepository.deleteById(solicitationId);

    }

    @Override
    public List<Solicitation> search(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        return solicitationRepository.findSolicitationsByUserId(userId);
    }

    @Override
    public List<Solicitation> searchPetSolicitations(Long userId, Long petId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new BusinessRuleException("Usuário com esse token não existe.");
        }

        Optional<Pet> pet = petRepository.findById(petId);

        if (pet.isEmpty()) {
            throw new BusinessRuleException("Pet não existe.");
        }

        if (pet.get().getUser() != user.get()) {
            throw new BusinessRuleException("Permissões insuficientes.");
        }

        return solicitationRepository.findSolicitationByPetAndAcceptedIsNull(pet.get());
    }
}
