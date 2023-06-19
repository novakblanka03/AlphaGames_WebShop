package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.to.GameTO;

public class GameConverter {
    public static GameTO toGameTO(Game game){
        return new GameTO(game.getId(), game.getPublisher().getName(),
                game.getName(), game.getDescription(),
                game.getImageUrl(), game.getPublishDate());
    }
}
