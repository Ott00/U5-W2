package com.otmankarim.U5W2D2.repositories;

import com.otmankarim.U5W2D2.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorDAO extends JpaRepository<Author, Integer> {
    Optional<Author> findByEmail(String email);

}
