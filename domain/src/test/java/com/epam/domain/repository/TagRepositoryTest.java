package com.epam.domain.repository;

import com.epam.domain.config.DomainConfig;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.certificate.Tag;
import com.epam.domain.repository.provider.GiftCertificateProvider;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // IDE thinks there is no bean of the repository
@ContextConfiguration(classes = DomainConfig.class)
class TagRepositoryTest {

    private final TagRepository tagRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public TagRepositoryTest(TagRepository tagRepository, UserDetailsRepository userDetailsRepository, GiftCertificateRepository giftCertificateRepository) {
        this.tagRepository = tagRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @DisplayName("Should save Tag then update it")
    @ParameterizedTest
    @MethodSource("tagProvider")
    void shouldSaveAndUpdate(Tag tag) {
        Tag saved = tagRepository.save(tag);

        Assertions.assertThat(tag).isNotNull();
        Assertions.assertThat(tag.getId()).isNotNull();

        Optional<Tag> optionalEntity = tagRepository.findByTagName(tag.getTagName());

        Assertions.assertThat(optionalEntity).isPresent();
        Assertions.assertThat(optionalEntity.get()).usingRecursiveComparison().isEqualTo(saved);

        Tag entity = optionalEntity.get();
        String oldName = entity.getTagName();
        tag.setTagName(tag.getTagName() + " (updated)");

        tagRepository.save(entity);

        Assertions.assertThat(entity.getTagName()).contains(oldName);
        Assertions.assertThat(entity.getTagName()).isNotEqualTo(oldName);
    }

    @DisplayName("Should not save and throws InvalidDataAccessApiUsageException")
    @ParameterizedTest
    @NullSource
    void shouldNotSaveAndThrowInvalidDataAccessApiUsageException(Tag tag) {
        Assertions.assertThatThrownBy(() -> tagRepository.save(tag)).isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

    @DisplayName("Should not save and throws DataIntegrityViolationException")
    @ParameterizedTest
    @MethodSource("tagProvider")
    void shouldNotSaveAndThrowDataIntegrityViolationException(Tag tag) {
        tagRepository.save(tag);

        Tag sameTag = new Tag();
        sameTag.setTagName(tag.getTagName());

        Assertions.assertThatThrownBy(() -> tagRepository.save(sameTag)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("Should find all or save if not exists")
    @ParameterizedTest
    @MethodSource("tagListProviderWithDuplicates")
    void shouldFindAllOrSave(List<Tag> tags) {
        Set<Tag> tagSet = new HashSet<>(tagRepository.findAllOrSaveIfNotExists(tags));

        Assertions.assertThat(tags.size()).isNotEqualTo(tagSet.size());

    }

    @DisplayName("Should gift-certificate and find most used tags and retrieve")
    @ParameterizedTest
    @ArgumentsSource(GiftCertificateProvider.class)
    void shouldSaveGiftCertificateAndFindMostUsedTags(GiftCertificate giftCertificate) {
        userDetailsRepository.save(giftCertificate.getCreatedBy());
        tagRepository.findAllOrSaveIfNotExists(giftCertificate.getTags());
        giftCertificateRepository.save(giftCertificate);

        Pageable pageable = PageRequest.of(0, 50);

        Assertions.assertThat(tagRepository.findMostUsed(pageable).getSize()).isPositive();
    }

    private static Stream<Arguments> tagListProviderWithDuplicates() {
        Random random = new Random();

        return LongStream.range(1, 6).mapToObj(iteration ->
                Arguments.of(LongStream.range(1, 51).mapToObj(value -> {
                    Tag tag = new Tag();
                    tag.setTagName("Tag #" + random.nextInt(30));
                    return tag;
                }).collect(Collectors.toList())));
    }

    private static Stream<Arguments> tagProvider() {
        return LongStream.range(1, 51).mapToObj(value -> {
            Tag tag = new Tag();
            tag.setTagName("Tag #" + value);
            return Arguments.of(tag);
        });
    }

}