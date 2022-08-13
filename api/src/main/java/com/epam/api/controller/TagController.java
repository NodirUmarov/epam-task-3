package com.epam.api.controller;

import com.epam.api.assembler.TagAssembler;
import com.epam.api.model.hypermediaresource.TagResource;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.enums.TagSortBy;
import com.epam.business.model.request.TagRequest;
import com.epam.business.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collection;
import java.util.List;
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
    private final TagAssembler tagAssembler;

    @GetMapping(params = {"quantity"})
    public ResponseEntity<Collection<TagResource>> getMostUsed(@RequestParam Integer quantity,
                                                               @RequestParam(defaultValue = "0", required = false) Integer page) {
        return ResponseEntity.ok(tagAssembler.toModelSet(tagService.getMostUsed(quantity, page)));
    }

    @GetMapping(params = {"sortBy"})
    @ApiOperation(value = "Get tags", notes = "This method retrieves all tags in given range. Pagination and order can be configured.")
    public ResponseEntity<Collection<TagResource>> getTags(@RequestParam(defaultValue = "5", required = false) Integer quantity,
                                                           @RequestParam(defaultValue = "0", required = false) Integer page,
                                                           @RequestParam(defaultValue = "NONE", required = false) SortType sortType,
                                                           @RequestParam TagSortBy sortBy) {
        return ResponseEntity
                .ok(tagAssembler.toModelSet(tagService.getAllTags(quantity, page, sortType, sortBy)));
    }

    @PostMapping
    @ApiOperation(value = "Create tags", notes = "This method creates all tags provided and returns them with their IDs as response")
    public ResponseEntity<Collection<TagResource>> create(@RequestBody List<TagRequest> tagRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagAssembler.toModelSet(tagService.create(tagRequest)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete by ID", notes = "This method deletes single tag by its ID")
    public ResponseEntity<TagResource> deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}