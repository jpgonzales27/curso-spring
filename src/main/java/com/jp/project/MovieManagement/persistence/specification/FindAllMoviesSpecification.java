package com.jp.project.MovieManagement.persistence.specification;

import com.jp.project.MovieManagement.dto.request.MovieSearchCriteria;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.persistence.entity.Rating;
import com.jp.project.MovieManagement.persistence.entity.User;
import com.jp.project.MovieManagement.util.MovieGenre;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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

//        if(this.searchCriteria.genre()!=null){
//            Predicate genreEqual = criteriaBuilder.equal(root.get("genre"),this.searchCriteria.genre());
//            //m.genre = "this.genre"
//            predicates.add(genreEqual);
//        }

//        if(this.searchCriteria.genres()!=null  && this.searchCriteria.genres().length > 0){
//
//            List<Predicate> genrePredicates = new ArrayList<>();
//            for (MovieGenre genre : this.searchCriteria.genres()) {
//                Predicate genreEqual = criteriaBuilder.equal(root.get("genre"),genre);
//                genrePredicates.add(genreEqual);
//            }
//
//            Predicate genreEqual = criteriaBuilder.or(genrePredicates.toArray(new Predicate[0]));
//            //and (m.genre = "this.genre" OR m.genre = "this.genre" OR m.genre = "this.genre" OR m.genre = "this.genre")
//            predicates.add(genreEqual);
//        }

        if(this.searchCriteria.genres()!=null  && this.searchCriteria.genres().length > 0){

            Predicate genreEqual = criteriaBuilder.in(root.get("genre")).value(Arrays.stream(this.searchCriteria.genres()).toList());
            //and m.genre in( "this.genre","this.genre","this.genre","this.genre")
            predicates.add(genreEqual);
        }

        if(this.searchCriteria.minReleaseYear()!= null && this.searchCriteria.minReleaseYear().intValue() > 0){
            Predicate releaseYearGreaterThanOrEquals = criteriaBuilder.greaterThanOrEqualTo(root.get("releaseYear"),this.searchCriteria.minReleaseYear());
            //m.releaseYear >= this.minReleaseYear
            predicates.add(releaseYearGreaterThanOrEquals);
        }

        if(this.searchCriteria.maxReleaseYear()!=null && this.searchCriteria.maxReleaseYear()> 0) {
            Predicate releaseYearLessThanOrEquals = criteriaBuilder.lessThanOrEqualTo(root.get("releaseYear"),this.searchCriteria.maxReleaseYear());
            //m.releaseYear >= this.maxReleaseYear
            predicates.add(releaseYearLessThanOrEquals);
        }


        if(this.searchCriteria.minAverageRating() != null && this.searchCriteria.minAverageRating() > 0){
            Subquery<Double> averageRatingSubquery = getAverageRatingSubquery(root, query, criteriaBuilder);

            Predicate averageRatingGreaterThanEqual = criteriaBuilder.greaterThanOrEqualTo(averageRatingSubquery, this.searchCriteria.minAverageRating().doubleValue());
            predicates.add(averageRatingGreaterThanEqual);
        }

        if(StringUtils.hasText(this.searchCriteria.username())){

            Join<Movie,Rating> joinMovieRating = root.join("ratings");
            Join<Movie, User> joinRatingUser = joinMovieRating.join("user");

//            select m.* from movie m
//                    join rating r on r.movie_id = m.id
//                    join user u on r.user_id = u.id
            Predicate usernameEqual = criteriaBuilder.equal(joinRatingUser.get("username"),this.searchCriteria.username());
            predicates.add(usernameEqual);
        }

        // select m.*
        // from movie m
        // where 1 = 1  and
        //              m.title like '%this.title%'
        //              and m.genre = "this.genre"
        //              and m.releaseYear >= this.minReleaseYear
        //              and m.releaseYear <= this.maxReleaseYear
        //              and (select avg(r1_0.rating)  from rating r1_0 where r1_0.movie_id = m1_0.id)
        //                      >= searchCriteria.minAverageRating()
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private static Subquery<Double> getAverageRatingSubquery(Root<Movie> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Double> averageRatingSubquery = query.subquery(Double.class);//double average rating
        Root<Rating> ratingRoot = averageRatingSubquery.from(Rating.class);//Select * from rating

        averageRatingSubquery.select( criteriaBuilder.avg(ratingRoot.get("rating")) );//select avg(rating) from rating

        Predicate movieIdEqual = criteriaBuilder.equal(root.get("id"), ratingRoot.get("movieId"));// where m.id = r.movie_id
        averageRatingSubquery.where(movieIdEqual);

        return averageRatingSubquery;
    }
}
