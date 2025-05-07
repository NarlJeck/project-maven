package com.narel.spring.http.controller;

import com.narel.spring.dto.CarReadDto;
import com.narel.spring.dto.PageResponse;
import com.narel.spring.filter.CarFilter;
import com.narel.spring.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public String findAll(Model model, CarFilter filter, Pageable pageable) {
        Page<CarReadDto> page = carService.findAll(filter, pageable);
        model.addAttribute("cars", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "car/cars";
    }
}
