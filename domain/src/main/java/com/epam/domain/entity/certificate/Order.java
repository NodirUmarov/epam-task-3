package com.epam.domain.entity.certificate;

import com.epam.domain.entity.config.BaseAuditableEntity;
import com.epam.domain.entity.user.UserDetails;
import java.math.BigDecimal;
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
import org.hibernate.Hibernate;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/9/2022
 */
@Entity
@Getter
@Setter
@Audited
@ToString
@AuditTable(value = "tb_orders_aud")
@Table(name = "tb_orders")
@NoArgsConstructor
public class Order extends BaseAuditableEntity<UserDetails, Long> {

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "order_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_ID", referencedColumnName = "ID"))
    private List<GiftCertificate> orderedCertificates;

    @Column(name = "total_price", nullable = false, updatable = false)
    private BigDecimal totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return getId() != null && Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
