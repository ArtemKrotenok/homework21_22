package com.gmail.artemkrotenok.mvc.repository;

import com.gmail.artemkrotenok.mvc.repository.model.Shop;

import java.util.List;

public interface ShopRepository extends GenericRepository<Long, Shop> {
    List<Shop> searchByParameters(String location);
}
