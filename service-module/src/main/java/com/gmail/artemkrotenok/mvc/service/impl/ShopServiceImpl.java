package com.gmail.artemkrotenok.mvc.service.impl;

import com.gmail.artemkrotenok.mvc.repository.ShopRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Shop;
import com.gmail.artemkrotenok.mvc.service.ShopService;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    @Transactional
    public void add(ShopDTO shopDTO) {
        Shop shop = getObjectFromDTO(shopDTO);
        shopRepository.persist(shop);
    }

    @Override
    @Transactional
    public List<ShopDTO> findAll() {
        List<Shop> shops = shopRepository.findAll();
        return convertShopListToShopListDTO(shops);
    }

    @Override
    @Transactional
    public ShopDTO findById(Long id) {
        Shop shop = shopRepository.findById(id);
        return getDTOFromObject(shop);
    }

    private List<ShopDTO> convertShopListToShopListDTO(List<Shop> shopList) {
        List<ShopDTO> shopDTOList = new ArrayList<>();
        for (Shop shop : shopList) {
            shopDTOList.add(getDTOFromObject(shop));
        }
        return shopDTOList;
    }

    public static ShopDTO getDTOFromObject(Shop shop) {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(shop.getId());
        shopDTO.setName(shop.getName());
        shopDTO.setLocation(shop.getLocation());
        return shopDTO;
    }

    public static Shop getObjectFromDTO(ShopDTO shopDTO) {
        Shop shop = new Shop();
        shop.setId(shopDTO.getId());
        shop.setName(shopDTO.getName());
        shop.setLocation(shopDTO.getLocation());
        return shop;
    }
}
