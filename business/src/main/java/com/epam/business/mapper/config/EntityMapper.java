package com.epam.business.mapper.config;

import java.util.Set;

/**
 * The interface that defines basic methods for mapping dto to entity. Object types are generic and
 * must be specified in parameters of class
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
public interface EntityMapper<T, S> {

    /**
     * This method maps passed as argument dto object to an entity.
     *
     * @param dto must not be null
     * @return mapped entity
     */
    T toEntity(S dto);

    /**
     * This method maps passed as argument {@link Set} of dtos to {@link Set} of entities.
     *
     * @param dtoSet must not be null
     * @return mapped {@link Set} of entities
     */
    Set<T> toEntitySet(Set<S> dtoSet);

}
