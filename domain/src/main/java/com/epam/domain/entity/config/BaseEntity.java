package com.epam.domain.entity.config;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.util.ProxyUtils;

/**
 * Abstract base class for entities. Allows parameterization of id type, chooses auto-generation and implements
 * {@link #equals(Object)} and {@link #hashCode()} based on that id.
 *
 * @param <T> the type of the identifier.
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseEntity<T extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, unique = true, nullable = false)
    private T id;

    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
            return false;
        }

        org.springframework.data.jpa.domain.AbstractPersistable<?> that = (org.springframework.data.jpa.domain.AbstractPersistable<?>) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }
}
