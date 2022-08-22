package com.epam.business.mapper.config;

import java.util.List;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/22/2022
 */
public interface EntityNestedObjectMapper<T, S> {
    T toEntityWithoutSomeChild(S object);

    List<T> toEntityListWithoutSomeChild(List<S> objects);
}