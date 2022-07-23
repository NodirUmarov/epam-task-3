package com.epam.business.service.impl;

import com.epam.business.mapper.dtoMapper.TagMapper;
import com.epam.business.mapper.requestMapper.CreateTagMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.enums.TagSortBy;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.TagService;
import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.repository.TagRepository;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        tagEntitySet = new HashSet<>(tagRepository.saveAll(tagEntitySet));
        return tagMapper.toDtoSet(tagEntitySet);
    }

    @Override
    public Set<TagDto> getAllTags(Integer quantity, Integer page, SortType sortType, TagSortBy tagSortBy) {
        Sort sort = Sort.by(tagSortBy.getAttributeName());

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

        return tagMapper.toDtoSet(tagRepository.findAll(pageable).toSet());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
