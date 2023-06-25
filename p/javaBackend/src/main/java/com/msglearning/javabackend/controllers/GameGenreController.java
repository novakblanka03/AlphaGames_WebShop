package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.to.GameGenreRequest;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GameGenre;
import com.msglearning.javabackend.services.GameGenreService;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.services.GenreService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME_GENRE)
public class GameGenreController {
    private final GameGenreService gameGenreService;

    public GameGenreController(GameGenreService gameGenreService) {
        this.gameGenreService = gameGenreService;
    }

    @GetMapping("/all")
    public List<GameGenre> findAllGameGenres(){
        return gameGenreService.findAllGameGenres();
    }

    @PostMapping
    public ResponseEntity<List<GameGenre>> saveGameGenre(@RequestBody GameGenreRequest gameGenreRequest) throws NotFoundException {
        return ResponseEntity.ok(gameGenreService.createGameGenre(gameGenreRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable Long id) {
        gameGenreService.deleteGameGenre(id);
        return ResponseEntity.ok("GameGenre with ID " + id + " has been deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGameGenre(@PathVariable Long id, @RequestBody GameGenre gameGenre) throws NotFoundException {
        gameGenreService.updateGameGenre(id, gameGenre);
        return ResponseEntity.ok("GameGenre with ID " + id + " has been updated.");
    }

}
