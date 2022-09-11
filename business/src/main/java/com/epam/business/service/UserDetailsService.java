package com.epam.business.service;

import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.dto.UserDetailsDto;
import com.epam.business.model.request.CreateUserDetailsRequest;
import java.util.List;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/29/2022
 */
public interface UserDetailsService {
    UserDetailsDto create(CreateUserDetailsRequest request);

    UserDetailsDto getUserDetailsDtoByUsername(String username);

    UserDetailsDto addCertificate(String username, List<GiftCertificateDto> giftCertificates);
}