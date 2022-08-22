package com.epam.business.service.impl;

import com.epam.business.mapper.dto.OrderMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.dto.OrderDto;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.business.model.enums.OrderSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.MakeOrderRequest;
import com.epam.business.service.GiftCertificateService;
import com.epam.business.service.OrderService;
import com.epam.business.service.UserDetailsService;
import com.epam.domain.entity.certificate.Order;
import com.epam.domain.repository.OrderRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserDetailsService userDetailsService;
    private final GiftCertificateService giftCertificateService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto create(String username, MakeOrderRequest request) {
        log.info("Making an order for user with username=\"{}\"", username);
        List<GiftCertificateDto> giftCertificateDtos = giftCertificateService.findAllByNames(request.getGiftCertificatesNames());
        userDetailsService.addCertificate(request.getDedicatedTo(), giftCertificateDtos);
        UserDetailsDto orderedByDto = userDetailsService.getUserDetailsDtoByUsername(username);

        BigDecimal totalPrice = giftCertificateDtos.stream().map(GiftCertificateDto::getPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        OrderDto orderDto = OrderDto.builder().orderedCertificates(giftCertificateDtos)
                .orderedBy(orderedByDto)
                .lastModifiedBy(orderedByDto)
                .totalPrice(totalPrice)
                .build();

        log.info("Order successfully made");

        Order order = orderMapper.toEntity(orderDto);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getAllOrders(String username, Integer quantity, Integer page, OrderSortBy orderSortBy, SortType sortType) {
        log.info("Getting all orders of user with username=\"{}\"", username);

        Sort sort = Sort.by(sortType.getDirection(), orderSortBy.getAttributeName());
        Pageable pageable = PageRequest.of(page, quantity, sort);
        Page<Order> orderPage = orderRepository.findAllByCreatedBy_Id(username, pageable);
        List<Order> orders = orderPage.toList();

        log.info("{} orders found of user with username=\"{}\"", orders.size(), username);
        return orderMapper.toDtoList(orders);
    }


}
