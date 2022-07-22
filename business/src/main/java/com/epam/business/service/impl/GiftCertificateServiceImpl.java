package com.epam.business.service.impl;

import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.mapper.dtoMapper.GiftCertificateMapper;
import com.epam.business.mapper.requestMapper.CreateGiftCertificateMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.business.service.TagService;
import com.epam.domain.repository.GiftCertificateRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagService tagService;
    private final GiftCertificateMapper giftCertificateMapper;
    private final CreateGiftCertificateMapper createGiftCertificateMapper;


    @Override
    public GiftCertificateDto create(CreateGiftCertificateRequest request) throws EntityExistsException {
        return giftCertificateMapper.toDto(giftCertificateRepository.save(createGiftCertificateMapper.toEntity(request)));
    }

    @Override
    public GiftCertificateDto getById(Long id) throws EntityIdNotFoundException {
        return giftCertificateMapper.toDto(giftCertificateRepository.findById(id).orElseThrow(EntityIdNotFoundException::new));
    }

    @Override
    public GiftCertificateDto getByName(String name) throws EntityNameNotFoundException {
        return null;
    }

    @Override
    public Set<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType) {
        return null;
    }

    @Override
    public void deleteById(Long id) throws EntityIdNotFoundException {

    }

    @Override
    public GiftCertificateDto updateById(Long id, UpdateGiftCertificateRequest request) throws EntityIdNotFoundException {
        return null;
    }

    @Override
    public GiftCertificateDto untag(Long id, Set<TagRequest> tags) throws EntityIdNotFoundException {
        return null;
    }

    @Override
    public GiftCertificateDto addTags(Long id, Set<TagRequest> tags) throws EntityIdNotFoundException {
        return null;
    }
}
