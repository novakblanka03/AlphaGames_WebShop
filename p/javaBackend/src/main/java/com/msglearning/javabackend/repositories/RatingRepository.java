package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByGameId(Long gameId);
    Optional<Rating> findByGameIdAndUserId(Long gameId, Long userId);
}
