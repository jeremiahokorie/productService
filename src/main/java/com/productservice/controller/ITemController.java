package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.ITemDto;
import com.productservice.dto.request.ITemRequest;
import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.ITem;
import com.productservice.service.ITemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(AppConstant.APP_CONTEXT)
@RequiredArgsConstructor
public class ITemController {

    private final ITemService iTemService;

    @PostMapping("/createItem")
    public ResponseEntity<ITemDto> createItem(@RequestBody ITemRequest item){
        Map<String, Object> map = new LinkedHashMap<>();
        ITemDto iTem = iTemService.create(item);
        map.put("status","201");
        map.put("message","Item Created Successfully");
        map.put("data",iTem);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ITem create(@RequestBody ITem item){
        return iTemService.saveItem(item);
    }
}
