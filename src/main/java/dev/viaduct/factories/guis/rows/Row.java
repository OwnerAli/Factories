package dev.viaduct.factories.guis.rows;

import lombok.Getter;

@Getter
public enum Row {

    FIRST(0),
    SECOND(1),
    THIRD(2),
    FOURTH(3),
    FIFTH(4);

    private final int index;

    Row(int index) {
        this.index = index;
    }

}
