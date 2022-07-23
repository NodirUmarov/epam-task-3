package com.epam.business.service.impl;

import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.mapper.dtoMapper.GiftCertificateMapper;
import com.epam.business.mapper.requestMapper.CreateGiftCertificateMapper;
import com.epam.business.mapper.requestMapper.UpdateGiftCertificateMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.enums.GiftCertificateSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.business.service.utils.BeanCopyUtils;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.repository.GiftCertificateRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final GiftCertificateMapper giftCertificateMapper;
    private final CreateGiftCertificateMapper createGiftCertificateMapper;
    private final UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @Override
    public GiftCertificateDto create(CreateGiftCertificateRequest request) throws EntityExistsException {
        GiftCertificate toSave = createGiftCertificateMapper.toEntity(request);
        return giftCertificateMapper.toDto(giftCertificateRepository.save(toSave));
    }

    @Override
    public GiftCertificateDto getById(Long id) throws EntityIdNotFoundException {
        return giftCertificateMapper.toDto(giftCertificateRepository.findById(id).orElseThrow(EntityIdNotFoundException::new));
    }

    @Override
    public GiftCertificateDto getByName(String name) throws EntityNameNotFoundException {
        return giftCertificateMapper.toDto(giftCertificateRepository.findByCertificateName(name)
                .orElseThrow(EntityNameNotFoundException::new));
    }

    @Override
    public Set<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType, GiftCertificateSortBy giftCertificateSortBy) {
        Sort sort = Sort.by(giftCertificateSortBy.getAttributeName());

        switch (sortType) {
            case NONE:
                break;
            case ASC:
                sort.ascending();
                break;
            case DESC:
                sort.descending();
                break;
        }

        Pageable pageable = PageRequest.of(page, quantity, sort);

        return giftCertificateMapper.toDtoSet(giftCertificateRepository.findByTags_TagName(pageable).toSet());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        giftCertificateRepository.deleteById(id);
    }

    @Override
    public GiftCertificateDto updateById(Long id, UpdateGiftCertificateRequest request) throws EntityIdNotFoundException {
        GiftCertificate toUpdate = giftCertificateRepository.findById(id).orElseThrow(EntityIdNotFoundException::new);
        BeanCopyUtils.copyNonNullProperties(updateGiftCertificateMapper.toEntity(request), toUpdate);
        return giftCertificateMapper.toDto(giftCertificateRepository.save(toUpdate));
    }

    @Override
    public GiftCertificateDto changeSetOfTags(Long id, Set<TagRequest> tags) throws EntityIdNotFoundException {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).orElseThrow(EntityIdNotFoundException::new);
        return giftCertificateMapper.toDto(giftCertificateRepository.save(giftCertificate));
    }
}
