package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.to.GameTO;
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

    @GetMapping("/all")
    public List<GameTO> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        if (gameService.existsGameByName(game.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return gameService.saveGame(game);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

    @DeleteMapping(ID_PATH)
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
