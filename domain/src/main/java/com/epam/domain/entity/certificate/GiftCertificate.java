package com.epam.domain.entity.certificate;

import com.epam.domain.entity.config.BaseAuditableEntity;
import com.epam.domain.entity.user.UserDetails;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate extends BaseAuditableEntity<UserDetails, Long> {

    @Column(nullable = false, length = 100, updatable = false, unique = true)
    private String certificateName;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime duration;
}
