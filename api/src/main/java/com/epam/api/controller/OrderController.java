package com.epam.api.controller;

import com.epam.api.assembler.OrderAssembler;
import com.epam.api.model.hypermediaresource.OrderResource;
import com.epam.business.model.enums.OrderSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.MakeOrderRequest;
import com.epam.business.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderAssembler orderAssembler;

    @PostMapping
    public ResponseEntity<OrderResource> create(@RequestHeader String username,
                                                @RequestBody MakeOrderRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderAssembler.toModel(orderService.create(username, request)));
    }

    @GetMapping
    public ResponseEntity<List<OrderResource>> getAll(@RequestHeader String username,
                                                      @RequestParam(required = false, defaultValue = "5") Integer quantity,
                                                      @RequestParam Integer page,
                                                      @RequestParam(required = false, defaultValue = "NONE") SortType sortType,
                                                      @RequestParam(required = false, defaultValue = "ID") OrderSortBy orderSortBy) {
        return ResponseEntity.ok(orderAssembler.toModelList(orderService.getAllOrders(username, quantity, page, orderSortBy, sortType)));
    }

}
