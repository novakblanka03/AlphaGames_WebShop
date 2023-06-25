package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.to.GamePublisherRequest;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GamePublisher;
import com.msglearning.javabackend.services.GamePublisherService;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.services.GenreService;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME_PUBLISHER)
public class GamePublisherController {
    private final GamePublisherService gamePublisherService;

    public GamePublisherController(GamePublisherService gamePublisherService) {
        this.gamePublisherService = gamePublisherService;
    }

    @GetMapping("/all")
    public List<GamePublisher> findAllGamePublishers(){
        return gamePublisherService.findAllGamePublishers();
    }

    @PostMapping
    public ResponseEntity<List<GamePublisher>> saveGamePublisher(@RequestBody GamePublisherRequest gamePublisherRequest) throws NotFoundException {
        return ResponseEntity.ok(gamePublisherService.createGamePublisher(gamePublisherRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurchase(@PathVariable Long id) {
        gamePublisherService.deleteGamePublisher(id);
        return ResponseEntity.ok("GamePublisher with ID " + id + " has been deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGamePublisher(@PathVariable Long id, @RequestBody GamePublisher gamePublisher) throws NotFoundException {
        gamePublisherService.updateGamePublisher(id, gamePublisher);
        return ResponseEntity.ok("GamePublisher with ID " + id + " has been updated.");
    }

}
