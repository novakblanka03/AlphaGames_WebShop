package com.msglearning.javabackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = Publisher.TABLE_NAME)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publisher {

    static final String TABLE_NAME = "publisher";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String website;

    @OneToMany(mappedBy = "publisher")
    private List<Game> games;
}
