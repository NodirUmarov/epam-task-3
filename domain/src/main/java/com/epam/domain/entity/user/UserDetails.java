package com.epam.domain.entity.user;

import com.epam.domain.entity.config.BaseEntity;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Entity
@Getter
@Setter
@Builder
@Table(name = "tb_user_details")
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails extends BaseEntity<String> {

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

    @PostLoad
    private void onLoad() {
        fullName = getNonNullString(firstName) + " " + getNonNullString(patronymic) + " " + getNonNullString(lastName);
    }

    private String getNonNullString(String str) {
        return str == null ? "" : str;
    }
}