package com.api.caramelo.services.interfaces;

import com.api.caramelo.models.Donation;
import com.api.caramelo.models.enums.DonationType;

public interface IDonationService {
    Donation create(Long userId, Long petId, Double value, DonationType type);
}
