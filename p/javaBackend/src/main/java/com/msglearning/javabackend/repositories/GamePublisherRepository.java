package com.msglearning.javabackend.repositories;
import com.msglearning.javabackend.entity.GamePublisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GamePublisherRepository extends JpaRepository<GamePublisher, Long> {
    List<GamePublisher> findByGameId(Long gameId);

    List<GamePublisher> findByPublisherId(Long id);
}