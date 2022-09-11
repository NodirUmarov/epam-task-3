package com.epam.domain.entity.certificate;

import com.epam.domain.entity.config.BaseEntity;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
@AuditTable(value = "tb_tags_aud")
@Table(name = "tb_tags")
@NoArgsConstructor
public class Tag extends BaseEntity<Long> {

    @Column(name = "tag_name", nullable = false, length = 100, updatable = false, unique = true)
    private String tagName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tag tag = (Tag) o;
        return getId() != null && Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
