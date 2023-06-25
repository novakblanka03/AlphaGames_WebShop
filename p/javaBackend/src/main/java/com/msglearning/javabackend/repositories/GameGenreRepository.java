package com.msglearning.javabackend.repositories;
import com.msglearning.javabackend.entity.GameGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameGenreRepository extends JpaRepository<GameGenre, Long> {
    List<GameGenre> findByGameId(Long gameId);

    List<GameGenre> findByGenreId(Long id);
}