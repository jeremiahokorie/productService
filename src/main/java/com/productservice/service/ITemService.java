package com.productservice.service;

import com.productservice.dto.request.ITemDto;
import com.productservice.dto.request.ITemRequest;
import com.productservice.persistence.entity.ITem;

import java.util.List;

public interface ITemService {
    ITemDto create(ITemRequest item);

    ITem saveItem(ITem item);

    List<ITem> getAllItem();

    ITem findItemById(Long id);
}
