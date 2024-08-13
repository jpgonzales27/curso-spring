package com.jp.project.MovieManagement.persistence.repository;

import com.jp.project.MovieManagement.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCrudRepository extends JpaRepository<User,Long> {
}
