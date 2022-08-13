package com.epam.api.assembler;

import com.epam.api.controller.TagController;
import com.epam.api.model.hypermediaresource.TagResource;
import com.epam.business.model.dto.TagDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */
@Component
public class TagAssembler extends RepresentationModelAssemblerSupport<TagDto, TagResource> {

    public TagAssembler() {
        super(TagController.class, TagResource.class);
    }

    public List<TagResource> toModelSet(List<TagDto> entityList) {
        return entityList.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public TagResource toModel(TagDto entity) {
        TagResource tagResource = createModelWithId(entity.getId(), entity);
        tagResource.add(linkTo(methodOn(TagController.class).deleteById(entity.getId())).withRel("delete"));
        tagResource.setId(entity.getId());
        tagResource.setTagName(entity.getTagName());
        return tagResource;
    }
}
