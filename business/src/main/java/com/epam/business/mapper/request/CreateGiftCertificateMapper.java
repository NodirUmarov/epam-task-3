package com.epam.business.mapper.request;

import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.user.UserDetails;
import com.epam.domain.repository.UserDetailsRepository;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This interface inherits {@link EntityMapper} and sets to mapper parameters
 * <p>
 * {@link GiftCertificate} to parameter T
 * {@link CreateGiftCertificateRequest} to parameter S
 * <p>
 * All configs retrieved from {@link ConfigMapper}
 * The implementation of mapper will be created by mapstruct and will be passed to Spring as bean. Can be autowired.
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 * @see ConfigMapper
 * @see Mapper
 * @see EntityMapper
 * @see DtoMapper
 * @see GiftCertificate
 * @see CreateGiftCertificateRequest
 */
@Mapper(config = ConfigMapper.class)
public abstract class CreateGiftCertificateMapper implements EntityMapper<GiftCertificate, CreateGiftCertificateRequest> {

    @Autowired
    protected UserDetailsRepository userDetailsRepository;

    /**
     * This method is using internally by mapper, to convert duration in days to instance of {@link LocalDateTime}
     *
     * @param duration
     * @return specified date from current time plus days passed as argument
     */
    protected LocalDateTime toLocalDateTime(Long duration) {
        return LocalDateTime.now().plusDays(duration);
    }

    protected UserDetails toUserDetails(String createdBy) {
        return userDetailsRepository.findById(createdBy).orElseThrow(EntityNameNotFoundException::new);
    }
}
