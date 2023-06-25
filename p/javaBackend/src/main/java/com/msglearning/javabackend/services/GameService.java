package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameConverter gameConverter;
    private final PurchaseRepository purchaseRepository;
    private final RatingRepository ratingRepository;

    private final GenreRepository genreRepository;
    private final PurchaseRepository purchaseRepository;
    private final GenreService genreService;
    private final GameGenreRepository gameGenreRepository;

    public GameService(GameRepository gameRepository, GameConverter gameConverter,
                       GenreRepository genreRepository, PurchaseRepository purchaseRepository,
                       GenreService genreService, GameGenreRepository gameGenreRepository) {
        this.gameRepository = gameRepository;
        this.gameConverter = gameConverter;
        this.genreRepository = genreRepository;
        this.purchaseRepository = purchaseRepository;
        this.genreService = genreService;
        this.gameGenreRepository = gameGenreRepository;
        this.purchaseRepository = purchaseRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
    
    public ResponseEntity<Game> getGameById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public boolean existsGameByName(String name) {
        return gameRepository.existsByName(name);
    }


    //TODO: saveGame should receive a Game object not GameTO
    public ResponseEntity<Game> saveGame(Game game) {
        Game newGame = gameRepository.save(game);
        return ResponseEntity.ok(newGame);
    }
    
    public ResponseEntity<Game> updateGame(Long id, Game newGame) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game existingGame = optionalGame.get();
            // Update game attributes
            existingGame.setName(newGame.getName());
            existingGame.setDescription(newGame.getDescription());
            existingGame.setImageUrl(newGame.getImageUrl());
            existingGame.setPublishDate(newGame.getPublishDate());
            existingGame.setPrice(newGame.getPrice());


            //Save the newGame
            Game updatedGame = gameRepository.save(existingGame);
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public void deleteGame(Long id) throws Exception{
        //Check if game exists in table
        if(!gameRepository.existsById(id))
            throw new IllegalStateException("Game id does not exist");
        Game game = gameRepository.getById(id);

        //Delete gameGenre connections which contain the game
        List<GameGenre> gameGenres = gameGenreRepository.findByGameId(id);
        gameGenres.forEach(gameGenre -> {
            gameGenreRepository.deleteById(gameGenre.getId());
        });

        //Delete purchases which contain the game
            List<Purchase> purchases = purchaseRepository.findByGameId(id);
            purchases.forEach(purchase -> {
                purchaseRepository.deleteById(purchase.getId());
            });

        //Delete the game
        gameRepository.deleteById(id);
    }
}

