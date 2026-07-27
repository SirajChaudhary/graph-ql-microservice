package com.sirajchaudhary.library.repository;

import com.sirajchaudhary.library.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    boolean existsByEmail(String email);
}