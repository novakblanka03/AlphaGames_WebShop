package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        if(games.isEmpty())
            return Collections.emptyList();
        else
            return games.stream()
                    .map(GameConverter::toGameTO)
                    .collect(Collectors.toList());
    }

    public ResponseEntity<Game> getGameById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        return optionalGame.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    public boolean existsGameByName(String name) {
        return gameRepository.existsByName(name);
    }

    public ResponseEntity<Game> saveGame(Game game) {
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGame);
    }

    public ResponseEntity<Game> updateGame(Long id, Game game) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game existingGame = optionalGame.get();
            // Update game attributes
            existingGame.setName(game.getName());
            existingGame.setDescription(game.getDescription());
            existingGame.setImageUrl(game.getImageUrl());
            existingGame.setPublishDate(game.getPublishDate());
            Game updatedGame = gameRepository.save(existingGame);
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
