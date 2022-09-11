package com.epam.business.service;

import com.epam.business.exception.EntityExistsException;
import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.mapper.dto.GiftCertificateMapper;
import com.epam.business.mapper.request.CreateGiftCertificateMapper;
import com.epam.business.mapper.request.UpdateGiftCertificateMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.enums.GiftCertificateSortBy;
import com.epam.business.model.enums.SortType;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.repository.GiftCertificateRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * <p>The interface that defines the gift-certificate api of application.
 *
 * <ul>
 *  <li><b>create</b>
 *      - create the certificate and return {@link GiftCertificateDto} it with id</li>
 *  <li><b>getByName</b>
 *      - retrieve {@link GiftCertificateDto} by its name</li>
 *  <li><b>getByTag</b>
 *      - retrieves all {@link GiftCertificateDto GiftSertificateDtos} wrapped in {@link Set} by single tag</li>
 * <li><b>deleteById</b>
 *      - delete the certificate by its id</li>
 * <li><b>updateById</b>
 *      - updated only changed field and return {@link GiftCertificateDto}</li>
 * <li><b>updateTags</b>
 *      - Changes the set of tags of certificate and return updated{@link GiftCertificateDto}</li>
 * </ul>
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/15/2022
 * @version 0.1.0
 * @see GiftCertificateRepository
 * @see GiftCertificate
 * @see GiftCertificateMapper
 * @see CreateGiftCertificateMapper
 * @see UpdateGiftCertificateMapper
 * @since 0.1.0
 */

@Service
public interface GiftCertificateService {

    /**
     * <p>Creates given certificate, if name provided is unique.</p>
     *
     * @param request must not be null
     * @return {@link GiftCertificateDto} with assigned id. Will never be null
     * @throws EntityExistsException if certificate with given name already exists
     * @since 0.1.0
     */
    GiftCertificateDto create(CreateGiftCertificateRequest request) throws EntityExistsException;

    /**
     * <p>Retrieves {@link GiftCertificateDto} by its id.</p>
     *
     * @param id must not be null
     * @return {@link GiftCertificateDto} by given id
     * @throws EntityIdNotFoundException if nothing found by provided id
     * @since 0.1.0
     */
    GiftCertificateDto getById(Long id) throws EntityIdNotFoundException;

    /**
     * <p>Retrieves {@link GiftCertificateDto} by its name.</p>
     *
     * @param name must not be null
     * @return {@link GiftCertificate} with given id wrapped in {@link java.util.Optional} or {@link Optional#empty()} if none found
     * @throws EntityNameNotFoundException if nothing found by provided name
     * @since 0.1.0
     */
    GiftCertificateDto getByName(String name) throws EntityNameNotFoundException;

    /**
     * <p>Retrieves all {@link GiftCertificateDto GiftCertificateDtos} with the given tag.
     * If tag is not found, no entities are returned for the tag
     * <p>
     * Note that the order of elements can be configured by {@link SortType}.</p>
     *
     * @param tag      must not be null
     * @param quantity must not be null nor negative or zero values
     * @param page     must not be null nor negative or zero values
     * @param sortType for configuring the order of elements. Can be {@link SortType#ASC}, {@link SortType#DESC}, {@link SortType#NONE}
     * @return all {@link GiftCertificateDto GiftCertificateDtos} in specified order,
     * in requested quantity and from requested position
     * @since 0.1.0
     */
    List<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType, GiftCertificateSortBy sortBy);

        /**
         * <p>Deletes certificate by given id</p>
         *
         * @param name must not be null
         * @throws EntityNameNotFoundException if nothing found by provided name
         * @since 0.1.0
         */
    void deleteByName(String name) throws EntityNameNotFoundException;

    /**
     * <p>Updates certificate by given id. It affects only changed fields</p>
     *
     * @param request must not be null
     * @return {@link GiftCertificateDto} with updated fields. Will never be null
     * @since 0.1.0
     */
    GiftCertificateDto updateById(Long id, UpdateGiftCertificateRequest request);

    /**
     * <p>Changes the set of tags of certificate.</p>
     *
     * @param id  must not be null nor negative or zero values
     * @param tags elements must not be null
     * @return updated {@link GiftCertificateDto}
     * @throws EntityIdNotFoundException if certificate with given id not found
     * @since 0.1.0
     */
    GiftCertificateDto addTagsToCertificate(Long id, List<TagRequest> tags) throws EntityIdNotFoundException;

    /**
     * <p>Changes the set of tags of certificate.</p>
     *
     * @param id  must not be null nor negative or zero values
     * @param tags elements must not be null
     * @return updated {@link GiftCertificateDto}
     * @throws EntityIdNotFoundException if certificate with given id not found
     * @since 0.1.0
     */
    GiftCertificateDto removeTagsFromCertificate(Long id, List<TagRequest> tags) throws EntityIdNotFoundException;


    List<GiftCertificateDto> findAllByNames(List<String> giftCertificatesNames);
}
