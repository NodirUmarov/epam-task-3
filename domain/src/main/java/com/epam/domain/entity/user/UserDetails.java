package com.epam.domain.entity.user;

import com.epam.domain.entity.certificate.GiftCertificate;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@ToString(callSuper = true)
@AuditTable(value = "tb_user_details_aud", schema = "audit_schema")
@Table(name = "tb_user_details", schema = "gift_certificates_schema")
@NoArgsConstructor
public class UserDetails {

    @Id
    @Column(name = "ID", updatable = false, unique = true, nullable = false)
    private String id;

    @Column(length = 100, unique = true)
    private String phoneNumber;

    @Column(length = 100)
    private String firstName;

    @Column(length = 100)
    private String lastName;

    @Column(length = 100)
    private String patronymic;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "send_emails")
    private Boolean sendEmail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_has_gift_certificate", schema = "gift_certificates_schema",
            joinColumns = @JoinColumn(name = "user_details_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "certificate_ID", referencedColumnName = "ID"))
    @Exclude
    private List<GiftCertificate> giftCertificates;

    @Transient
    private String fullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDetails that = (UserDetails) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @PrePersist
    @PostLoad
    private void onLoad() {
        fullName = getNonNullString(firstName) + " " + getNonNullString(lastName) + " " + getNonNullString(patronymic);
    }

    private String getNonNullString(String str) {
        return str == null ? "" : str;
    }
}