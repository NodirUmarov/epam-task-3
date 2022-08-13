package com.epam.domain.repository;

import com.epam.domain.entity.certificate.GiftCertificate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        RevisionRepository<GiftCertificate, Long, Integer> {

    Optional<GiftCertificate> findByCertificateName(String name);
    Page<GiftCertificate> findByTags_TagName(String tagName, Pageable pageable);
    boolean existsByCertificateName(String name);

    @Transactional
    void deleteByCertificateName(String name);

    default List<GiftCertificate> findAllByCertificateName(List<String> certificateNames) {
        return certificateNames.stream().map(certificateName -> findByCertificateName(certificateName).get()).collect(Collectors.toList());
    }
}