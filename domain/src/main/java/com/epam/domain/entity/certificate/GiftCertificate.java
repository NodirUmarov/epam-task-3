package com.epam.domain.entity.certificate;

import com.epam.domain.entity.config.BaseAuditableEntity;
import com.epam.domain.entity.user.UserDetails;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "tb_gift_certificates")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_ID", referencedColumnName = "ID", nullable = false)
    UserDetails userDetails;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gift_certificate_has_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "tag_ID", referencedColumnName = "ID"))
    private Set<Tag> tags;

}