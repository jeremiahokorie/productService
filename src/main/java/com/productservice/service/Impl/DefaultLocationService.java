package com.productservice.service.Impl;

import com.productservice.core.exceptions.CustomException;
import com.productservice.dto.request.LocationRequest;
import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.Location;
import com.productservice.persistence.repository.LocationRepository;
import com.productservice.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultLocationService implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location createLocation(Location location) {
        // check if location already exists
           if (locationRepository.findBylocationName(location.getLocationName()).isPresent()) {
            throw new CustomException("Location Already exist, Try another location");
        }
        locationRepository.save(location);
        return location;
    }
}
