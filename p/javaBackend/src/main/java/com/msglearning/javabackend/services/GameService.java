package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Genre;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.GenreRepository;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    private final GameConverter gameConverter;

    private final GenreRepository genreRepository;
    private final PurchaseRepository purchaseRepository;
    private final GenreService genreService;

    public GameService(GameRepository gameRepository, GameConverter gameConverter,
                       GenreRepository genreRepository, PurchaseRepository purchaseRepository,
                       GenreService genreService) {
        this.gameRepository = gameRepository;
        this.gameConverter = gameConverter;
        this.genreRepository = genreRepository;
        this.purchaseRepository = purchaseRepository;
        this.genreService = genreService;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

//    public List<Game> getAllGamesRaw() {
//        return gameRepository.findAll();
//    }

    public ResponseEntity<Game> getGameById(Long id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            GameTO gameTO = gameConverter.toGameTO(game);
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
            System.out.println(existingGame.getGenres());

            //Delete existing genres from existingGame;
            List<Genre> genres = genreRepository.findByGameId(id);
            for(Genre genre: genres){
                System.out.println(genre.getId());
                genreRepository.deleteById(genre.getId());
            }

            //Save genres in new game by incrementing id manually
            //and update the existingGame;
            Long highestId = genreRepository.getHighestId();
            for(Genre genre: newGame.getGenres()){
                genre.setId(++highestId);
            }
            existingGame.setGenres(newGame.getGenres());


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

        //Delete genres for the game
        Game game = gameRepository.getById(id);
        List<Genre> genres = game.getGenres();
        for(Genre genre: genres){
            genreRepository.deleteById(genre.getId());
        }

        //Delete purchases which contain the game
            List<Purchase> purchases = purchaseRepository.findByGameId(id);
            for(Purchase purchase: purchases){
                gameRepository.deleteById(purchase.getId());
            }

        //Delete the game
        gameRepository.deleteById(id);
    }
}
