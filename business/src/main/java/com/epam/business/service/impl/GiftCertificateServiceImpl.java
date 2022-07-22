package com.epam.business.service.impl;

import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.mapper.dtoMapper.GiftCertificateMapper;
import com.epam.business.mapper.requestMapper.CreateGiftCertificateMapper;
import com.epam.business.mapper.requestMapper.UpdateGiftCertificateMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.business.service.TagService;
import com.epam.data.dao.GiftCertificateDao;
import com.epam.data.model.entity.GiftCertificateEntity;
import com.epam.lib.constants.SortType;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDao giftCertificateDao;
    private final TagService tagService;
    private final GiftCertificateMapper giftCertificateMapper;
    private final CreateGiftCertificateMapper createGiftCertificateMapper;
    private final UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @Override
    public GiftCertificateDto create(CreateGiftCertificateRequest request) {
        tagService.create(request.getTags());
        GiftCertificateEntity entity = giftCertificateDao.save(createGiftCertificateMapper.toEntity(request));
        return giftCertificateMapper.toDto(entity);
    }

    @Override
    public GiftCertificateDto getById(Long id) {
        return giftCertificateMapper.toDto(giftCertificateDao.findById(id).orElseThrow(EntityIdNotFoundException::new));
    }

    @Override
    public GiftCertificateDto getByName(String name) throws EntityNameNotFoundException {
        return giftCertificateMapper.toDto(giftCertificateDao.findByName(name)
                .orElseThrow(EntityNameNotFoundException::new));
    }

    @Override
    public Set<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType) {
        int offset = (page - 1) * quantity;
        Set<GiftCertificateEntity> entities = new HashSet<>(giftCertificateDao.findByTag(tag, quantity, offset, sortType));
        return giftCertificateMapper.toDtoSet(entities);
    }

    @Override
    public void deleteById(Long id) {
        giftCertificateDao.deleteById(id);
    }

    @Override
    public GiftCertificateDto updateById(Long id, UpdateGiftCertificateRequest request) throws EntityIdNotFoundException {
        GiftCertificateEntity entity = updateGiftCertificateMapper.toEntity(request);
        entity.setId(id);

        try {
            entity = giftCertificateDao.save(entity);
        } catch (EntityIdNotFoundException ex) {
            throw new EntityIdNotFoundException();
        }
        return giftCertificateMapper.toDto(entity);
    }

    @Override
    public GiftCertificateDto untag(Long id, Set<TagRequest> tags) {
        return giftCertificateMapper.toDto(giftCertificateDao
                .untagCertificate(id, tags.stream().map(TagRequest::getName).collect(Collectors.toSet())));
    }

    @Override
    public GiftCertificateDto addTags(Long id, Set<TagRequest> tags) {
        tagService.create(tags);
        GiftCertificateEntity entity = giftCertificateDao.addTag(id, tags.stream().map(TagRequest::getName).collect(Collectors.toSet()));
        return giftCertificateMapper.toDto(entity);
    }
}
