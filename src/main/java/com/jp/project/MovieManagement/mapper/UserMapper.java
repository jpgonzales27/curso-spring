package com.jp.project.MovieManagement.mapper;

import com.jp.project.MovieManagement.dto.request.SaveUser;
import com.jp.project.MovieManagement.dto.response.GetUser;
import com.jp.project.MovieManagement.persistence.entity.User;

import java.util.List;

public class UserMapper {

    public static GetUser toGetDto(User entity) {
        if (entity == null) return null;

        return new GetUser(
                entity.getUsername(),
                entity.getName(),
                null
        );
    }

    public static List<GetUser> toGetDtoList(List<User> entities) {
        if(entities == null) return null;

        return entities.stream()
                .map(UserMapper::toGetDto)
                .toList();
    }

    public static User toEntity (SaveUser saveDto) {
        User newUser = new User();
        newUser.setUsername(saveDto.username());
        newUser.setName(saveDto.name());
        newUser.setPassword(saveDto.password());

        return newUser;
    }

    public static void updateEntity(User oldUser, SaveUser userDto) {
        if(oldUser == null || userDto == null) return;
        oldUser.setName(userDto.name());
        oldUser.setPassword(userDto.password());
    }
}
