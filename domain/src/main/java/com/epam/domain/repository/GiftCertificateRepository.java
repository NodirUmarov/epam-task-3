package com.epam.domain.repository;

import com.epam.domain.entity.certificate.GiftCertificate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        RevisionRepository<GiftCertificate, Long, Integer> {

    Optional<GiftCertificate> findByCertificateName(String name);

    Page<GiftCertificate> findByTags_TagName(String tag, Pageable pageable);

    boolean existsByCertificateName(String name);

    List<GiftCertificate> findAllByDurationBefore(LocalDateTime dateTime);

    @Transactional
    void deleteByCertificateName(String name);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "WITH updating_query AS ( " +
            "    UPDATE tb_gift_certificates " +
            "        SET is_expired = TRUE " +
            "        FROM tb_gift_certificates gc " +
            "            JOIN user_has_gift_certificate u ON gc.id = u.certificate_id " +
            "        WHERE gc.is_expired IS FALSE AND gc.duration <= now() " +
            "        RETURNING u.user_details_id) " +
            "SELECT user_details_id " +
            "FROM updating_query " +
            "GROUP BY user_details_id;", nativeQuery = true)
    List<String> updateExpirationStatus();

    default List<GiftCertificate> findAllByCertificateName(List<String> certificateNames) {
        return certificateNames.stream().map(certificateName -> findByCertificateName(certificateName).get()).collect(Collectors.toList());
    }
}