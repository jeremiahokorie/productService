package com.productservice.service;

import com.productservice.dto.request.ITemDto;
import com.productservice.dto.request.ITemRequest;
import com.productservice.persistence.entity.ITem;

public interface ITemService {
    ITemDto create(ITemRequest item);

    ITem saveItem(ITem item);
}
