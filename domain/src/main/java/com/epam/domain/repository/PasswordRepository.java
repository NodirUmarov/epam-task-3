package com.epam.domain.repository;

import com.epam.domain.entity.user.Password;
import com.epam.domain.entity.user.PasswordID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/20/2022
 */
@Repository
public interface PasswordRepository extends JpaRepository<Password, PasswordID> {
}