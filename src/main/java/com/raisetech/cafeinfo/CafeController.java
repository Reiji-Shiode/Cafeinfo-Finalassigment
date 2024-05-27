package com.raisetech.cafeinfo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CafeController {

    public CafeService cafeService;

    public CafeController(CafeService cafeService) {
        this.cafeService = cafeService;
    }

    @GetMapping("/cafes")
    public List<Cafe> findCafePlaces(@RequestParam(value = "place", required = false) String place) {
        return cafeService.findByPlaces(place);
    }

    @GetMapping("/cafes/{id}")
    public Cafe findInformation(@PathVariable("id") int id) {
        return cafeService.findById(id);
    }
}
