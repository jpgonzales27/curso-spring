package com.jp.project.MovieManagement.service.Impl;

import com.jp.project.MovieManagement.dto.request.SaveUser;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.mapper.UserMapper;
import com.jp.project.MovieManagement.persistence.entity.User;
import com.jp.project.MovieManagement.persistence.repository.UserCrudRepository;
import com.jp.project.MovieManagement.service.UserService;
import com.jp.project.MovieManagement.service.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    public List<GetUser> findAll() {
        List<User> entities = userCrudRepository.findAll();
        return UserMapper.toGetDtoList(entities);
    }

    @Override
    public List<GetUser> findAllByName(String name) {
        List<User> entities = userCrudRepository.findByNameContaining(name);
        return UserMapper.toGetDtoList(entities);
    }

    @Override
    public GetUser findOneByUsername(String username) {
        return UserMapper.toGetDto(this.findOneEntityByUsername(username));
    }

    private User findOneEntityByUsername(String username) {
        return userCrudRepository.findByUsername(username)
                .orElseThrow( () -> new ObjectNotFoundException("[user:" + username + "]"));
    }

    @Override
    public GetUser saveOne(SaveUser userDto) {
        PasswordValidator.validatedPassword(userDto.password(), userDto.passwordRepeated());
        User user = UserMapper.toEntity(userDto);
        return UserMapper.toGetDto(userCrudRepository.save(user));
    }

    @Override
    public GetUser updateOneByUsername(String username, SaveUser userDto) {
        PasswordValidator.validatedPassword(userDto.password(), userDto.passwordRepeated());
        User oldUser = this.findOneEntityByUsername(username);
        UserMapper.updateEntity(oldUser, userDto);
        return UserMapper.toGetDto(userCrudRepository.save(oldUser));

    }

    @Override
    public void deleteOneByUsername(String username) {
//        User user = this.findOneByUsername(username);
//        userCrudRepository.delete(user);

        if (userCrudRepository.deleteByUsername(username) != 1) {
            throw new ObjectNotFoundException("[user:" + username + "]");
        }
    }
}
