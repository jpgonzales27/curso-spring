package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.dto.request.SaveRating;
import com.jp.project.MovieManagement.dto.response.GetCompleteRating;
import com.jp.project.MovieManagement.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<Page<GetCompleteRating>> findAll(Pageable pageable){
        return ResponseEntity.ok(ratingService.findAll(pageable));
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRating> createOne (@PathVariable Long ratingId){
       return ResponseEntity.ok(ratingService.findOneById(ratingId));
    }

    @PostMapping
    public ResponseEntity<GetCompleteRating> createOne(@RequestBody @Valid SaveRating saveDto,
                                                       HttpServletRequest request){
        GetCompleteRating dto = ratingService.createOne(saveDto);
        String urlRequest = request.getRequestURL().toString();
        URI urlCreated = URI.create(urlRequest+"/"+dto.ratingId());
        return ResponseEntity.created(urlCreated).body(dto);
    }

    @PutMapping("/{ratingId}")
    public ResponseEntity<GetCompleteRating> updateOneById(@PathVariable Long ratingId,
                                                           @RequestBody @Valid SaveRating saveDto){
        return ResponseEntity.ok(ratingService.updateOneById(ratingId, saveDto));
    }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long ratingId){
        ratingService.deleteOneById(ratingId);
        return ResponseEntity.noContent().build();
    }
}
