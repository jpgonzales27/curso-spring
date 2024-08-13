package com.jp.project.MovieManagement.persistence.repository;

import com.jp.project.MovieManagement.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface UserCrudRepository extends JpaRepository<User,Long> {
    List<User> findByNameContaining(String name);
    User findByUsername(String username);

    /**
     * Modifying indica a JPA que la consulta modificara la BD
     * es decir son consultas SQL como DELETE, INSERT o UPDATE
     * y deben ser ejecutas en el contexto transaccional
     */
    @Modifying
    void deleteByUsername(String username);
}
