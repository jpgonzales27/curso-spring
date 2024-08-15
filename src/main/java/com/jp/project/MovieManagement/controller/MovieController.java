package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.service.MovieService;
import com.jp.project.MovieManagement.util.MovieGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Movie> findAllMovies(@RequestParam(required = false) String title,
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

        return movies;
    }

//    @RequestMapping(method = RequestMethod.GET, params = {"title", "genre"})
//    public List<Movie> findAllByGenreAndTitle(@RequestParam String title,
//                                              @RequestParam MovieGenre genre) {
//        System.out.println("Metodo: findAllByGenreAndTitle");
//        return movieService.findAllByGenreAndTitle(genre, title);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, params = "title")
//    public List<Movie> findAllByTitle(@RequestParam String title) {
//        System.out.println("Metodo: findAllByTitle");
//        return movieService.findAllByTitle(title);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, params = "genre")
//    public List<Movie> findAllByGenre(@RequestParam MovieGenre genre) {
//        System.out.println("Metodo: findAllByGenre");
//        return movieService.findAllByGenre(genre);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, params = {"!title", "!genre"})
//    public List<Movie> findAll() {
//        System.out.println("Metodo: findAll");
//        return movieService.findAll();
//    }

    @GetMapping("/{id}")
    public Movie findMovieById(@PathVariable Long id) {
        return movieService.findOneById(id);
    }
}
