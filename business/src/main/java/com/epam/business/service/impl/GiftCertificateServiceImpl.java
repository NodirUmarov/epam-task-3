package com.epam.business.service.impl;

import com.epam.business.client.MailSenderClient;
import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.mapper.dto.GiftCertificateMapper;
import com.epam.business.mapper.dto.TagMapper;
import com.epam.business.mapper.dto.UserDetailsMapper;
import com.epam.business.mapper.request.CreateGiftCertificateMapper;
import com.epam.business.mapper.request.UpdateGiftCertificateMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.enums.GiftCertificateSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.SendMailClientRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.business.service.TagService;
import com.epam.business.service.UserDetailsService;
import com.epam.business.service.utils.BeanCopyUtils;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.user.UserDetails;
import com.epam.domain.repository.GiftCertificateRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final UserDetailsService userDetailsService;
    private final UserDetailsMapper userDetailsMapper;
    private final TagMapper tagMapper;
    private final TagService tagService;
    private final MailSenderClient mailSenderClient;

    @Override
    public GiftCertificateDto create(CreateGiftCertificateRequest request) throws EntityExistsException {
        log.info("Creating a gift certificate...");

        GiftCertificate toSave = createGiftCertificateMapper.toEntity(request);

        UserDetails createdBy = userDetailsMapper.toEntity(userDetailsService.getUserDetailsDtoByUsername(request.getCreatedBy()));

        if (giftCertificateRepository.existsByCertificateName(request.getCertificateName())) {
            throw new EntityExistsException();
        }

        List<TagDto> savedTags = tagService.create(request.getTags());

        toSave.setCreatedBy(createdBy);
        toSave.setTags(tagMapper.toEntityList(savedTags));

        setLastModifiedBy(request.getCreatedBy(), toSave);

        GiftCertificate giftCertificate = giftCertificateRepository.save(toSave);

        GiftCertificateDto giftCertificateDto = giftCertificateMapper.toDto(giftCertificate);

        log.info("Gift-Certificate created");

        return giftCertificateDto;
    }

    @Override
    public GiftCertificateDto getById(Long id) throws EntityIdNotFoundException {
        log.info("Searching for Gift-Certificate by id={}", id);

        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).orElseThrow(EntityIdNotFoundException::new);

        log.info("Gift-Certificate by id={} found in database", id);
        return giftCertificateMapper.toDto(giftCertificate);
    }

    @Override
    public GiftCertificateDto getByName(String name) throws EntityNameNotFoundException {
        log.info("Searching for Gift-Certificate by name=\"{}\"", name);

        GiftCertificate giftCertificate = giftCertificateRepository.findByCertificateName(name).orElseThrow(EntityIdNotFoundException::new);

        log.info("Gift-Certificate by name=\"{}\" found in database", name);
        return giftCertificateMapper.toDto(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType, GiftCertificateSortBy giftCertificateSortBy) {
        log.info("Searching for Gift-Certificates by tag=\"{}\"", tag);

        Sort sort = Sort.by(sortType.getDirection(), giftCertificateSortBy.getAttributeName());

        Pageable pageable = PageRequest.of(page, quantity, sort);

        Page<GiftCertificate> certificatePage = giftCertificateRepository.findByTags_TagName(tag, pageable);

        List<GiftCertificate> giftCertificates = certificatePage.toList();

        List<GiftCertificateDto> giftCertificateDtos = giftCertificateMapper.toDtoList(giftCertificates);

        log.info("{} object{} found in database", giftCertificateDtos.size(), giftCertificateDtos.size() > 1 ? "s" : "");

        return giftCertificateDtos;
    }

    @Transactional
    @Override
    public void deleteByName(String name) {
        log.info("Deleting Gift-Certificate with name=\"{}\"", name);

        if (!giftCertificateRepository.existsByCertificateName(name)) {
            log.warn("Object with name=\"{}\" already deleted or have not been created. Operation aborted", name);
            throw new EntityNameNotFoundException();
        }

        giftCertificateRepository.deleteByCertificateName(name);
        log.info("Gift-Certificate with name=\"{}\", successfully deleted", name);
    }

    @Override
    public GiftCertificateDto updateById(Long id, UpdateGiftCertificateRequest request) throws EntityIdNotFoundException {
        log.info("Updating Gift-Certificate by id={}", id);

        GiftCertificate updateTarget = giftCertificateRepository.findById(id).orElseThrow(EntityIdNotFoundException::new);

        GiftCertificate updateSource = updateGiftCertificateMapper.toEntity(request);

        BeanCopyUtils.copyNonNullProperties(updateSource, updateTarget);

        setLastModifiedBy(request.getUpdatedBy(), updateTarget);

        GiftCertificate giftCertificate;

        try {
            giftCertificate = giftCertificateRepository.save(updateTarget);
        } catch (ConstraintViolationException exception) {
            throw new EntityExistsException();
        }
        log.info("Gift-Certificate updated");
        return giftCertificateMapper.toDto(giftCertificate);
    }

    @Override
    public GiftCertificateDto changeSetOfTags(Long id, Set<TagRequest> tags) throws EntityIdNotFoundException {
        log.info("Changing the tags of gift-certificate with id={}", id);

        GiftCertificate giftCertificate = giftCertificateRepository.findById(id).orElseThrow(EntityIdNotFoundException::new);
        GiftCertificateDto dto = giftCertificateMapper.toDto(giftCertificateRepository.save(giftCertificate));

        log.info("Gift-certificate's set of tags has changed");
        return dto;
    }

    @Override
    public List<GiftCertificateDto> findAllByNames(List<String> giftCertificatesNames) {
        return giftCertificateMapper.toDtoList(giftCertificateRepository.findAllByCertificateName(giftCertificatesNames));
    }

    @Scheduled(fixedDelay = 60000)
    public void checkToUpdateCertificatesDuration() {
        log.info("Updating gift-certificates expiration status...");

        giftCertificateRepository.updateExpirationStatus().forEach(user ->
                sendMessage(SendMailClientRequest.builder().receiver(user)
                        .subject("Expired Gift-Certificate")
                        .text("One or more gift-certificates has expired. Check your account for details")
                        .build()));
        log.info("Gift-certificates expiration statuses have changed and have been reported via mail to users.");
    }

    @Transactional
    @Scheduled(cron = "0 9 13 * * *")
    public void checkToRemoveCertificateDuration() {
        log.info("Task remove");
        List<GiftCertificate> giftCertificatesExpired = giftCertificateRepository
                .findAllByDurationBefore(LocalDateTime.now());

        List<GiftCertificate> giftCertificatesAboutToExpire = giftCertificateRepository
                .findAllByDurationBefore(LocalDateTime.now().plusDays(1));
    }

    private void setLastModifiedBy(String username, GiftCertificate giftCertificate) {
        UserDetails details = userDetailsMapper.toEntity(userDetailsService.getUserDetailsDtoByUsername(username));

        giftCertificate.setLastModifiedBy(details);
        log.info("{} object set to certificate {}", details, giftCertificate);
    }

    private void sendMessage(SendMailClientRequest request) {
        try {
            mailSenderClient.sendMail(request);
        } catch (Exception e) {
            log.error("Message has not been sent");
            e.printStackTrace();
        }
    }
}
