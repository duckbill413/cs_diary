package com.example.jparepositorytest.repository;

import com.example.jparepositorytest.domain.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
