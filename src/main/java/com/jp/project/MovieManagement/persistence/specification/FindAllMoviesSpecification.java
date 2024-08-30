package com.jp.project.MovieManagement.persistence.specification;

import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.util.MovieGenre;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllMoviesSpecification implements Specification<Movie> {

    private String title;
    private MovieGenre genre;
    private Integer minReleaseYear;

    public FindAllMoviesSpecification(String title, MovieGenre genre, Integer minReleaseYear) {
        this.title = title;
        this.genre = genre;
        this.minReleaseYear = minReleaseYear;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        //root = select * from Movie
        //query = criterios de la consulta en si misma
        //criteriaBuilder = fabrica que te permite construir predicados y expresiones

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(this.title)){
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%" + this.title + "%");
            //m.title like '%this.title%'
            predicates.add(titleLike);
        }

        if(genre!=null){
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"),this.genre);
            //m.genre = "this.genre"
            predicates.add(genreEqual);
        }

        if(minReleaseYear!= null && minReleaseYear > 0){
            Predicate releaseYearGreaterThanOrEquals = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"),this.minReleaseYear);
            //m.releaseYear >= this.minReleaseYear
            predicates.add(releaseYearGreaterThanOrEquals);
        }

        //select m.* from Movie m where 1 = 1 and m.title like '%this.title%' and m.genre = "this.genre" and m.releaseYear >= this.minReleaseYear
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
