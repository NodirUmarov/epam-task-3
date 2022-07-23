package com.epam.api.controller;

import com.epam.business.model.enums.SortType;
import com.epam.business.model.enums.TagSortBy;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@Api(produces = "application/json", consumes = "application/json")
@RequestMapping("/api/v1/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping
    @ApiOperation(value = "Get tags", notes = "This method retrieves all tags in given range. Pagination and order can be configured.")
    public ResponseEntity<?> getTags(@RequestParam(defaultValue = "5", required = false) Integer quantity,
                                     @RequestParam(defaultValue = "1", required = false) Integer page,
                                     @RequestParam(defaultValue = "NONE", required = false) SortType sortType,
                                     @RequestParam(defaultValue = "ID", required = false) TagSortBy sortBy) {
        return ResponseEntity
                .ok(tagService.getAllTags(quantity, page, sortType, sortBy));
    }

    @PostMapping
    @ApiOperation(value = "Create tags", notes = "This method creates all tags provided and returns them with their IDs as response")
    public ResponseEntity<?> create(@RequestBody Set<TagRequest> tagRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.create(tagRequest));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by ID", notes = "This method deletes single tag by its ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}