package com.epam.business.service;

import com.epam.business.model.dto.OrderDto;
import com.epam.business.model.enums.OrderSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.MakeOrderRequest;
import java.util.List;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
public interface OrderService {

    OrderDto create(String orderedBy, MakeOrderRequest request);
    List<OrderDto> getAllOrders(String username, Integer quantity, Integer page, OrderSortBy orderSortBy, SortType sortType);

}
