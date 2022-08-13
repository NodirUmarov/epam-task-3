package com.epam.domain.repository;

import com.epam.domain.entity.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, String>,
        RevisionRepository<UserDetails, String, Integer> {

}