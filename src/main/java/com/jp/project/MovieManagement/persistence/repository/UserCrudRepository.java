package com.jp.project.MovieManagement.persistence.repository;

import com.jp.project.MovieManagement.persistence.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User,Long> {
    List<User> findByNameContaining(String name);
    Optional<User> findByUsername(String username);

    /**
     * Modifying indica a JPA que la consulta modificara la BD
     * es decir son consultas SQL como DELETE, INSERT o UPDATE
     * y deben ser ejecutas en el contexto transaccional
     */

//    @Modifying
//    void deleteByUsername(String username);

    /**
     * Si hacemos que el metodo de eliminar devuelva un entero
     * podremos saber cuando registros han sido afectados por la eliminacion
     * lo esperado en este caso seria uno solo
     */
    @Modifying
    @Transactional
    int deleteByUsername(String username);
}
