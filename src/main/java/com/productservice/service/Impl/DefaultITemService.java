package com.productservice.service.Impl;

import com.productservice.dto.request.ITemDto;
import com.productservice.dto.request.ITemRequest;
import com.productservice.persistence.entity.Category;
import com.productservice.persistence.entity.ITem;
import com.productservice.persistence.repository.CategoryRepository;
import com.productservice.persistence.repository.ITemRepository;
import com.productservice.service.ITemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DefaultITemService implements ITemService {

    private ITemRepository iTemRepository;
    private CategoryRepository categoryRepository;



    @Override
    public ITemDto create(ITemRequest items) {
        ITem iTem = new ITem();

        iTem.setName(items.getName());
        iTem.setDescription(items.getDescription());
        iTem.setStock(items.getStock());
        iTem.setAmount(items.getAmount());
        iTem.setQuantity(items.getQuantity());
        ITem item1 = iTemRepository.save(iTem);
        ITemDto iTemDto = new ITemDto();
        iTemDto.setName(item1.getName());
        iTemDto.setDescription(item1.getDescription());
        iTemDto.setStock(item1.getStock());
        iTemDto.setAmount(item1.getAmount());
        iTemDto.setQuantity(item1.getQuantity());
        return iTemDto;
    }

    @Override
    public ITem saveItem(ITem item) {
        Category selectedCategory = categoryRepository.findById(item.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        item.setCategory(selectedCategory);
        item.setDate(new Date());
        return iTemRepository.save(item);
    }

    @Override
    public List<ITem> getAllItem() {
        return iTemRepository.findAll();
    }

    @Override
    public ITem findItemById(Long id) {
       ITem iTem = iTemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not Found: "+id));
        return iTem;
    }


}
