package com.epam.api.controller;

import com.epam.api.assembler.TagAssembler;
import com.epam.api.internationalization.MessageCode;
import com.epam.api.model.builder.ApiErrorModelBuilder;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.enums.TagSortBy;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(produces = "application/json", consumes = "application/json")
@RequestMapping("/api/v1/tag")
public class TagController {

    private final TagService tagService;
    private final TagAssembler tagAssembler;
    private final ApiErrorModelBuilder apiErrorModelBuilder;

    @GetMapping(params = {"amount"})
    public ResponseEntity<?> getMostUsed(@RequestParam Integer amount) {
        log.info("Getting most used tags");
        if (amount <= 0) {
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.PAGINATION_INVALID, HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(tagAssembler.toCollectionModel(tagService.getMostUsed(amount)));
    }

    @GetMapping(params = {"sortBy"})
    @ApiOperation(value = "Get tags", notes = "This method retrieves all tags in given range. Pagination and order can be configured.")
    public ResponseEntity<?> getTags(@RequestParam(defaultValue = "5", required = false) Integer quantity,
                                     @RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "NONE", required = false) SortType sortType,
                                     @RequestParam TagSortBy sortBy) {
        log.info("Getting all tags");
        if (quantity <= 0 || page < 0) {
            log.error("Invalid pagination details passed. page={}, quantity={}", page, quantity);
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.PAGINATION_INVALID, HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity
                .ok(tagAssembler.toModelSet(tagService.getAllTags(quantity, page, sortType, sortBy)));
    }

    @PostMapping
    @ApiOperation(value = "Create tags", notes = "This method creates all tags provided and returns them with their IDs as response")
    public ResponseEntity<?> create(@RequestBody List<TagRequest> tagRequest) {
        log.info("Creating tags");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagAssembler.toModelSet(tagService.create(tagRequest)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by ID", notes = "This method deletes single tag by its ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            log.info("Deleting tag by id={}", id);
            tagService.deleteById(id);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (EntityIdNotFoundException exception) {
            return apiErrorModelBuilder.buildResponseEntity(MessageCode.TAG_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}