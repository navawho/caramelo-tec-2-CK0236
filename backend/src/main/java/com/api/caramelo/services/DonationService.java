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
        // TO DO
        return null;
    }
}
