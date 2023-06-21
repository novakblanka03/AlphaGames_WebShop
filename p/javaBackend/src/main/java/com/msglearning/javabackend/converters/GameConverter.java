package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.GameGenre;
import com.msglearning.javabackend.entity.Genre;
import com.msglearning.javabackend.to.GameTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameConverter {

    //TODO: Decide on method names;
    public GameTO toGameTO(Game game){
        return GameTO.builder()
                .id(game.getId())
                .name(game.getName())
                .description(game.getDescription())
                .price(game.getPrice())
                .imageUrl(game.getImageUrl())
                .publishDate(game.getPublishDate())
                .genreNames(getGenreNames(game))
                .publisherName(game.getPublisher().getName())
                .build();
    }

    private List<String> getGenreNames(Game game){
        List<GameGenre> gameGenres = game.getGameGenres();
        return gameGenres.stream()
                .map(gameGenre -> gameGenre.getGenre().getName())
                .collect(Collectors.toList());
    }

    public List<GameTO> convertToTOList(List<Game> games) {
        return games.stream()
                .map(this::toGameTO)
                .collect(Collectors.toList());
    }

    //TODO: Discutie in legatura cu necesitatea noPriceTO;

}
