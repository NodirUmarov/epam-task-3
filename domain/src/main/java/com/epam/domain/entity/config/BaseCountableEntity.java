package com.epam.domain.entity.config;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import lombok.Getter;

/**
 * Abstract base class for entities. Allows parameterization of id type and implements
 * {@link #equals(Object)} and {@link #hashCode()} based on that id. Counts how many times retrieved entity.
 *
 * @param <ID> the type of the identifier.
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Getter
@MappedSuperclass
public abstract class BaseCountableEntity<ID extends Serializable> extends BaseEntity<ID> {

    @Column(insertable = false, nullable = false)
    private BigDecimal selectOperationsCount;

    @PostLoad
    private void onLoad() {
        selectOperationsCount = selectOperationsCount.add(BigDecimal.ONE);
    }
}