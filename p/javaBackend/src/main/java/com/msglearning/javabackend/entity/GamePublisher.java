package com.msglearning.javabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = GamePublisher.TABLE_NAME)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GamePublisher {

    static final String TABLE_NAME = "game_publisher";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}
