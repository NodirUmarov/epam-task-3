package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.dto.OrderDto;
import com.epam.domain.entity.certificate.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
@Mapper(config = ConfigMapper.class, uses = {UserDetailsMapper.class, GiftCertificateMapper.class})
public interface OrderMapper extends EntityMapper<Order, OrderDto>, DtoMapper<Order, OrderDto> {

    @Override
    @Mapping(source = "createdBy", target = "orderedBy")
    OrderDto toDto(Order entity);

    @Override
    @Mapping(source = "orderedBy", target = "createdBy")
    Order toEntity(OrderDto dto);
}
