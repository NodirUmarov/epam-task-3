package com.epam.domain.repository.query;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/11/2022
 */
public class TagQuery {
    private TagQuery() {
    }

    public static final String GROUP_AND_COUNT_JOINS = "" +
            "SELECT entity, count(entity) as c " +
            "FROM Tag as entity " +
            "JOIN GiftCertificate as g ON entity in elements(g.tags) " +
            "GROUP BY entity.id " +
            "ORDER BY c DESC";

}
