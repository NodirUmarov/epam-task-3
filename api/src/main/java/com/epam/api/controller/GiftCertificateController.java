package com.epam.api.controller;

import com.epam.api.assembler.GiftCertificatesAssembler;
import com.epam.api.internationalization.MessageCode;
import com.epam.api.model.builder.ApiErrorModelBuilder;
import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.model.enums.GiftCertificateSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.business.service.GiftCertificateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(produces = "application/json", consumes = "application/json")
@RequestMapping("/api/v1/gift-certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificatesAssembler giftCertificatesAssembler;
    private final ApiErrorModelBuilder apiErrorModelBuilder;

    @GetMapping(params = {"name"})
    @ApiOperation(value = "Get By Name", notes = "This method retrieves single gift-certificate by its name")
    public ResponseEntity<?> getByName(@RequestParam String name) {
        try {
            log.info("Get Gift-Certificate by name=\"{}\"", name);
            return ResponseEntity.ok(giftCertificatesAssembler.toModel(giftCertificateService.getByName(name)));
        } catch (EntityNameNotFoundException exception) {
            log.error("Gift-certificate by name=\"{}\" not found", name);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get By ID", notes = "This method retrieves single gift-certificate by its ID")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            log.info("Get Gift-certificate by id={}", id);
            return ResponseEntity.ok(giftCertificatesAssembler.toModel(giftCertificateService.getById(id)));
        } catch (EntityIdNotFoundException exception) {
            log.error("Gift-certificate by id={} not found", id);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = {"tag"})
    @ApiOperation(value = "Get By Tag", notes = "This method retrieves all gift-certificates by single tag. Pagination and order can be configured.")
    public ResponseEntity<?> getByTag(@RequestParam String tag,
                                      @RequestParam(defaultValue = "5", required = false) Integer quantity,
                                      @RequestParam(defaultValue = "1", required = false) Integer page,
                                      @RequestParam(defaultValue = "NONE", required = false) SortType sortType,
                                      @RequestParam(defaultValue = "ID", required = false) GiftCertificateSortBy sortBy) {

        log.info("Get Gift-certificate by tag=\"{}\"", tag);
        if (quantity <= 0 || page < 0) {
            log.error("Invalid pagination details passed. page={}, quantity={}", page, quantity);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.PAGINATION_INVALID, HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(giftCertificatesAssembler.toCollectionModel(giftCertificateService.getByTag(tag, quantity, page, sortType, sortBy)));
    }

    @PostMapping
    @ApiOperation(value = "Create gift-certificate",
            notes = "This method creates gift-certificate with tags. If tag does not exists, it will be created automatically. " +
                    "Created gift-certificate will be returned as response.")
    public ResponseEntity<?> create(@RequestBody CreateGiftCertificateRequest request) {
        try {
            log.info("Create Gift-certificate by request={}", request);
            return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificatesAssembler.toModel(giftCertificateService.create(request)));
        } catch (EntityExistsException exception) {
            log.error("Gift-certificate with name=\"{}\" already exists", request.getCertificateName());
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NAME_INVALID, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Update gift-certificate", notes = "This method updates only changed fields.")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody @Validated UpdateGiftCertificateRequest request) {
        try {
            log.info("Updating Gift-certificate with id={} and by request={}", id, request);
            return ResponseEntity.accepted().body(giftCertificatesAssembler.toModel(giftCertificateService.updateById(id, request)));
        } catch (EntityIdNotFoundException exception) {
            log.error("Updating failed, Gift-certificate by id={} not found", id);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } catch (EntityExistsException exception) {
            log.error("Updating failed, Gift-certificate with name=\"{}\" already exists", request.getCertificateName());
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NAME_INVALID, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Add tag",
            notes = "This method adds given tags to gift-certificate.")
    public ResponseEntity<?> addTagsToCertificate(@PathVariable Long id,
                                                  @RequestBody @Validated List<TagRequest> tags) {
        try {
            log.info("Adding tags to Gift-certificate with id={}", id);
            return ResponseEntity.ok(giftCertificatesAssembler.toModel(giftCertificateService.addTagsToCertificate(id, tags)));
        } catch (EntityIdNotFoundException exception) {
            log.error("Gift-certificate not found by id={}", id);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = " tag",
            notes = "This method adds given tags to gift-certificate.")
    public ResponseEntity<?> untagCertificate(@PathVariable Long id,
                                              @RequestBody @Validated List<TagRequest> tags) {
        try {
            log.info("Removing tags from Gift-certificate with id={}", id);
            return ResponseEntity.ok(giftCertificatesAssembler.toModel(giftCertificateService.removeTagsFromCertificate(id, tags)));
        } catch (EntityIdNotFoundException exception) {
            log.error("Gift-certificate not found by id={}", id);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    @ApiOperation(value = "Delete by gift-certificate name", notes = "This method deletes gift-certificate by its unique full name")
    public ResponseEntity<?> deleteByName(@RequestParam("name") String giftCertificateName) {
        try {
            log.info("Deleting Gift-certificate by name=\"{}\"", giftCertificateName);
            giftCertificateService.deleteByName(giftCertificateName);
            return ResponseEntity.noContent().build();
        } catch (EntityNameNotFoundException exception) {
            log.error("Nothing found to delete by name=\"{}\"", giftCertificateName);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.CERTIFICATE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }
}