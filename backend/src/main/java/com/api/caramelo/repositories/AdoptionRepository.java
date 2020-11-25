package com.api.caramelo.repositories;

import com.api.caramelo.models.Adoption;
import com.api.caramelo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findByUserAndReturnedIsFalse(User user);
}
