package com.epam.api.assembler;

import com.epam.api.controller.OrderController;
import com.epam.api.model.hypermediaresource.OrderResource;
import com.epam.business.model.dto.OrderDto;
import com.epam.business.model.enums.OrderSortBy;
import com.epam.business.model.enums.SortType;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */
@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<OrderDto, OrderResource> {

    private final UserDetailsAssembler userDetailsAssembler;
    private final GiftCertificatesAssembler giftCertificatesAssembler;

    public OrderAssembler(UserDetailsAssembler userDetailsAssembler, GiftCertificatesAssembler giftCertificatesAssembler) {
        super(OrderController.class, OrderResource.class);
        this.userDetailsAssembler = userDetailsAssembler;
        this.giftCertificatesAssembler = giftCertificatesAssembler;
    }

    public List<OrderResource> toModelList(List<OrderDto> orderDtos) {
        return orderDtos.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public OrderResource toModel(OrderDto entity) {
        OrderResource orderResource = createModelWithId(entity.getId(), entity);
        orderResource.add(linkTo(methodOn(OrderController.class).getAll(entity.getOrderedBy().getUsername(), entity.getId().intValue(), 0, SortType.NONE, OrderSortBy.ID)).withRel("getAll"));

        orderResource.setId(entity.getId());
        orderResource.setLastModifiedBy(userDetailsAssembler.toModel(entity.getLastModifiedBy()));
        orderResource.setCreatedDate(entity.getCreatedDate());
        orderResource.setOrderedBy(userDetailsAssembler.toModel(entity.getOrderedBy()));
        orderResource.setLastModifiedDate(entity.getLastModifiedDate());
        orderResource.setOrderedCertificates(entity.getOrderedCertificates().stream()
                .map(giftCertificatesAssembler::toModel).collect(Collectors.toSet()));
        orderResource.setTotalPrice(entity.getTotalPrice());
        return orderResource;
    }
}
