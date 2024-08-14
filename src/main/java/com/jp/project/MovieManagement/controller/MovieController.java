package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> findAllMovies() {
        return movieService.findAll();
    }
}
