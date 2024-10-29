package org.example.food.infrastructure.search.controller;

import lombok.RequiredArgsConstructor;
import org.example.food.infrastructure.search.service.NaverSearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NaverSearchController {

    private final NaverSearchService naverSearchService;

    @GetMapping("/search")
    public String search(@RequestParam String query) {
        return naverSearchService.searchLocal(query);
    }
}
