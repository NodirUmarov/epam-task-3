package com.epam.business.mapper.dtoMapper;

import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.data.model.entity.GiftCertificateEntity;
import org.mapstruct.Mapper;

/**
 * This interface inherits {@link EntityMapper} and {@link DtoMapper}, and sets to mapper parameters
 *
 * {@link GiftCertificateEntity} to parameter T
 * {@link GiftCertificateDto} to parameter S
 *
 * All configs retrieved from {@link ConfigMapper}
 * The implementation of mapper will be created by mapstruct and will be passed to Spring as bean. Can be autowired.
 *
 * @see ConfigMapper
 * @see Mapper
 * @see EntityMapper
 * @see DtoMapper
 * @see GiftCertificateEntity
 * @see GiftCertificateDto
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface GiftCertificateMapper extends EntityMapper<GiftCertificateEntity, GiftCertificateDto>,
        DtoMapper<GiftCertificateEntity, GiftCertificateDto> {
}