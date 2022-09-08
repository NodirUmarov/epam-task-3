package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.domain.entity.certificate.GiftCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * This interface inherits {@link EntityMapper} and {@link DtoMapper}, and sets to mapper parameters
 * <p>
 * {@link GiftCertificate} to parameter T
 * {@link GiftCertificateDto} to parameter S
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
 * @see GiftCertificateDto
 */
@Named("GiftCertificateMapper")
@Mapper(config = ConfigMapper.class, uses = {UserDetailsMapper.class})
public interface GiftCertificateMapper extends EntityMapper<GiftCertificate, GiftCertificateDto>,
        DtoMapper<GiftCertificate, GiftCertificateDto> {
}