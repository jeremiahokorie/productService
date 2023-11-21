package com.productservice.persistence.repository;

import com.productservice.persistence.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByIdAndLocation_Id(Long itemId, Long locationId);

    List<Item> findByLocation_Id(Long id);
}
