package com.epam.domain.entity;

import javax.persistence.PostLoad;
import javax.persistence.Transient;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

    @Id
    @Column(name = "username", nullable = false, length = 100, unique = true)
    private String username;

    @Column(name = "phone_number", length = 100, unique = true)
    private String phoneNumber;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "patronymic", length = 100)
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
        return username != null && Objects.equals(username, that.username);
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