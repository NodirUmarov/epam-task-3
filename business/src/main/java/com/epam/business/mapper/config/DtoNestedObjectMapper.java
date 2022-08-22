package com.epam.business.mapper.config;

import java.util.List;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/22/2022
 */
public interface DtoNestedObjectMapper<T, S> {
    S toDtoWithoutSomeChild(T object);

    List<S> toDtoListWithoutSomeChild(List<T> objects);
}
