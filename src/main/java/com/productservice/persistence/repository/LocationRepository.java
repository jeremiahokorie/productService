package com.productservice.persistence.repository;

import com.productservice.persistence.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

    Optional<Location> findBylocationName(String locationName);
}
