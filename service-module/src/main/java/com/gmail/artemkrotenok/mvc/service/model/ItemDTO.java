package com.gmail.artemkrotenok.mvc.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemDTO {
    private Long id;
    @NotNull(message = "name size must be between 1 and 100 characters")
    @Size(min = 1, max = 100, message = "name size must be between 1 and 100 characters")
    private String name;
    @NotNull(message = "description size must be between 1 and 100 characters")
    @Size(min = 1, max = 100, message = "description size must be between 1 and 100 characters")
    private String description;
    @NotNull(message = "price size must be between 0 and 9223372036854775807")
    private BigDecimal price;
    private List<ShopDTO> shops = new ArrayList<>();
    private Long[] shopIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ShopDTO> getShops() {
        return shops;
    }

    public void setShops(List<ShopDTO> shops) {
        this.shops = shops;
    }

    public Long[] getShopIds() {
        return shopIds;
    }

    public void setShopIds(Long[] shopIds) {
        this.shopIds = shopIds;
    }
}
