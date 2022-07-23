package com.epam.business.model.enums;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/23/2022
 */
public enum GiftCertificateSortBy {
    ID("id"),
    NAME("certificateName"),
    DESCRIPTION("description"),
    PRICE("price"),
    DURATION("duration"),
    CREATED("createDate"),
    UPDATED("lastUpdateDate");

    private final String attribute;

    GiftCertificateSortBy(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeName() {
        return attribute;
    }
}
