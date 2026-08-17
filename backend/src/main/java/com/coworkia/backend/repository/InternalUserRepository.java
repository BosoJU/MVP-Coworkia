package com.coworkia.backend.repository;

import com.coworkia.backend.entities.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternalUserRepository extends JpaRepository<InternalUser, Long> {

    Optional<InternalUser> findByEmail(String email);

}
