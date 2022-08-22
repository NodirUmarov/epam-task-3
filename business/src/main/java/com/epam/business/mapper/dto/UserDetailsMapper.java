package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.DtoNestedObjectMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.mapper.config.EntityNestedObjectMapper;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.domain.entity.user.UserDetails;
import java.util.List;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
@Named("UserDetailsMapper")
@Mapper(config = ConfigMapper.class, uses = {GiftCertificateMapper.class})
public interface UserDetailsMapper extends EntityMapper<UserDetails, UserDetailsDto>, DtoMapper<UserDetails, UserDetailsDto>,
        DtoNestedObjectMapper<UserDetails, UserDetailsDto>, EntityNestedObjectMapper<UserDetails, UserDetailsDto> {

    @Override
    @Named("toEntity")
    @Mapping(target = "id", source = "username")
    UserDetails toEntity(UserDetailsDto dto);

    @Override
    @Named("toDto")
    @Mapping(target = "username", source = "id")
    UserDetailsDto toDto(UserDetails entity);

    @Override
    @Named("toDtoWithoutSomeChild")
    @Mapping(target = "username", source = "id")
    @Mapping(target = "giftCertificates", ignore = true)
    UserDetailsDto toDtoWithoutSomeChild(UserDetails object);

    @Override
    @Named("toDtoListWithoutSomeChild")
    @IterableMapping(qualifiedByName = "toDtoWithoutSomeChild")
    List<UserDetailsDto> toDtoListWithoutSomeChild(List<UserDetails> objects);

    @Override
    @Named("toEntityWithoutSomeChild")
    @Mapping(target = "id", source = "username")
    @Mapping(target = "giftCertificates", ignore = true)
    UserDetails toEntityWithoutSomeChild(UserDetailsDto object);

    @Override
    @Named("toEntityListWithoutSomeChild")
    @IterableMapping(qualifiedByName = "toEntityWithoutSomeChild")
    List<UserDetails> toEntityListWithoutSomeChild(List<UserDetailsDto> objects);
}