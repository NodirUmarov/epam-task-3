package com.epam.domain.entity.certificate;

import com.epam.domain.entity.config.BaseAuditableEntity;
import com.epam.domain.entity.user.UserDetails;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Entity
@Getter
@Setter
@Audited
@ToString
@AuditTable(value = "tb_gift_certificates_aud", schema = "audit_schema")
@Table(name = "tb_gift_certificates", schema = "gift_certificates_schema")
@NoArgsConstructor
public class GiftCertificate extends BaseAuditableEntity<UserDetails, Long> {

    @Column(nullable = false, length = 100, updatable = false, unique = true)
    private String certificateName;

    @Column
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime duration;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isExpired;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_gift_certificate", schema = "gift_certificates_schema",
            joinColumns = @JoinColumn(name = "certificate_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "user_details_ID", referencedColumnName = "ID"))
    @Exclude
    List<UserDetails> giftToUsers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gift_certificate_has_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "tag_ID", referencedColumnName = "ID"),
            schema = "gift_certificates_schema")
    @Exclude
    private List<Tag> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}