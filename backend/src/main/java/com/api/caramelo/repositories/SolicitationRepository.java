package com.api.caramelo.repositories;

import com.api.caramelo.models.Pet;
import com.api.caramelo.models.Solicitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolicitationRepository extends JpaRepository<Solicitation, Long> {

    @Query("select s from Solicitation s join Pet p on p.id=s.pet.id where s.user.id=?1 or p.user.id=?1")
    List<Solicitation> findSolicitations(Long userId);

    List<Solicitation> findSolicitationByPetAndAcceptedIsNull(Pet pet);

    List<Solicitation> findSolicitationsByUserId(Long userId);

}
