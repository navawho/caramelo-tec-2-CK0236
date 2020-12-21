package com.api.caramelo.services;

import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.*;
import com.api.caramelo.models.enums.DonationType;
import com.api.caramelo.repositories.*;
import com.api.caramelo.services.interfaces.IDonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationService implements IDonationService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final DonationRepository donationRepository;

    @Override
    public Donation create(Long petId, Long userId, Double value, DonationType type) {
        Optional<Pet> optionalPet = petRepository.findById(petId);

        if (optionalPet.isEmpty()) {
            throw new BusinessRuleException("Pet inexistente.");
        }
        Pet pet = optionalPet.get();

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new BusinessRuleException("User inexistente.");
        }
        User user = optionalUser.get();

        if (pet.getUser() == user) {
            throw new BusinessRuleException("Você não pode doar pro seu próprio pet.");
        }

        Donation donation = donationRepository.save(new Donation());

        return donation;
    }
}
