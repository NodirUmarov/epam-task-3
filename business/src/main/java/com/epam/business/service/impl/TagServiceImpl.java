package com.epam.business.service.impl;

import com.epam.business.mapper.dto.TagMapper;
import com.epam.business.mapper.request.CreateTagMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.enums.TagSortBy;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.TagService;
import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.repository.TagRepository;
import java.util.List;
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
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final CreateTagMapper createTagMapper;

    @Override
    public List<TagDto> create(List<TagRequest> tags) {
        log.info("Saving tags to database");
        List<Tag> tagEntitySet = createTagMapper.toEntityList(tags);
        tagEntitySet = tagRepository.findAllOrSaveIfNotExists(tagEntitySet);
        log.info("New tags saved and existing tags retrieved with ids");

        return tagMapper.toDtoList(tagEntitySet);
    }

    @Override
    public List<TagDto> getAllTags(Integer quantity, Integer page, SortType sortType, TagSortBy tagSortBy) {
        log.info("Getting all tags");
        Sort sort = Sort.by(sortType.getDirection(), tagSortBy.getAttributeName());

        Pageable pageable = PageRequest.of(page, quantity, sort);
        List<Tag> tagEntities = tagRepository.findAll(pageable).toList();
        log.info("{} objects retrieved from database", tagEntities.size());
        return tagMapper.toDtoList(tagEntities);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        log.info("Deleting the tag");
        tagRepository.deleteById(id);
        log.info("Tag with id={} deleted successfully", id);
    }

    @Override
    public List<TagDto> getMostUsed(Integer quantity) {
        Pageable pageable = PageRequest.of(0, quantity);
        return tagMapper.toDtoList(tagRepository.findMostUsed(pageable).toList());
    }
}