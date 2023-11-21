package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.ITemDto;
import com.productservice.dto.request.ITemRequest;
import com.productservice.persistence.entity.Item;
import com.productservice.service.ITemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
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

    @PostMapping("/Item/create")
    public ResponseEntity<Item> create(@RequestBody Item item){
        Map<String,Object> map = new LinkedHashMap<>();
        Item items = iTemService.saveItem(item);
        map.put("status","200");
        map.put("message","Item successfully created");
        map.put("data",items);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @GetMapping("/getAllItem")
    public ResponseEntity<Item> getAllItem(){
        Map<String,Object> map = new LinkedHashMap<>();
        List<Item> item = iTemService.getAllItem();
        map.put("status","200");
        map.put("message","successfully retrieved");
        map.put("data",item);
        return new ResponseEntity(map,HttpStatus.OK);
    }

    @GetMapping("/Item/{id}")
    public ResponseEntity<?>getITemById(@PathVariable("id") Long id){
        Item iTem = iTemService.findItemById(id);
        return new ResponseEntity<>(iTem, HttpStatus.FOUND);
    }

    @DeleteMapping("/Item/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable("id") Long id){
        iTemService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //findItem using item name and location

    @GetMapping("/{itemId}/locations/{id}")
    public ResponseEntity<Item> findItemByItemNameAndLocation(@PathVariable("itemId") Long itemId, @PathVariable("id") Long locationId){
        Item iTem = iTemService.findItemByItemNameAndLocation(itemId, locationId);
        if (iTem != null) {
            return new ResponseEntity<>(iTem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //findItem using location
    @GetMapping("/locations/{id}")
    public ResponseEntity<?> findItemByLocation(@PathVariable("id") Long id){
        List<Item> items = iTemService.getAllItemByLocation(id);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
