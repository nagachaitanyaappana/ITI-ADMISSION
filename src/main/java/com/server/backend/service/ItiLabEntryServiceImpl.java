package com.server.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.server.backend.DTO.ItiLabsEntryDto;
import com.server.backend.DTO.ItiLabsEntryDto.LabItemDTO;
import com.server.backend.Repository.ItiLabsEntryRepository;
import com.server.backend.Repository.ItiLabItemsRepository;
import com.server.backend.entity.labs.Labs;
import com.server.backend.entity.labs.LabItems;

@Service
public class ItiLabEntryServiceImpl implements ItiLabEntryService {

    private final ItiLabsEntryRepository itiLabEntryRepository;
    private final ItiLabItemsRepository itiLabItemsRepository;

    public ItiLabEntryServiceImpl(
            ItiLabsEntryRepository itiLabEntryRepository,
            ItiLabItemsRepository itiLabItemsRepository) {

        this.itiLabEntryRepository = itiLabEntryRepository;
        this.itiLabItemsRepository = itiLabItemsRepository;
    }

    @Override
    @Transactional
    public void saveLabEntry(ItiLabsEntryDto dto) {

        // Save main lab information
        Labs lab = new Labs();

        lab.setItiCode(dto.getItiCode());
        lab.setIndustryName(dto.getIndustryName());
        lab.setTradeShort(dto.getTradeShort());
        lab.setDescription(dto.getDescription());

        Labs savedLab = itiLabEntryRepository.save(lab);

        // Save equipment/items
        if (dto.getItems() != null) {

            for (LabItemDTO itemDTO : dto.getItems()) {

                LabItems item = new LabItems();

                item.setItiCode(dto.getItiCode());
                item.setLabId(savedLab.getLabId());
                item.setItemName(itemDTO.getItemName());
                item.setItemCost(itemDTO.getItemCost());
                item.setItemPhoto(itemDTO.getItemPhoto());

                itiLabItemsRepository.save(item);
            }
        }
    }
}