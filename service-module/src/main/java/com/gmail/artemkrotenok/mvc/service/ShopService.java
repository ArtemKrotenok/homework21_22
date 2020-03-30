package com.gmail.artemkrotenok.mvc.service;

import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import com.gmail.artemkrotenok.mvc.service.model.ShopSearchDTO;

import java.util.List;

public interface ShopService {

    void add(ShopDTO shopDTO);

    List<ShopDTO> findAll();

    ShopDTO findById(Long id);

    List<ShopDTO> searchByParameters(ShopSearchDTO shopSearchDTO);
}
