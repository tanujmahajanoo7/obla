package com.onlinebooking.library.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.onlinebooking.library.model.Item;

@Repository
public interface LibraryItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByTitle(String title);
}