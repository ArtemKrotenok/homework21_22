package com.gmail.artemkrotenok.mvc.service.impl;

import com.gmail.artemkrotenok.mvc.repository.ShopRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Shop;
import com.gmail.artemkrotenok.mvc.service.ShopService;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import com.gmail.artemkrotenok.mvc.service.util.ShopConverterUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    @Transactional
    public void add(ShopDTO shopDTO) {
        Shop shop = ShopConverterUtil.getObjectFromDTO(shopDTO);
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
        return ShopConverterUtil.getDTOFromObject(shop);
    }

    private List<ShopDTO> convertShopListToShopListDTO(List<Shop> shopList) {
        List<ShopDTO> shopDTOList = new ArrayList<>();
        for (Shop shop : shopList) {
            shopDTOList.add(ShopConverterUtil.getDTOFromObject(shop));
        }
        return shopDTOList;
    }
}
