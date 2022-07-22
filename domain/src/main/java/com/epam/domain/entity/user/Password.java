package com.epam.domain.entity.user;

import com.epam.domain.repository.TagRepository;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Entity
@NoArgsConstructor
public class Password {

    @EmbeddedId
    private PasswordID passwordID;

    @Column(nullable = false)
    private boolean isCurrentPassword;

    private String getUsername() {
        return passwordID.getUsername();
    }

    private void setPassword(String password) {
        passwordID.setPassword(password);
    }

    private String getPassword() {
        return passwordID.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Password password = (Password) o;
        return passwordID != null && Objects.equals(passwordID, password.passwordID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passwordID);
    }
}
