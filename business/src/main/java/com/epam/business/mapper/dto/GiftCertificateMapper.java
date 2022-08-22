package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.DtoNestedObjectMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.mapper.config.EntityNestedObjectMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.user.UserDetails;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
        DtoMapper<GiftCertificate, GiftCertificateDto>, DtoNestedObjectMapper<GiftCertificate, GiftCertificateDto>, EntityNestedObjectMapper<GiftCertificate, GiftCertificateDto> {

    @Override
    @Named("toDto")
    @Mapping(target = "createdBy", qualifiedByName = {"UserDetailsMapper", "toDtoWithoutSomeChild"})
    @Mapping(target = "lastModifiedBy", qualifiedByName = {"UserDetailsMapper", "toDtoWithoutSomeChild"})
    @Mapping(target = "giftToUsers", qualifiedByName = {"UserDetailsMapper", "toDtoListWithoutSomeChild"})
    GiftCertificateDto toDto(GiftCertificate entity);

    @Override
    @Named("toDtoWithoutSomeChild")
    GiftCertificateDto toDtoWithoutSomeChild(GiftCertificate object);

    @Override
    @Named("toDtoListWithoutSomeChild")
    List<GiftCertificateDto> toDtoListWithoutSomeChild(List<GiftCertificate> objects);

    @Override
    @Named("toEntityWithoutSomeChild")
    GiftCertificate toEntityWithoutSomeChild(GiftCertificateDto object);

    @Override
    @Named("toEntityListWithoutSomeChild")
    List<GiftCertificate> toEntityListWithoutSomeChild(List<GiftCertificateDto> objects);
}