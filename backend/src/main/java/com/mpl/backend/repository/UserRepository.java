package com.mpl.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpl.backend.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
