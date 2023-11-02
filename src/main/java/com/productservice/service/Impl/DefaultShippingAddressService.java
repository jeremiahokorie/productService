package com.productservice.service.Impl;

import com.productservice.core.exceptions.CustomException;
import com.productservice.dto.request.ShippingAddressDto;
import com.productservice.dto.request.ShippingAddressRequest;
import com.productservice.persistence.entity.ShippingAddress;
import com.productservice.persistence.repository.ShippingAddressRepository;
import com.productservice.service.ShippingAddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultShippingAddressService implements ShippingAddressService {

    ShippingAddressRepository shippingAddressRepository;

    @Override
    public ShippingAddressDto createAddress(ShippingAddressRequest shippingAddressRequest) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setAddress(shippingAddressRequest.getAddress());
        shippingAddress.setCity(shippingAddressRequest.getCity());
        shippingAddress.setState(shippingAddressRequest.getState());
        shippingAddress.setPhone(shippingAddressRequest.getPhone());
        shippingAddress.setDate(new Date());
        ShippingAddress shippingAddress1 = shippingAddressRepository.save(shippingAddress);
        ShippingAddressDto shippingAddressDto = new ShippingAddressDto();
        shippingAddressDto.setAddress(shippingAddress1.getAddress());
        shippingAddressDto.setCity(shippingAddress1.getCity());
        shippingAddressDto.setState(shippingAddress1.getState());
        shippingAddressDto.setPhone(shippingAddress1.getPhone());
        shippingAddressDto.setDate(new Date());
        return shippingAddressDto;
    }

    @Override
    public List<ShippingAddress> getShippingAddress() {
        return shippingAddressRepository.findAll();
    }

    @Override
    public ShippingAddress getShippingAddressById(Long id) {
        return shippingAddressRepository.findById(id)
                .orElseThrow(() -> new CustomException("Address not found"));

    }


}
