package com.gmail.artemkrotenok.mvc.web.controller;

import com.gmail.artemkrotenok.mvc.service.ShopService;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import com.gmail.artemkrotenok.mvc.service.model.ShopSearchDTO;
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
@RequestMapping("/shops")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping()
    public String getShopsPage(Model model) {
        List<ShopDTO> shopsDTO = shopService.findAll();
        model.addAttribute("shops", shopsDTO);
        return "shops";
    }

    @GetMapping("/search")
    public String getSearchShopPage() {
        return "shops_search";
    }

    @PostMapping("/search")
    public String getResultSearch(
            @ModelAttribute(name = "shopSearch") ShopSearchDTO shopSearchDTO,
            Model model) {
        List<ShopDTO> shopsDTO = shopService.searchByParameters(shopSearchDTO);
        model.addAttribute("shops", shopsDTO);
        return "shops_search_result";
    }

    @GetMapping("/add")
    public String addShopPage(Model model) {
        model.addAttribute("shop", new ShopDTO());
        return "shop_add";
    }

    @PostMapping()
    public String addShop(
            Model model,
            @Valid @ModelAttribute(name = "shop") ShopDTO shopDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shop", shopDTO);
            return "shop_add";
        }
        shopService.add(shopDTO);
        return "redirect:/shops";
    }
}
