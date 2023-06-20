package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final GameConverter gameConverter;

    public GameService(GameRepository gameRepository, GameConverter gameConverter) {
        this.gameRepository = gameRepository;
        this.gameConverter = gameConverter;
    }

    public List<GameTO> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return gameConverter.convertToTOList(games);
    }

//    public List<Game> getAllGamesRaw() {
//        return gameRepository.findAll();
//    }

    public ResponseEntity<GameTO> getGameById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            GameTO gameTO = gameConverter.toGameTO(game);
            return ResponseEntity.ok(gameTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean existsGameByName(String name) {
        return gameRepository.existsByName(name);
    }


    //TODO: saveGame should receive a Game object not GameTO
    public ResponseEntity<Game> saveGame(Game game) {
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGame);
    }

//    Asa arata body-ul pt un updateGame:

//    {
//            "id": 12,
//            "publisherId": 4,
//            "name": "Merge gameUpdate-ul",
//            "description": "Pentru toti parametrii!!!",
//            "price": 35.0,
//            "imageUrl": "link imagine(pt ca merge update-ul)",
//            "publishDate": "2023-02-01"
//
// }

    public ResponseEntity<GameTO> updateGame(Long id, Game game) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game existingGame = optionalGame.get();
            // Update game attributes
            existingGame.setName(game.getName());
            existingGame.setDescription(game.getDescription());
            existingGame.setImageUrl(game.getImageUrl());
            existingGame.setPublishDate(game.getPublishDate());
            existingGame.setPrice(game.getPrice());
            //Convert game to gameTO
            Game updatedGame = gameRepository.save(existingGame);
            GameTO updatedGameTO = gameConverter.toGameTO(updatedGame);
            return ResponseEntity.ok(updatedGameTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @Transactional
//    public void updateGame(Long id, String name, String decription){
//
//    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }


}
