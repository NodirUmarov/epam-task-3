package com.epam.business.service.impl;

import com.epam.business.mapper.dtoMapper.TagMapper;
import com.epam.business.mapper.requestMapper.CreateTagMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.TagService;
import com.epam.data.dao.TagDao;
import com.epam.data.model.entity.TagEntity;

import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.repository.TagRepository;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final CreateTagMapper createTagMapper;

    @Override
    public Set<TagDto> create(Set<TagRequest> tags) {
        Set<Tag> tagEntitySet = createTagMapper.toEntitySet(tags);
        tagEntitySet = tagRepository.saveAll(tagEntitySet);
        return tagMapper.toDtoSet(tagEntitySet);
    }

    @Override
    public Set<TagDto> getAllTags(Integer quantity, Integer page) {
        return tagMapper.toDtoSet(tagRepository.findAllSorted(quantity, getOffset(quantity, page)));
    }

    private Integer getOffset(Integer quantity, Integer page) {
        return (page - 1) * quantity;
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
