package com.epam.domain.repository;

import com.epam.domain.entity.certificate.GiftCertificate;
import com.epam.domain.entity.certificate.Tag;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {
    Optional<GiftCertificate> findByCertificateName(String name);
    Set<GiftCertificate> findByTags_TagName(String tagName);
}