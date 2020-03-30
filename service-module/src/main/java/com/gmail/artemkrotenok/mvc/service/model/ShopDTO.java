package com.gmail.artemkrotenok.mvc.service.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ShopDTO {

    private Long id;
    @NotNull(message = "name size must be between 1 and 100 characters")
    @Size(min = 1, max = 100, message = "name size must be between 1 and 100 characters")
    private String name;
    @NotNull(message = "location size must be between 1 and 100 characters")
    @Size(min = 1, max = 100, message = "location size must be between 1 and 100 characters")
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
