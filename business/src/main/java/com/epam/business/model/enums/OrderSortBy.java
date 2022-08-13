package com.epam.business.model.enums;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/10/2022
 */
public enum OrderSortBy {
    ID("id"),
    TOTAL_PRICE("totalPrice");

    private final String attribute;

    OrderSortBy(String attribute) {
        this.attribute = attribute;
    }

    public String getAttributeName() {
        return attribute;
    }
}
