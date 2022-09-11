package com.epam.business.service;

import com.epam.business.client.MailSenderClient;
import com.epam.business.mapper.dto.GiftCertificateMapper;
import com.epam.business.mapper.dto.TagMapper;
import com.epam.business.mapper.dto.UserDetailsMapper;
import com.epam.business.mapper.request.CreateGiftCertificateMapper;
import com.epam.business.mapper.request.CreateTagMapper;
import com.epam.business.mapper.request.UpdateGiftCertificateMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.business.model.enums.GiftCertificateSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.impl.GiftCertificateServiceImpl;
import com.epam.business.service.provider.CreateGiftCertificateProvider;
import com.epam.business.service.provider.CreateTagProvider;
import com.epam.business.service.provider.UpdateGiftCertificateProvider;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.entity.user.UserDetails;
import com.epam.domain.repository.GiftCertificateRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagService tagService;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private CreateTagMapper createTagMapper;

    @Spy
    private MailSenderClient mailSenderClient;

    @Mock
    private UserDetailsMapper userDetailsMapper;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @Mock
    private CreateGiftCertificateMapper createGiftCertificateMapper;

    @Mock
    private UpdateGiftCertificateMapper updateGiftCertificateMapper;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateServiceImpl;

    @DisplayName("Testing create method")
    @ParameterizedTest
    @ArgumentsSource(CreateGiftCertificateProvider.class)
    void create(CreateGiftCertificateRequest request) {
        setUpUserDetailsMapper();
        setUpUserService(request.getCreatedBy());
        setUpTagMapper(request.getTags());
        setUpCreateGiftCertificateMapper(request);
        setUpTagService(request.getTags());

        giftCertificateServiceImpl.create(request);

        ArgumentCaptor<GiftCertificate> captor = ArgumentCaptor.forClass(GiftCertificate.class);

        verify(giftCertificateRepository).save(captor.capture());

        GiftCertificate captured = captor.getValue();

        assertThat(captured).usingRecursiveComparison().isEqualTo(createGiftCertificateMapper.toEntity(request));
    }

    @DisplayName("Testing getByTag method")
    @ParameterizedTest
    @ArgumentsSource(CreateTagProvider.class)
    void getByTag(List<TagRequest> tagRequests) {
        Random random = new Random();

        String tag = tagRequests.stream().findAny().get().getTagName();
        Integer page = 1 + random.nextInt(10);
        Integer quantity = 5;
        GiftCertificateSortBy giftCertificateSortBy = GiftCertificateSortBy.ID;

        SortType sortType = SortType.ASC;

        Sort sort = Sort.by(Direction.ASC, giftCertificateSortBy.getAttributeName());
        Pageable pageable = PageRequest.of(page, quantity, sort);

        when(giftCertificateRepository.findByTags_TagName(tag, pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        giftCertificateServiceImpl.getByTag(tag, quantity, page, sortType, giftCertificateSortBy);

        ArgumentCaptor<String> tagCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(giftCertificateRepository).findByTags_TagName(tagCaptor.capture(), pageableCaptor.capture());

        String capturedTag = tagCaptor.getValue();
        Pageable capturedPageable = pageableCaptor.getValue();

        assertThat(capturedTag).isEqualTo(tag);
        assertThat(capturedPageable).isEqualTo(pageable);
    }

    @DisplayName("Testing deleteById method")
    @ParameterizedTest
    @MethodSource("generateName")
    void deleteById(String name) {
        when(giftCertificateRepository.existsByCertificateName(name)).thenReturn(true);

        giftCertificateServiceImpl.deleteByName(name);

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);

        verify(giftCertificateRepository).deleteByCertificateName(nameCaptor.capture());

        String capturedName = nameCaptor.getValue();

        assertThat(capturedName).isEqualTo(name);
    }

    @DisplayName("Testing update method")
    @ParameterizedTest
    @ArgumentsSource(UpdateGiftCertificateProvider.class)
    void update(Long id, UpdateGiftCertificateRequest request) {
        when(giftCertificateRepository.findById(id)).thenReturn(Optional.of(new GiftCertificate()));

        when(updateGiftCertificateMapper.toEntity(request)).then(invocation -> {
            GiftCertificate giftCertificate = new GiftCertificate();
                    giftCertificate.setDuration(LocalDateTime.of(2022, 2, 2, 0, 0, 0).plusDays(request.getDuration()));
                    giftCertificate.setPrice(request.getPrice());
                    giftCertificate.setCertificateName(request.getGiftCertificateName());
                    giftCertificate.setDescription(request.getDescription());
                    return giftCertificate;
        });

        giftCertificateServiceImpl.updateById(id, request);

        ArgumentCaptor<GiftCertificate> entityCaptor = ArgumentCaptor.forClass(GiftCertificate.class);

        verify(giftCertificateRepository).save(entityCaptor.capture());

        GiftCertificate captured = entityCaptor.getValue();

        assertThat(captured).usingRecursiveComparison().isEqualTo(updateGiftCertificateMapper.toEntity(request));
    }

    private void setUpUserService(String username) {
        when(userDetailsService.getUserDetailsDtoByUsername(username)).then(invocation -> generateUserDetailsDto());
    }

    private void setUpTagService(List<TagRequest> tagRequests) {
        AtomicLong id = new AtomicLong(1L);
        when(tagService.create(tagRequests)).thenReturn(tagRequests.stream()
                .map(tagRequest -> new TagDto(id.getAndIncrement(), tagRequest.getTagName())).collect(Collectors.toList()));
    }

    private void setUpTagMapper(List<TagRequest> requests) {
        List<TagDto> tagDtos = generateTagDto(requests);
        when(tagMapper.toEntityList(tagDtos)).thenReturn(tagDtos.stream().map(tagRequest -> {
            Tag tag = new Tag();
            tag.setTagName(tagRequest.getTagName());
            return tag;
        }).collect(Collectors.toList()));
    }

    private List<TagDto> generateTagDto(List<TagRequest> requests) {
        AtomicLong id = new AtomicLong(1L);
        return requests.stream().map(request -> new TagDto(id.getAndIncrement(), request.getTagName())).collect(Collectors.toList());
    }

    private UserDetailsDto generateUserDetailsDto() {
        return UserDetailsDto.builder()
                .username("username@gmail.com")
                .dob(LocalDate.now())
                .giftCertificates(new ArrayList<>())
                .firstName("fName")
                .lastName("lName")
                .patronymic("patreName")
                .sendEmail(true)
                .phoneNumber("+999 999 999 999")
                .fullName("fname lname patreName")
                .build();
    }

    private UserDetails generateUserDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setId("username@gmail.com");
        userDetails.setDob(LocalDate.now());
        userDetails.setGiftCertificates(new ArrayList<>());
        userDetails.setFirstName("fName");
        userDetails.setLastName("lName");
        userDetails.setPatronymic("patreName");
        userDetails.setSendEmail(true);
        userDetails.setPhoneNumber("+999 999 999 999");
        userDetails.setFullName("fname lname patreName");
        return userDetails;
    }

    private void setUpUserDetailsMapper() {
        UserDetailsDto userDetailsDto = generateUserDetailsDto();
        when(userDetailsMapper.toEntity(userDetailsDto)).then(invocation -> {
            UserDetails userDetails = new UserDetails();
            userDetails.setId(userDetailsDto.getUsername());
            userDetails.setFirstName(userDetailsDto.getFirstName());
            userDetails.setLastName(userDetailsDto.getLastName());
            userDetails.setPatronymic(userDetailsDto.getPatronymic());
            userDetails.setFullName(userDetailsDto.getFullName());
            userDetails.setGiftCertificates(giftCertificateMapper.toEntityList(userDetailsDto.getGiftCertificates()));
            userDetails.setSendEmail(userDetailsDto.getSendEmail());
            userDetails.setDob(userDetailsDto.getDob());
            userDetails.setPhoneNumber(userDetailsDto.getPhoneNumber());
            return userDetails;
        });
    }

    private void setUpCreateGiftCertificateMapper(CreateGiftCertificateRequest request) {
        when(createGiftCertificateMapper.toEntity(request)).then(invocation -> {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setCertificateName(request.getCertificateName());
            giftCertificate.setCreateDate(LocalDateTime.of(2022, 1, 1, 0, 0, 0));
            giftCertificate.setDescription(request.getDescription());
            giftCertificate.setDuration(LocalDateTime.of(2022, 1, 1, 0, 0, 0).plusDays(request.getDuration()));
            giftCertificate.setPrice(request.getPrice());
            giftCertificate.setTags(request.getTags().stream()
                    .map(tagRequest -> {
                        Tag tag = new Tag();
                        tag.setTagName(tagRequest.getTagName());
                        return tag;
                    }).collect(Collectors.toList()));
            giftCertificate.setLastModifiedBy(generateUserDetails());
            giftCertificate.setCreatedBy(generateUserDetails());
            return giftCertificate;
        });
    }

    private static Stream<Arguments> generateName() {
        return IntStream.range(1, 51).mapToObj(value -> Arguments.of("Name #" + value));
    }

}