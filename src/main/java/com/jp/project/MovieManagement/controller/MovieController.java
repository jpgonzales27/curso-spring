package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.dto.request.MovieSearchCriteria;
import com.jp.project.MovieManagement.dto.request.SaveMovie;
import com.jp.project.MovieManagement.dto.response.ApiError;
import com.jp.project.MovieManagement.dto.response.GetMovie;
import com.jp.project.MovieManagement.exception.InvalidPasswordException;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.service.MovieService;
import com.jp.project.MovieManagement.util.MovieGenre;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<GetMovie>> findAllMovies(@RequestParam(required = false) String title,
                                                        @RequestParam(required = false) MovieGenre genre,
                                                        @RequestParam(required = false) Integer minReleaseYear,
                                                        @RequestParam(required = false) Integer maxReleaseYear,
                                                        @RequestParam(required = false) Integer minAverageRating,
                                                        @RequestParam(required = false) String username) {

        MovieSearchCriteria searchCriteria = new MovieSearchCriteria(title,genre,minReleaseYear,maxReleaseYear,minAverageRating,username);
        List<GetMovie> movies = movieService.findAll(searchCriteria);

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
        return ResponseEntity.ok(movieService.findOneById(id));
    }

    @PostMapping
    public ResponseEntity<GetMovie> createMovie(@Valid @RequestBody SaveMovie movieDto, HttpServletRequest request) {
//        System.out.println("Fecha: " + saveDto.availabilityEndTime());
        GetMovie movieCreated = movieService.saveOne(movieDto);

        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + movieCreated.id());

        return ResponseEntity.created(newLocation).body(movieCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetMovie> updateMovie(@PathVariable Long id, @Valid @RequestBody SaveMovie movieDto) {
        GetMovie updatedMovie = movieService.updateOneById(id, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteOneById(id);
        return ResponseEntity.noContent().build();
    }
}
