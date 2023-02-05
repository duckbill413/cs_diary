package com.example.batch.part3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author        : duckbill413
 * date          : 2023-02-04
 * description   :
 **/
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
