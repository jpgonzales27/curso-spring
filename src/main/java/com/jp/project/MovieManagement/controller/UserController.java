package com.jp.project.MovieManagement.controller;

import com.jp.project.MovieManagement.dto.request.SaveUser;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.persistence.entity.User;
import com.jp.project.MovieManagement.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<GetUser>> findAllUsers(@RequestParam(required = false) String name) {

        List<GetUser> users = null;

        if(StringUtils.hasText(name)) {
            users = userService.findAllByName(name);
        } else {
            users =userService.findAll();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<GetUser> findUserById(@PathVariable String username) {
        try{
            return ResponseEntity.ok(userService.findOneByUsername(username));
        }catch (ObjectNotFoundException exception){
//            return ResponseEntity.status(404).build();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<GetUser> createUser(@RequestBody SaveUser userDto, HttpServletRequest request){

        GetUser userCreated = userService.saveOne(userDto);
        String baseUrl = request.getRequestURL().toString();
        URI newLocation = URI.create(baseUrl + "/" + userCreated.username());

        return ResponseEntity.created(newLocation).body(userCreated);
    }

    @PutMapping("/{username}")
    public ResponseEntity<GetUser> updateUser(@PathVariable String username,@RequestBody SaveUser userDto) {
        try {
            GetUser userUpdated = userService.updateOneByUsername(username,userDto);
            return ResponseEntity.ok(userUpdated);
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username){
        try {
            userService.deleteOneByUsername(username);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
