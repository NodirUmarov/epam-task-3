package com.epam.domain.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Password {

    @EmbeddedId
    private PasswordId passwordId;

    @Column(name = "is_current_password", nullable = false)
    private boolean isCurrentPassword;

    private String getUsername() {
        return passwordId.username;
    }

    private void setPassword(String password) {
        passwordId.password = password;
    }

    private String getPassword() {
        return passwordId.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Password password = (Password) o;
        return passwordId != null && Objects.equals(passwordId, password.passwordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwordId);
    }

    @Embeddable
    private static class PasswordId implements Serializable {

        @Column(name = "username", nullable = false, length = 100)
        private String username;

        @Column(name = "password", nullable = false)
        private String password;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
            PasswordId that = (PasswordId) o;
            return username != null && Objects.equals(username, that.username)
                    && password != null && Objects.equals(password, that.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(username, password);
        }
    }
}
