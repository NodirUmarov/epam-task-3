package com.epam.api.assembler;

import com.epam.api.controller.UserDetailsController;
import com.epam.api.model.hypermediaresource.UserDetailsResource;
import com.epam.business.model.dto.UserDetailsDto;
import java.util.stream.Collectors;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */
@Component
public class UserDetailsAssembler extends RepresentationModelAssemblerSupport<UserDetailsDto, UserDetailsResource> {

    private final GiftCertificatesAssembler giftCertificatesAssembler;

    public UserDetailsAssembler(GiftCertificatesAssembler giftCertificatesAssembler) {
        super(UserDetailsController.class, UserDetailsResource.class);
        this.giftCertificatesAssembler = giftCertificatesAssembler;
    }

    @Override
    public UserDetailsResource toModel(UserDetailsDto entity) {
        UserDetailsResource userDetailsResource = new UserDetailsResource();
        userDetailsResource.add((linkTo(methodOn(UserDetailsController.class).getUserByUsername(entity.getUsername())).withRel("self")));

        userDetailsResource.setUsername(entity.getUsername());
        userDetailsResource.setFirstName(entity.getFirstName());
        userDetailsResource.setLastName(entity.getLastName());
        userDetailsResource.setPatronymic(entity.getPatronymic());
        userDetailsResource.setDob(entity.getDob());
        userDetailsResource.setFullName(entity.getFullName());
        userDetailsResource.setPhoneNumber(entity.getPhoneNumber());
        if (entity.getGiftCertificates() != null) {
            userDetailsResource.setGiftCertificates(entity.getGiftCertificates().stream()
                    .map(giftCertificatesAssembler::toModel).collect(Collectors.toSet()));
        }
        userDetailsResource.setSendEmail(entity.getSendEmail());
        return userDetailsResource;
    }
}
