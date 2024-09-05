package com.jp.project.MovieManagement.service;

import com.jp.project.MovieManagement.dto.request.SaveUser;
import com.jp.project.MovieManagement.dto.request.UserSearchCriteria;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<GetUser> findAll(UserSearchCriteria searchCriteria , Pageable pageable);
    GetUser findOneByUsername(String username);
    GetUser saveOne(SaveUser userDto);
    GetUser updateOneByUsername(String username, SaveUser userDto);
    void deleteOneByUsername(String username);

}
