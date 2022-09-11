package com.epam.api.internationalization;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/16/2022
 */
public enum MessageCode {

    REQUEST_BODY_MISSING("request.body.missing"),
    TAG_NOT_FOUND("tag.not.found"),
    TAG_ALREADY_EXISTS("tag.already.exists"),
    TAG_INVALID("tag.invalid"),
    USER_NOT_FOUND("user.not.found"),
    USER_DUPLICATE_NAME("user.duplicate-name"),
    ORDER_NOT_FOUND("order.not.found"),
    CERTIFICATE_NOT_FOUND("certificate.not.found"),
    CERTIFICATE_DURATION_INVALID("certificate.duration.invalid"),
    CERTIFICATE_PRICE_INVALID("certificate.price.invalid"),
    CERTIFICATE_DESCRIPTION_INVALID("certificate.description.invalid"),
    CERTIFICATE_NAME_INVALID("certificate.name.invalid"),
    PAGINATION_INVALID("pagination.invalid"),
    SORT_PARAMS_INVALID("sort.params.invalid");

    private final String code;

    MessageCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
