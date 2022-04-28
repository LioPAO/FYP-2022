package com.finalyearproject.fyp.repository;

import com.finalyearproject.fyp.model.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish,Long> {
    List<Wish> findAllByUserId(Long userId);
}
