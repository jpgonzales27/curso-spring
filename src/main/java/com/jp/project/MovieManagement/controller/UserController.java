package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.dto.request.SaveUser;
import com.jp.project.MovieManagement.dto.request.UserSearchCriteria;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.persistence.entity.User;
import com.jp.project.MovieManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<Page<GetUser>> findAllUsers(@RequestParam(required = false) String name,
                                                      Pageable userPageable) {

        UserSearchCriteria searchCriteria = new UserSearchCriteria(name);
        Page<GetUser> users = userService.findAll(searchCriteria,userPageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetUser> findUserById(@PathVariable String username) {
        return ResponseEntity.ok(userService.findOneByUsername(username));
    }

    @PostMapping
    public ResponseEntity<GetUser> createUser(@Valid @RequestBody SaveUser userDto, HttpServletRequest request){

        GetUser userCreated = userService.saveOne(userDto);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + userCreated.username());

        return ResponseEntity.created(newLocation).body(userCreated);
    }

    @PutMapping("/{username}")
    public ResponseEntity<GetUser> updateUser(@PathVariable String username,@Valid @RequestBody SaveUser userDto) {
        GetUser userUpdated = userService.updateOneByUsername(username,userDto);
        return ResponseEntity.ok(userUpdated);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        userService.deleteOneByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
