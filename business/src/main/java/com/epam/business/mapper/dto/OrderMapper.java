package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.dto.OrderDto;
import com.epam.domain.entity.certificate.Order;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
@Mapper(config = ConfigMapper.class, uses = {UserDetailsMapper.class, GiftCertificateMapper.class})
public interface OrderMapper extends EntityMapper<Order, OrderDto>, DtoMapper<Order, OrderDto> {

    @Override
    @Mapping(source = "createdBy", target = "orderedBy", qualifiedByName = {"UserDetailsMapper", "toDtoWithoutSomeChild"})
    @Mapping(target = "lastModifiedBy", qualifiedByName = {"UserDetailsMapper", "toDtoWithoutSomeChild"})
    @Mapping(target = "orderedCertificates", qualifiedByName = {"GiftCertificateMapper", "toDtoWithoutSomeChild"})
    OrderDto toDto(Order entity);

    @Override
    @Mapping(source = "orderedBy", target = "createdBy", qualifiedByName = {"UserDetailsMapper", "toEntityWithoutSomeChild"})
    @Mapping(target = "lastModifiedBy", qualifiedByName = {"UserDetailsMapper", "toEntityWithoutSomeChild"})
    @Mapping(target = "orderedCertificates", qualifiedByName = {"GiftCertificateMapper", "toEntityListWithoutSomeChild"})
    Order toEntity(OrderDto dto);
}
