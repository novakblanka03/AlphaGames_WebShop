package com.msglearning.javabackend.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameTO implements Serializable {
    private Long id;
    private String publisherName;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDate publishDate;
}
