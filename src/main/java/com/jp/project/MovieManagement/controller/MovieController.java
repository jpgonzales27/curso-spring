package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.dto.request.SaveMovie;
import com.jp.project.MovieManagement.dto.response.GetMovie;
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
    public ResponseEntity<List<GetMovie>> findAllMovies(@RequestParam(required = false) String title,
                                        @RequestParam(required = false) MovieGenre genre) {

        List<GetMovie> movies = null;

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
    public ResponseEntity<GetMovie> findMovieById(@PathVariable Long id) {
       try {
           return ResponseEntity.ok(movieService.findOneById(id));
       }catch (ObjectNotFoundException e) {
           return ResponseEntity.notFound().build();
       }
    }

    @PostMapping
    public ResponseEntity<GetMovie> createMovie(@RequestBody SaveMovie movieDto, HttpServletRequest request){

        GetMovie movieCreated = movieService.saveOne(movieDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.id());

        return ResponseEntity.created(newLocation).body(movieCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetMovie> updateMovie(@PathVariable Long id ,@RequestBody SaveMovie movieDto) {
       try{
           GetMovie updatedMovie = movieService.updateOneById(id, movieDto);
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
