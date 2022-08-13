package com.epam.domain.repository;

import com.epam.domain.entity.certificate.Tag;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>,
        RevisionRepository<Tag, Long, Integer> {

    Optional<Tag> findByTagName(String name);


    @Query("SELECT entity " +
            "FROM Tag as entity " +
            "JOIN GiftCertificate as g ON entity in elements(g.tags) " +
            "GROUP BY entity.id " +
            "HAVING count(entity) > 0 " +
            "ORDER BY count(entity) DESC")
    Page<Tag> findMostUsed(Pageable pageable);

    default List<Tag> findAllOrSaveIfNotExists(List<Tag> tags) {
        return tags.stream().map(tag -> findByTagName(tag.getTagName()).orElseGet(() -> save(tag)))
                .collect(Collectors.toList());
    }
}