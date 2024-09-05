package com.jp.project.MovieManagement.persistence.repository;

import com.jp.project.MovieManagement.persistence.entity.Movie;
import com.jp.project.MovieManagement.persistence.entity.User;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User,Long> , JpaSpecificationExecutor<User> {

    /**
     * Especificaion en linea
     * pero es mejor crear la clase Specification y su DTO para los criterios de busqueda
     * y asi desacoplar mas y cuando queramos mandar mas parametros de buqueda no afectemos
     * a esta clase en lo absoluto
     */
    default Page<User> findByNameContaining(String name, Pageable pageable){
        Specification<User> userSpecification = (root, query, builder) -> {
            if(StringUtils.hasText(name)){
                Predicate nameLike = builder.like(root.get("name"), "%" + name + "%");
                return nameLike;
            }

            return null;
        };

        return this.findAll(userSpecification, pageable);
    }

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
