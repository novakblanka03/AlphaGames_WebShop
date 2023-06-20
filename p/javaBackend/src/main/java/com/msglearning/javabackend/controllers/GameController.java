package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.to.GameTO;
import com.sun.xml.bind.v2.TODO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME)
public class GameController {
    private static final String ID_PATH = "/{id}";

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
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
        return gameService.saveGame(game);
    }

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
