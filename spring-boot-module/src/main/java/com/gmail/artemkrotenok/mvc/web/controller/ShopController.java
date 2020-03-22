package com.gmail.artemkrotenok.mvc.web.controller;

import com.gmail.artemkrotenok.mvc.service.ShopService;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping()
    public String getShops(Model model) {
        List<ShopDTO> shopsDTO = shopService.findAll();
        model.addAttribute("shops", shopsDTO);
        return "shops";
    }

    @GetMapping("/add")
    public String addShopPage(Model model) {
        model.addAttribute("shop", new ShopDTO());
        return "shop_add";
    }

    @PostMapping
    public String addShop(
            @Valid @ModelAttribute(name = "shop") ShopDTO shopDTO,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shop", shopDTO);
            return "shop_add";
        }
        shopService.add(shopDTO);
        return "redirect:/shops";
    }
}
