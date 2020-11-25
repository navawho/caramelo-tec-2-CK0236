package com.api.caramelo.repositories;

import com.api.caramelo.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    Pet findPetByName(String name);

    Pet findPetById(Long id);

    void deleteById(Long petId);

    @Query("select p from Pet p\n" +
            "where p.user.id<>?1 and p.available=true and p.id not in (select s.pet.id from Solicitation s where s.user.id=?1 and s.accepted is null) \n" +
            "and lower(p.name) LIKE lower(concat('%', ?2, '%')) and lower(p.port) LIKE lower(concat('%', ?3, '%')) and lower(p.type) LIKE lower(concat('%', ?4, '%')) and lower(p.sex) LIKE lower(concat('%', ?5, '%'))")
    List<Pet> findPetsToRequest(Long userId, String name, String port, String type, String sex);

    @Query("select p from Pet p where p.user.id=?1")
    List<Pet> findMyPets(Long userId);
}
