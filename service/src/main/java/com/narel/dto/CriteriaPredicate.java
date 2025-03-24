package com.narel.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CriteriaPredicate {

    private final List<Predicate> predicates = new ArrayList<>();
    private final CriteriaBuilder criteriaBuilder;

    public CriteriaPredicate(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }
    public static CriteriaPredicate builder() {
        return new CriteriaPredicate(builder().criteriaBuilder);
    }
    public <T> CriteriaPredicate add(T object, Function<T, Predicate> function) {
        if (object != null) {
            predicates.add(function.apply(object));
        }
        return this;
    }
    public Predicate buildAnd() {
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
    public Predicate buildOr() {
        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }
}


