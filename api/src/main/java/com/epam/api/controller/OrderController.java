package com.epam.api.controller;

import com.epam.api.assembler.OrderAssembler;
import com.epam.api.internationalization.MessageCode;
import com.epam.api.model.builder.ApiErrorModelBuilder;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.model.enums.OrderSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.MakeOrderRequest;
import com.epam.business.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderAssembler orderAssembler;
    private final ApiErrorModelBuilder apiErrorModelBuilder;

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader String username,
                                    @RequestBody MakeOrderRequest request) {
        try {
            log.info("Making order for user=\"{}\", request={}", username, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(orderAssembler.toModel(orderService.create(username, request)));
        } catch (EntityNameNotFoundException exception) {
            log.error("One of the required data not found in database. Username=\"{}\", request={}", username, request);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.REQUEST_BODY_MISSING, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader String username,
                                    @RequestParam(required = false, defaultValue = "5") Integer quantity,
                                    @RequestParam Integer page,
                                    @RequestParam(required = false, defaultValue = "NONE") SortType sortType,
                                    @RequestParam(required = false, defaultValue = "ID") OrderSortBy orderSortBy) {
        try {
            log.info("Getting all orders of user=\"{}\"", username);

            if (page < 0 || quantity <= 0) {
                log.error("Invalid pagination details passed. page={}, quantity={}", page, quantity);
                return apiErrorModelBuilder.buildResponseEntity(MessageCode.PAGINATION_INVALID, HttpStatus.NOT_ACCEPTABLE);
            }

            return ResponseEntity.ok(orderAssembler.toCollectionModel(orderService.getAllOrders(username, quantity, page, orderSortBy, sortType)));
        } catch (EntityNameNotFoundException exception) {
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
