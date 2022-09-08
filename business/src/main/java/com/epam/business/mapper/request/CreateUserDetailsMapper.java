package com.epam.business.mapper.request;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.CreateUserDetailsRequest;
import com.epam.domain.entity.user.UserDetails;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface CreateUserDetailsMapper extends EntityMapper<UserDetails, CreateUserDetailsRequest> {

    @Override
    @Mapping(target = "id", source = "username")
    UserDetails toEntity(CreateUserDetailsRequest request);
}