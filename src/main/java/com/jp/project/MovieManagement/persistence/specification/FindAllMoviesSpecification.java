package com.jp.project.MovieManagement.persistence.specification;

import com.jp.project.MovieManagement.dto.request.MovieSearchCriteria;
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

    private MovieSearchCriteria searchCriteria;


    public FindAllMoviesSpecification(MovieSearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        //root = select * from Movie
        //query = criterios de la consulta en si misma
        //criteriaBuilder = fabrica que te permite construir predicados y expresiones

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(this.searchCriteria.title())){
            Predicate titleLike = criteriaBuilder.like(root.get("title"), "%" + this.searchCriteria.title() + "%");
            //m.title like '%this.title%'
            predicates.add(titleLike);
        }

        if(this.searchCriteria.genre()!=null){
            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"),this.searchCriteria.genre());
            //m.genre = "this.genre"
            predicates.add(genreEqual);
        }

        if(this.searchCriteria.minReleaseYear()!= null && this.searchCriteria.minReleaseYear().intValue() > 0){
            Predicate releaseYearGreaterThanOrEquals = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"),this.searchCriteria.minReleaseYear());
            //m.releaseYear >= this.minReleaseYear
            predicates.add(releaseYearGreaterThanOrEquals);
        }

        //select m.* from Movie m where 1 = 1 and m.title like '%this.title%' and m.genre = "this.genre" and m.releaseYear >= this.minReleaseYear
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
