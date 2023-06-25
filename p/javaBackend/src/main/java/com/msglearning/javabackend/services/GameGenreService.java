package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GameGenre;
import com.msglearning.javabackend.entity.Genre;
import com.msglearning.javabackend.repositories.GameGenreRepository;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.GenreRepository;
import com.msglearning.javabackend.to.GameGenreRequest;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameGenreService {

    private final GameGenreRepository gameGenreRepository;
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;

    public GameGenreService(GameGenreRepository gameGenreRepository, GameRepository gameRepository, GenreRepository genreRepository) {
        this.gameGenreRepository = gameGenreRepository;
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
    }


    public List<GameGenre> findAllGameGenres() {
        return gameGenreRepository.findAll();
    }

    public List<GameGenre> createGameGenre(GameGenreRequest gameGenreRequest) throws NotFoundException {
        Long gameId = gameGenreRequest.getGameId();
        List<Long> genreIds = gameGenreRequest.getGenreIds();

        Game game = gameRepository.findById(gameId).get();
        if(gameRepository.findById(gameId).isEmpty())
            throw new NotFoundException("Game id not found");

        List<Genre> genres = genreRepository.findAllById(genreIds);

        genres.forEach(genre -> {
            GameGenre gameGenre = GameGenre.builder()
                    .game(game)
                    .genre(genre)
                    .build();
            gameGenreRepository.save(gameGenre);
        });
        return gameGenreRepository.findByGameId(gameId);
    }

    public void deleteGameGenre(Long id) {
        gameGenreRepository.deleteById(id);
    }

    public void updateGameGenre(Long id, GameGenre newGameGenre) throws NotFoundException {
        GameGenre existingGameGenre = gameGenreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("GameGenre not found with ID: " + id));
        
        existingGameGenre.setGame(newGameGenre.getGame());
        existingGameGenre.setGenre(newGameGenre.getGenre());
        // Update other fields as needed

        gameGenreRepository.save(existingGameGenre);
    }
}
