package com.epam.business.model.enums;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/22/2022
 */
public enum TagSortBy {
    ID("id"),
    NAME("tagName");

    private final String attributeName;

    TagSortBy(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
