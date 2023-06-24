package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.dto.GameGenreRequest;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.services.GameService;
import com.msglearning.javabackend.services.GenreService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PATH_GAME_GENRE)
public class GameGenreController {
    private final GameService gameService;
    private final GenreService genreService;

    public GameGenreController(GameService gameService, GenreService genreService) {
        this.gameService = gameService;
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<Game> saveGameGenre(@RequestBody GameGenreRequest gameGenreRequest) {
        return gameService.saveGame(gameGenreRequest.getGame());
    }

    @GetMapping("/all")
    public List<Game> findAllGames(){
        return gameService.getAllGames();
    }
//    @GetMapping("/genre/{genreId}/games")
//    public List<Game> getGamesByGenreIdSortedByTitle(@PathVariable Long genreId, @RequestParam(defaultValue = "title") String sortBy) {
//        Sort sort = Sort.by(sortBy);
//        return gameGenreService.getGamesByGenreIdSortedByTitle(genreId, sort);
//    }

    @PutMapping("/{{id}}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game game){
        return gameService.updateGame(id, game);
    }

}
