package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.domain.entity.user.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface UserDetailsMapper extends EntityMapper<UserDetails, UserDetailsDto>,
        DtoMapper<UserDetails, UserDetailsDto> {

    @Override
    @Mapping(target = "id", source = "username")
    UserDetails toEntity(UserDetailsDto dto);

    @Override
    @Mapping(target = "username", source = "id")
    UserDetailsDto toDto(UserDetails entity);
}