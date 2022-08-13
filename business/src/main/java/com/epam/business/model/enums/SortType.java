package com.epam.business.model.enums;

import org.springframework.data.domain.Sort.Direction;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/22/2022
 */
public enum SortType {

    ASC(Direction.ASC),
    DESC(Direction.DESC),
    NONE(null);

    private final Direction direction;

    SortType(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
