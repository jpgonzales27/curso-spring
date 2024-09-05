package com.jp.project.MovieManagement.persistence.specification;

import com.jp.project.MovieManagement.dto.request.UserSearchCriteria;
import com.jp.project.MovieManagement.persistence.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FindAllUserSpecification implements Specification<User> {

    private UserSearchCriteria userSearchCriteria;

    public FindAllUserSpecification(UserSearchCriteria userSearchCriteria) {
        this.userSearchCriteria = userSearchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.hasText(this.userSearchCriteria.name())){
            Predicate nameLike = criteriaBuilder.like(root.get("name"),"%" + this.userSearchCriteria.name() + "%");
            //u.name like '%this.name%'
            predicates.add(nameLike);
        }

        // select u.*
        // from user u
        // where 1 = 1 and
        //             u.name like '%this.name%'
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
