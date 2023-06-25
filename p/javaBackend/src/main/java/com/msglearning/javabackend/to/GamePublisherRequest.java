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
public class GamePublisherRequest implements Serializable {
    private List<Long> gameIds;
    private Long publisherId;
}
