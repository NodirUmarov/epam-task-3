package com.epam.domain.entity.config;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Abstract base class for auditable entities. Stores the audition values in persistent fields.
 * Type parameters:
 * <U> – the auditing type. Typically some kind of user.
 * <ID> – the type of the auditing type's identifier.
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseAuditableEntity<U, ID extends Serializable> extends BaseEntity<ID> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_ID", referencedColumnName = "ID", nullable = false, updatable = false)
    private U createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by_ID", referencedColumnName = "ID", nullable = false)
    private U lastModifiedBy;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column
    private LocalDateTime lastModifiedDate;

}
