package com.epam.domain.entity;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate extends AbstractAuditable<UserDetails, Long> {

    @Column(name = "certificate_name", nullable = false, length = 100, updatable = false, unique = true)
    private String certificateName;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "duration", nullable = false)
    private LocalDateTime duration;
}
