package com.jp.project.MovieManagement.persistence.repository;

import com.jp.project.MovieManagement.persistence.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingCrudRepository extends JpaRepository<Rating,Long> {

    /**
     * Esta consulta relaciona las entidades Movie y Rating, por lo tanto,
     * primero estamos buscando el campo MovieId dentro de la entidad Rating
     * El cual ya guarda la informacion con el campo id de las Movies
     */
    List<Rating> findByMovieId(long id);

    /**
     * Esta consulta relaciona las entidades User y Rating, por lo tanto,
     * primero estamos buscando el campo User dentro de la entidad Rating
     * para poder acceder al campo Username de la entidad User
     */
    List<Rating> findByUserUsername(String username);

    /**
     * Esta consulta es igual a la consulta anterior relaciona las entidades User y Rating,
     * pero estamos usando QueryMethods realizando un select en la entidad Rating
     * y a traves de un JOIN hacer referencia al campo Username de la entidad User
     */
    @Query("select r from Rating r join r.user u where u.username = ?1")
    List<Rating> findByUsername(String username);
}
