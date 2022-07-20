package com.epam.domain.entity.user;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Getter
@Setter
@Embeddable
public class PasswordID implements Serializable {

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PasswordID that = (PasswordID) o;
        return username != null && Objects.equals(username, that.username)
                && password != null && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
