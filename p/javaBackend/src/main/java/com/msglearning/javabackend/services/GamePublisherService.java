package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GamePublisher;
import com.msglearning.javabackend.entity.Publisher;
import com.msglearning.javabackend.repositories.GamePublisherRepository;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.PublisherRepository;
import com.msglearning.javabackend.to.GamePublisherRequest;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GamePublisherService {

    private final GamePublisherRepository gamePublisherRepository;
    private final GameRepository gameRepository;
    private final PublisherRepository publisherRepository;

    public GamePublisherService(GamePublisherRepository gamePublisherRepository, GameRepository gameRepository, PublisherRepository publisherRepository) {
        this.gamePublisherRepository = gamePublisherRepository;
        this.gameRepository = gameRepository;
        this.publisherRepository = publisherRepository;
    }


    public List<GamePublisher> findAllGamePublishers() {
        return gamePublisherRepository.findAll();
    }

    public List<GamePublisher> createGamePublisher(GamePublisherRequest gamePublisherRequest) throws NotFoundException {
        List<Long> gameIds = gamePublisherRequest.getGameIds();
        Long publisherId = gamePublisherRequest.getPublisherId();

        Publisher publisher = publisherRepository.findById(publisherId).get();
        if(publisherRepository.findById(publisherId).isEmpty())
            throw new NotFoundException("Publisher id not found");

        List<Game> games = gameRepository.findAllById(gameIds);

        games.forEach(game -> {
            GamePublisher gamePublisher = GamePublisher.builder()
                    .game(game)
                    .publisher(publisher)
                    .build();
            gamePublisherRepository.save(gamePublisher);
        });
        return gamePublisherRepository.findByPublisherId(publisherId);
    }

    public void deleteGamePublisher(Long id) {
        gamePublisherRepository.deleteById(id);
    }

    public void updateGamePublisher(Long id, GamePublisher newGamePublisher) throws NotFoundException {
        GamePublisher existingGamePublisher = gamePublisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("GamePublisher not found with ID: " + id));

        existingGamePublisher.setGame(newGamePublisher.getGame());
        existingGamePublisher.setPublisher(newGamePublisher.getPublisher());
        // Update other fields as needed

        gamePublisherRepository.save(existingGamePublisher);
    }
}
