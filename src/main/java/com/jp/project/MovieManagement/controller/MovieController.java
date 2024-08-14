package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> findAllMovies() {
        return movieService.findAll();
    }
}
