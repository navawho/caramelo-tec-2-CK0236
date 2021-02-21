package com.api.caramelo.services;

import com.api.caramelo.exceptions.BusinessRuleException;
import com.api.caramelo.models.Donation;
import com.api.caramelo.models.Pet;
import com.api.caramelo.models.User;
import com.api.caramelo.models.enums.DonationType;
import com.api.caramelo.repositories.DonationRepository;
import com.api.caramelo.repositories.PetRepository;
import com.api.caramelo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TestDonationService {

    @SpyBean
    DonationService donationService;

    @MockBean
    DonationRepository donationRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PetRepository petRepository;

    @Test
    public void testCreateDonationWithSucess() {
        Long petId = 1l;
        Long donationUserId = 1l;
        Double value = 50.00;
        DonationType donationType = DonationType.DINHEIRO;

        Pet pet = new Pet();
        User petUser = new User();
        pet.setUser(petUser);
        User donationUser = new User();
        Donation donation = Donation.builder().user(donationUser).pet(pet).build();

        Mockito.when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        Mockito.when(userRepository.findById(donationUserId)).thenReturn(Optional.of(donationUser));
        Mockito.when(donationRepository.save(Mockito.any(Donation.class))).thenReturn(donation);

        Donation savedDonation = donationService.create(petId, donationUserId, value, donationType);

        assertEquals(savedDonation, donation);
    }

    @Test
    public void testTryToDonateToYourOwnPet() {
        Long petId = 1l;
        Long donationUserId = 1l;
        Double value = 50.00;
        DonationType donationType = DonationType.DINHEIRO;

        Pet pet = new Pet();
        User user = new User();
        pet.setUser(user);

        Mockito.when(userRepository.findById(donationUserId)).thenReturn(Optional.of(user));
        Mockito.when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        Throwable exception = assertThrows(BusinessRuleException.class, () -> donationService.create(petId, donationUserId, value, donationType));

        assertEquals(exception.getMessage(), "Você não pode doar pro seu próprio pet.");
    }

    @Test
    public void testTryToDonateToAPetThatDoesNotExists() {
        Long petId = 1l;
        Long donationUserId = 1l;
        Double value = 50.00;
        DonationType donationType = DonationType.DINHEIRO;

        Mockito.when(petRepository.findById(petId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> donationService.create(petId, donationUserId, value, donationType));

        assertEquals(exception.getMessage(), "Pet inexistente.");
    }

    @Test
    public void testUserThatDoesNotExists() {
        Long petId = 1l;
        Long donationUserId = 1l;
        Double value = 50.00;
        DonationType donationType = DonationType.DINHEIRO;

        Pet pet = new Pet();
        User petUser = new User();
        pet.setUser(petUser);

        Mockito.when(petRepository.findById(petId)).thenReturn(Optional.of(pet));
        Mockito.when(userRepository.findById(donationUserId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(BusinessRuleException.class, () -> donationService.create(petId, donationUserId, value, donationType));

        assertEquals(exception.getMessage(), "User inexistente.");
    }
}
