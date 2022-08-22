package com.epam.api.controller;

import com.epam.api.assembler.UserDetailsAssembler;
import com.epam.api.internationalization.MessageCode;
import com.epam.api.model.builder.ApiErrorModelBuilder;
import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.model.request.CreateUserDetailsRequest;
import com.epam.business.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-details")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;
    private final UserDetailsAssembler userDetailsAssembler;
    private final ApiErrorModelBuilder apiErrorModelBuilder;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserDetailsRequest request) {
        try {
            log.info("Creating user by request={}", request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userDetailsAssembler.toModel(userDetailsService.create(request)));
        } catch (EntityExistsException exception) {
            log.error("User with username=\"{}\" already exists", request.getUsername());
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.USER_DUPLICATE_NAME, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
        try {
            log.info("Getting user by username=\"{}\"", username);
            return ResponseEntity.ok(userDetailsAssembler.toModel(userDetailsService.getUserDetailsDtoByUsername(username)));
        } catch (EntityNameNotFoundException exception) {
            log.error("User not found by username=\"{}\"", username);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}
