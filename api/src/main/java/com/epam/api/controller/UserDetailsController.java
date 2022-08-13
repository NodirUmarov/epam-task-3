package com.epam.api.controller;

import com.epam.api.assembler.UserDetailsAssembler;
import com.epam.api.model.hypermediaresource.UserDetailsResource;
import com.epam.business.model.request.CreateUserDetailsRequest;
import com.epam.business.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-details")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;
    private final UserDetailsAssembler userDetailsAssembler;

    @PostMapping
    public ResponseEntity<UserDetailsResource> create(@RequestBody CreateUserDetailsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDetailsAssembler.toModel(userDetailsService.create(request)));
    }

    @GetMapping
    public ResponseEntity<UserDetailsResource> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userDetailsAssembler.toModel(userDetailsService.getUserDetailsDtoByUsername(username)));
    }
}
