package com.msglearning.javabackend.repositories;

import com.msglearning.javabackend.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("select max(g.id) from Genre g")
    Long getHighestId();


    boolean existsByName(String name);

}
