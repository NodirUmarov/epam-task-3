package com.epam.domain.entity.config;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import lombok.Getter;

/**
 * Abstract base class for auditable entities. Stores the audition values in persistent fields, and counts how many times retrieved
 * Type parameters:
 * <U> – the auditing type. Typically some kind of user.
 * <ID> – the type of the auditing type's identifier.
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Getter
@MappedSuperclass
public abstract class BaseAuditableAndCountableEntity<U, ID extends Serializable> extends BaseAuditableEntity<U, ID> {

    @Column(insertable = false, nullable = false)
    private BigDecimal selectOperationsCount;

    @PostLoad
    private void onLoad() {
        selectOperationsCount = selectOperationsCount.add(BigDecimal.ONE);
    }
}