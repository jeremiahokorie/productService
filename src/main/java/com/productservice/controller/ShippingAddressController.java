package com.productservice.controller;

import com.productservice.core.constants.AppConstant;
import com.productservice.dto.request.ShippingAddressDto;
import com.productservice.dto.request.ShippingAddressRequest;
import com.productservice.persistence.entity.ShippingAddress;
import com.productservice.service.ShippingAddressService;
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
public class ShippingAddressController {

    private final ShippingAddressService shippingAddress;

    @PostMapping("/shippingAddress")
    public ResponseEntity<?>createAddress(@RequestBody ShippingAddressRequest shippingAddressRequest){
        Map<String,Object> map = new LinkedHashMap<>();
        ShippingAddressDto shippingAddress1 = shippingAddress.createAddress(shippingAddressRequest);
        map.put("status","201");
        map.put("message","successfully created");
        map.put("data",shippingAddress1);
        return new ResponseEntity(map, HttpStatus.CREATED);
    }

    @GetMapping("/getAddress")
    public ResponseEntity<?> getAllAddress(){
        List<ShippingAddress> addresses = shippingAddress.getShippingAddress();
        return new ResponseEntity(addresses,HttpStatus.OK);
    }

    @GetMapping("/{AddressId}")
    public ShippingAddress getAddressById(@PathVariable("AddressId") Long id){
        return shippingAddress.getShippingAddressById(id);
    }

    @DeleteMapping("/deleteAddress/{AddressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable("AddressId") Long id){
        shippingAddress.deleteAddress(id);
        return new ResponseEntity<>("Address deleted successfully",HttpStatus.OK);
    }
}
