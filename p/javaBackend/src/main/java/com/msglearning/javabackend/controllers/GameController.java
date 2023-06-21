package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.converters.GameConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GameGenre;
import com.msglearning.javabackend.entity.Genre;
import com.msglearning.javabackend.repositories.GenreRepository;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.services.GenreService;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME)
public class GameController {
    private static final String ID_PATH = "/{id}";

    private final GameService gameService;
    private final GameConverter gameConverter;
    private final GameGenreController gameGenreController;

    public GameController(GameService gameService, GameConverter gameConverter, GameGenreController gameGenreController) {
        this.gameService = gameService;
        this.gameConverter = gameConverter;
        this.gameGenreController = gameGenreController;
    }

    //TODO: Change all get requests to use some type of TO by need;

    @GetMapping("/all")
    public List<GameTO> getAllGames() {
        return gameService.getAllGames();
    }

//    @GetMapping("/all/raw")
//    public List<Game> getAllGamesRaw() {
//        return gameService.getAllGamesRaw();
//    }

    @GetMapping(ID_PATH)
    public ResponseEntity<GameTO> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }


    @PostMapping
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        if (gameService.existsGameByName(game.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        ResponseEntity<Game> savedGameResponse = gameService.saveGame(game);

        // Create GameGenre connections
        if (savedGameResponse.getStatusCode().is2xxSuccessful() && game.getGameGenres() != null) {
            Game savedGame = savedGameResponse.getBody();
            for (GameGenre gameGenre : game.getGameGenres()) {
                gameGenre.setGame(savedGame);
                gameGenreController.saveGameGenre(gameGenre);
            }
        }

        return savedGameResponse;
    }

//    JSON for Game Post:
//{
//    "publisher": {
//    "id": 1
//},
//    "name": "Game Name",
//        "description": "Description of the Game",
//        "price": 49.99,
//        "imageUrl": "www.example.com/game.jpg",
//        "publishDate": "2023-06-21",
//        "gameGenres": [
//    {
//        "genre": {
//        "id": 1
//    }
//    },
//    {
//        "genre": {
//        "id": 2
//    }
//    },
//    {
//        "genre": {
//        "id": 3
//    }
//    }
//  ]
//}


    //TODO: Discuss functionality of update methods
    @PutMapping(ID_PATH)
    public ResponseEntity<GameTO> updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

//    @PutMapping(ID_PATH)
//    public void updateGame(){
//        @PathVariable("id")Long id,
//        @RequestParam(required = false) String name,
//        @RequestParam(required = false) String description){
//            gameService.updateGame(id, name, description);
//        }
//
//    }

    @DeleteMapping(ID_PATH)
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
