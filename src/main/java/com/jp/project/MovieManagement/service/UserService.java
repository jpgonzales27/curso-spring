package com.jp.project.MovieManagement.service;

import com.jp.project.MovieManagement.dto.request.SaveUser;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.persistence.entity.User;

import java.util.List;

public interface UserService {

    List<GetUser> findAll();
    List<GetUser> findAllByName(String name);
    GetUser findOneByUsername(String username);
    GetUser saveOne(SaveUser userDto);
    GetUser updateOneByUsername(String username, SaveUser userDto);
    void deleteOneByUsername(String username);

}
