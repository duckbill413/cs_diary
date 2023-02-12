package com.example.batch.part4.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * author        : duckbill413
 * date          : 2023-02-07
 * description   :
 **/

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Collection<Object> findAllByUpdatedDate(LocalDate updatedDate);
}
