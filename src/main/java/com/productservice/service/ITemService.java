package com.productservice.service;

import com.productservice.dto.request.ITemDto;
import com.productservice.dto.request.ITemRequest;
import com.productservice.persistence.entity.Item;

import java.util.List;

public interface ITemService {
    ITemDto create(ITemRequest item);

    Item saveItem(Item item);

    List<Item> getAllItem();

    Item findItemById(Long id);

    void deleteItem(Long id);

    Item findItemByItemNameAndLocation(Long itemId, Long locationId);


    List<Item> getAllItemByLocation(Long id);
}
