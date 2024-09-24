package com.jp.project.MovieManagement.service.Impl;

import com.jp.project.MovieManagement.dto.request.SaveUser;
import com.jp.project.MovieManagement.dto.request.UserSearchCriteria;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.exception.ObjectNotFoundException;
import com.jp.project.MovieManagement.mapper.UserMapper;
import com.jp.project.MovieManagement.persistence.entity.User;
import com.jp.project.MovieManagement.persistence.repository.UserCrudRepository;
import com.jp.project.MovieManagement.persistence.specification.FindAllUserSpecification;
import com.jp.project.MovieManagement.service.UserService;
import com.jp.project.MovieManagement.service.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserCrudRepository userCrudRepository;

    @Override
    public Page<GetUser> findAll(UserSearchCriteria searchCriteria, Pageable pageable) {
        FindAllUserSpecification userSpecification = new FindAllUserSpecification(searchCriteria);
        Page<User> entities = userCrudRepository.findAll(userSpecification,pageable);
        return entities.map(UserMapper::toGetDto);
    }

    @Override
    public GetUser findOneByUsername(String username) {
        return UserMapper.toGetDto(this.findOneEntityByUsername(username));
    }

    public User findOneEntityByUsername(String username) {
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
