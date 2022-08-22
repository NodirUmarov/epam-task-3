package com.epam.business.service.impl;

import com.epam.business.client.MailSenderClient;
import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.mapper.dto.GiftCertificateMapper;
import com.epam.business.mapper.dto.UserDetailsMapper;
import com.epam.business.mapper.request.CreateUserDetailsMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.business.model.request.CreateUserDetailsRequest;
import com.epam.business.model.request.SendMailClientRequest;
import com.epam.business.service.UserDetailsService;
import com.epam.domain.entity.user.UserDetails;
import com.epam.domain.repository.UserDetailsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserDetailsMapper userDetailsMapper;
    private final CreateUserDetailsMapper createDetailsUserMapper;
    private final GiftCertificateMapper giftCertificateMapper;
    private final MailSenderClient mailSenderClient;

    @Override
    public UserDetailsDto create(CreateUserDetailsRequest request) {
        if (userDetailsRepository.existsById(request.getUsername())) {
            log.warn("User with username '{}' exists in database, operation aborted.", request.getUsername());
            throw new EntityExistsException();
        }

        if (Boolean.FALSE.equals(request.getSendEmail())) {
            log.warn("User disabled email sending.");
        }

        UserDetails userDetails = createDetailsUserMapper.toEntity(request);

        log.info("Entity created");

        sendMessage(SendMailClientRequest.builder()
                .receiver(userDetails.getId())
                .subject("Account is activated")
                .text(String.format("Hi %s, welcome to gift-certificates service. " +
                        "Your account successfully activated.", userDetails.getFirstName())).build());

        return userDetailsMapper.toDto(userDetailsRepository.save(userDetails));
    }

    @Override
    public UserDetailsDto getUserDetailsDtoByUsername(String username) {

        UserDetails userDetails = getUserDetailsByUsername(username);

        return userDetailsMapper.toDto(userDetails);
    }

    @Override
    public UserDetailsDto addCertificate(String username, List<GiftCertificateDto> giftCertificates) {
        log.info("Adding {} gift-certificates to user with username=\"{}\"", giftCertificates.size(), username);
        UserDetails userDetails = userDetailsRepository.findById(username)
                .map(details -> {
                    details.getGiftCertificates().addAll(giftCertificateMapper.toEntityList(giftCertificates));
                    return userDetailsRepository.save(details);
                }).orElseThrow(() -> {
                    log.warn("User with username=\"{}\" not found in database", username);
                    return new EntityNameNotFoundException();
                });
        log.info("Gift-certificates added to user with username=\"{}\"", userDetails.getId());

        if (Boolean.TRUE.equals(userDetails.getSendEmail())) {
            sendMessage(SendMailClientRequest.builder()
                    .receiver(userDetails.getId())
                    .subject("Gift received!")
                    .text(String.format("You got new %d certificate%s%n", giftCertificates.size(), giftCertificates.size() > 1 ? "s" : ""))
                    .build());
        }
        return userDetailsMapper.toDto(userDetails);
    }

    private UserDetails getUserDetailsByUsername(String username) {
        if (username.isBlank()) {
            log.warn("Username is blank. Operation aborted.");
            throw new IllegalArgumentException();
        }

        UserDetails details = userDetailsRepository.findById(username).orElseThrow(() -> {
            log.warn("User with username=\"{}\" not found in database", username);
            return new EntityNameNotFoundException();
        });
        log.info("User with username '{}' found in database", username);
        return details;
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
