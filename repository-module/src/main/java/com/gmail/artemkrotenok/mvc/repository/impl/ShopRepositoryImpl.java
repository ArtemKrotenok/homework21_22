package com.gmail.artemkrotenok.mvc.repository.impl;

import com.gmail.artemkrotenok.mvc.repository.ShopRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Shop;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ShopRepositoryImpl extends GenericRepositoryImpl<Long, Shop> implements ShopRepository {
    @Override
    public List<Shop> searchByParameters(String location) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shop> query = builder.createQuery(Shop.class);
        Root<Shop> root = query.from(Shop.class);
        ParameterExpression<String> parameterExpression = builder.parameter(String.class);
        query.select(root).where(builder.equal(root.get("location"), parameterExpression));
        TypedQuery<Shop> typedQuery = entityManager.createQuery(query);
        typedQuery.setParameter(parameterExpression, location);
        return typedQuery.getResultList();
    }
}
