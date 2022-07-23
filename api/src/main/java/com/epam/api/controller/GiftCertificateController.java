package com.epam.api.controller;

import com.epam.business.model.enums.GiftCertificateSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(produces = "application/json", consumes = "application/json")
@RequestMapping("/api/v1/gift-certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @GetMapping(params = {"name"})
    @ApiOperation(value = "Get By Name", notes = "This method retrieves single gift-certificate by its name")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getByName(name));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get By ID", notes = "This method retrieves single gift-certificate by its ID")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getById(id));
    }

    @GetMapping(params = {"tag"})
    @ApiOperation(value = "Get By Tag", notes = "This method retrieves all gift-certificates by single tag. Pagination and order can be configured.")
    public ResponseEntity<?> getByTag(@RequestParam String tag,
                                      @RequestParam(defaultValue = "5", required = false) Integer quantity,
                                      @RequestParam(defaultValue = "1", required = false) Integer page,
                                      @RequestParam(defaultValue = "NONE", required = false) SortType sortType,
                                      @RequestParam(defaultValue = "ID", required = false) GiftCertificateSortBy sortBy) {
        return ResponseEntity
                .ok(giftCertificateService
                        .getByTag(tag, quantity, page, sortType, sortBy));
    }

    @PostMapping
    @ApiOperation(value = "Create gift-certificate",
            notes = "This method creates gift-certificate with tags. If tag does not exists, it will be created automatically. " +
                    "Created gift-certificate will be returned as response.")
    public ResponseEntity<?> create(@RequestBody CreateGiftCertificateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(giftCertificateService
                        .create(request));
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Update gift-certificate", notes = "This method updates only changed fields.")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Validated UpdateGiftCertificateRequest request) {
        return ResponseEntity
                .accepted()
                .body(giftCertificateService
                        .updateById(id, request));
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Add tag",
            notes = "This method adds given tags to gift-certificate.")
    public ResponseEntity<?> addTagsToCertificate(@PathVariable Long id,
                                                  @RequestBody @Validated Set<TagRequest> tags) {
        return ResponseEntity.ok(giftCertificateService.changeSetOfTags(id, tags));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = " tag",
            notes = "This method adds given tags to gift-certificate.")
    public ResponseEntity<?> untagCertificate(@PathVariable Long id,
                                              @RequestBody @Validated Set<TagRequest> tags) {
        return ResponseEntity.ok(giftCertificateService.changeSetOfTags(id, tags));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by ID", notes = "This method deletes gift-certificate by its ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        giftCertificateService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}