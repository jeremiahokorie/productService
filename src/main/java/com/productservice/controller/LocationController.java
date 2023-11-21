package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.persistence.entity.Location;
import com.productservice.service.LocationService;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppConstant.APP_CONTEXT)
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/createLocation")
    public ResponseEntity<Location> createLocation(@RequestBody Location location){
        Location locations = locationService.createLocation(location);
        return ResponseEntity.ok(locations);
    }

}
