package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.service.MovieService;
import com.jp.project.MovieManagement.util.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Movie>> findAllMovies(@RequestParam(required = false) String title,
                                        @RequestParam(required = false) MovieGenre genre) {

        List<Movie> movies = null;

        if (StringUtils.hasText(title) && genre != null) {
            movies = movieService.findAllByGenreAndTitle(genre, title);
        } else if (StringUtils.hasText(title)) {
            movies = movieService.findAllByTitle(title);
        } else if (genre != null) {
            movies = movieService.findAllByGenre(genre);
        } else {
            movies = movieService.findAll();
        }

        /*
         * Option 1 - Devolver el objeto y el status code
         * De esta forma tb es facil agregarle los headers en caso de necesitarlo         *
         */
         //return new ResponseEntity<>( movies, HttpStatus.OK);
         //HttpHeaders headers = new HttpHeaders();
         //return new ResponseEntity(movies, headers, HttpStatus.OK);


        /*
         * Option 2 - usar el formato de builder para crear nuestra respuesta
         */
        //return ResponseEntity.status(HttpStatus.OK).body(movies);

        /*
         * Option 3 - retornar directamente el objeto ResponseEntity
         */
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
       try {
           return ResponseEntity.ok(movieService.findOneById(id));
       }catch (ObjectNotFoundException e) {
           return ResponseEntity.notFound().build();
       }
    }

    //    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Movie> createOneV1(@RequestParam String title,
                                             @RequestParam String director,
                                             @RequestParam MovieGenre genre,
                                             @RequestParam int releaseYear,
                                             HttpServletRequest request){

        Movie newMovie = new Movie();
        newMovie.setTitle(title);
        newMovie.setDirector(director);
        newMovie.setGenre(genre);
        newMovie.setReleaseYear(releaseYear);

        Movie movieCreated = movieService.saveOne(newMovie);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.getId());

        return ResponseEntity
                .created(newLocation)
                .body(movieCreated);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie,HttpServletRequest request){

        Movie movieCreated = movieService.saveOne(movie);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.getId());

        return ResponseEntity.created(newLocation).body(movieCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id ,@RequestBody Movie movie) {
       try{
           Movie updatedMovie = movieService.updateOneById(id, movie);
           return ResponseEntity.ok(updatedMovie);
       } catch ( ObjectNotFoundException e){
           return ResponseEntity.notFound().build();
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        try{
            movieService.deleteOneById(id);
            return ResponseEntity.noContent().build();
        } catch ( ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
