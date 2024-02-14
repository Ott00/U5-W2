package com.otmankarim.U5W2D2.repositories;

import com.otmankarim.U5W2D2.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogDAO extends JpaRepository<Blog, Integer> {
    Optional<Blog> findByTitle(String title);
}
