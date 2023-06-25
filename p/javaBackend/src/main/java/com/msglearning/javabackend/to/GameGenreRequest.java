package com.msglearning.javabackend.to;

import com.msglearning.javabackend.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GameGenreRequest implements Serializable {
    private Long gameId;
    private List<Long> genreIds;
}
