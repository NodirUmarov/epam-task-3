package com.epam.business.service;

import com.epam.business.mapper.dto.TagMapper;
import com.epam.business.mapper.request.CreateTagMapper;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.enums.TagSortBy;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.impl.TagServiceImpl;
import com.epam.business.service.provider.CreateTagProvider;
import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.repository.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 9/6/2022
 */
@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagServiceImpl tagServiceImpl;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private CreateTagMapper createTagMapper;

    @Mock
    private TagMapper tagMapper;

    @DisplayName("Should create tag and retrieve")
    @ParameterizedTest
    @ArgumentsSource(CreateTagProvider.class)
    void shouldCreateTagAndRetrieve(List<TagRequest> requests) {
        when(createTagMapper.toEntityList(requests)).thenReturn(requests.stream().map(tagRequest -> {
            Tag tag = new Tag();
            tag.setTagName(tagRequest.getTagName());
            return tag;
        }).collect(Collectors.toList()));

        tagServiceImpl.create(requests);

        ArgumentCaptor<List<Tag>> createTagCaptor = ArgumentCaptor.forClass(List.class);

        verify(tagRepository).findAllOrSaveIfNotExists(createTagCaptor.capture());

        List<Tag> capturedTagEntities = createTagCaptor.getValue();

        assertThat(createTagMapper.toEntityList(requests)).isEqualTo(capturedTagEntities);

    }

    @DisplayName("Should get all tags")
    @ParameterizedTest
    @MethodSource("paginationDetails")
    void shouldGetAllTags(Integer quantity, Integer page) {
        tagServiceImpl.getAllTags(quantity, page, SortType.ASC, TagSortBy.ID);

        ArgumentCaptor<Pageable> pageCaptor = ArgumentCaptor.forClass(Pageable.class);

        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page, quantity, sort);

        verify(tagRepository).findAll(pageCaptor.capture());

        Pageable capturedPageable = pageCaptor.getValue();

        assertThat(capturedPageable).isEqualTo(pageable);
    }


    @DisplayName("Should delete by id")
    @ParameterizedTest
    @MethodSource("idDetails")
    public void shouldDeleteById(Long id) {
        tagServiceImpl.deleteById(id);

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);

        verify(tagRepository).deleteById(idCaptor.capture());

        Long capturedId = idCaptor.getValue();

        assertThat(capturedId).isEqualTo(id);
    }

    private static Stream<Arguments> paginationDetails() {
        return Stream.of(
                Arguments.of(5, 1),
                Arguments.of(15, 3),
                Arguments.of(20, 2),
                Arguments.of(1, 1),
                Arguments.of(50, 1)
        );
    }

    @Test
    void create() {
    }

    @Test
    void getAllTags() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getMostUsed() {
    }
}