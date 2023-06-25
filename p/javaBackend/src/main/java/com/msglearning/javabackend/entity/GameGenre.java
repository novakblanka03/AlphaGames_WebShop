package com.msglearning.javabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = GameGenre.TABLE_NAME)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameGenre {

    static final String TABLE_NAME = "game_genre";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
