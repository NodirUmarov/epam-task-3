package com.epam.api.assembler;

import com.epam.api.controller.GiftCertificateController;
import com.epam.api.model.hypermediaresource.GiftCertificateResource;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.request.TagRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */
@Component
public class GiftCertificatesAssembler extends RepresentationModelAssemblerSupport<GiftCertificateDto, GiftCertificateResource> {

    private final TagAssembler tagAssembler;
    private final UserDetailsAssembler userDetailsAssembler;

    @Autowired
    public GiftCertificatesAssembler(TagAssembler tagAssembler, @Lazy UserDetailsAssembler userDetailsAssembler) {
        super(GiftCertificateController.class, GiftCertificateResource.class);
        this.tagAssembler = tagAssembler;
        this.userDetailsAssembler = userDetailsAssembler;
    }

    public List<GiftCertificateResource> toModelList(List<GiftCertificateDto> giftCertificateDtos) {
        return giftCertificateDtos.stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateResource toModel(GiftCertificateDto entity) {
        GiftCertificateResource resource = createModelWithId(entity.getId(), entity);
        resource.add(linkTo(methodOn(GiftCertificateController.class).deleteById(entity.getCertificateName())).withRel("delete"));
        resource.add(linkTo(methodOn(GiftCertificateController.class).getById(entity.getId())).withRel("get"));


        TagRequest tagRequest = new TagRequest();
        Optional<TagDto> tagDto = entity.getTags().stream().findAny();
        if (tagDto.isPresent()) {
            tagRequest.setTagName(tagDto.get().getTagName());

            resource.add(linkTo(methodOn(GiftCertificateController.class).untagCertificate(entity.getId(), Set.of(tagRequest))).withRel("untag"));
        }

        resource.setId(entity.getId());
        resource.setCertificateName(entity.getCertificateName());
        resource.setCreateDate(entity.getCreateDate());
        resource.setCreatedBy(userDetailsAssembler.toModel(entity.getCreatedBy()));
        resource.setDescription(entity.getDescription());
        resource.setDuration(entity.getDuration());
        if (entity.getGiftToUser() != null) {
            resource.setGiftToUser(entity.getGiftToUser().stream().map(userDetailsAssembler::toModel).collect(Collectors.toList()));
        }
        resource.setLastModifiedBy(userDetailsAssembler.toModel(entity.getLastModifiedBy()));
        resource.setLastUpdateDate(entity.getLastModifiedDate());
        resource.setPrice(entity.getPrice());
        resource.setTags(entity.getTags().stream().map(tagAssembler::toModel).collect(Collectors.toSet()));
        return resource;
    }
}
