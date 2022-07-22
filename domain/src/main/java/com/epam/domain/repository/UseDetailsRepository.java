package com.epam.domain.repository;

import com.epam.domain.entity.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UseDetailsRepository extends JpaRepository<UserDetails, String> {

}