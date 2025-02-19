package com.mpl.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mpl.backend.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
