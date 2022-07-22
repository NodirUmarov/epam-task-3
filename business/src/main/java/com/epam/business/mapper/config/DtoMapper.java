package com.epam.business.mapper.config;

import java.util.Set;

/**
 * The interface that defines basic methods for mapping entity to dto. Object types are generic and
 * must be specified in parameters of class
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
public interface DtoMapper<T, S> {

    /**
     * This method maps passed as argument entity object to a dto.
     *
     * @param entity must not be null
     * @return mapped dto
     */
    S toDto(T entity);

    /**
     * This method maps passed as argument {@link Set} of entities to {@link Set} of dtos.
     *
     * @param entitySet must not be null
     * @return mapped {@link Set} of dtos
     */
    Set<S> toDtoSet(Set<T> entitySet);
}
