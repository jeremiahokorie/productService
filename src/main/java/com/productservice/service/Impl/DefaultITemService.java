package com.productservice.service.Impl;

import com.productservice.core.exceptions.CustomException;
import com.productservice.dto.request.ITemDto;
import com.productservice.dto.request.ITemRequest;
import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.Item;
import com.productservice.persistence.entity.Location;
import com.productservice.persistence.repository.CategoryRepository;
import com.productservice.persistence.repository.ITemRepository;
import com.productservice.persistence.repository.LocationRepository;
import com.productservice.service.ITemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class DefaultITemService implements ITemService {
    private ITemRepository iTemRepository;
    private CategoryRepository categoryRepository;
    private LocationRepository locationRepository;


    @Override
    public ITemDto create(ITemRequest items) {
        Item iTem = new Item();
        iTem.setName(items.getName());
        iTem.setDescription(items.getDescription());
        iTem.setStock(items.getStock());
        iTem.setAmount(items.getAmount());
        iTem.setQuantity(items.getQuantity());
        Item item1 = iTemRepository.save(iTem);
        ITemDto iTemDto = new ITemDto();
        iTemDto.setName(item1.getName());
        iTemDto.setDescription(item1.getDescription());
        iTemDto.setStock(item1.getStock());
        iTemDto.setAmount(item1.getAmount());
        iTemDto.setQuantity(item1.getQuantity());
        return iTemDto;
    }

    @Override
    public Item saveItem(Item item) {
        Category selectedCategory = categoryRepository.findById(item.getCategory().getId())
                .orElseThrow(() -> new CustomException("Category not found"));

        Location location = locationRepository.findById(item.getLocation().getId())
                .orElseThrow(() -> new CustomException("Location not found"));

        item.setCategory(selectedCategory);
        item.setLocation(location);
        item.setDate(new Date());
        return iTemRepository.save(item);
    }

    @Override
    public List<Item> getAllItem() {
        return iTemRepository.findAll();
    }

    @Override
    public Item findItemById(Long id) {
       Item iTem = iTemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: "+id));
        return iTem;
    }


    @Override
    public void deleteItem(Long id) {
        iTemRepository.deleteById(id);
    }


    public Item findItemByItemNameAndLocation(Long itemId, Long locationId) {
        Item iTem = iTemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException("Not Found: "+itemId));

        Optional<Item> optionalItem = iTemRepository.findByIdAndLocation_Id(itemId, locationId);

        return optionalItem.orElse(iTem);
    }




    @Override
    public List<Item> getAllItemByLocation(Long id) {
        Item iTem = iTemRepository.findById(id)
                .orElseThrow(() -> new CustomException("Not Found: "+id));

        return iTemRepository.findByLocation_Id(id);
    }


}
