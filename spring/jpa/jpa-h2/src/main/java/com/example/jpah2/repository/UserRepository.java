package com.example.jpah2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jpah2.dto.User;
public interface UserRepository extends JpaRepository<User, Long>{

}