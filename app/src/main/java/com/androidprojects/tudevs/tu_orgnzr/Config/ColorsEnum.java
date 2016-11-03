package com.androidprojects.tudevs.tu_orgnzr.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Ivan Grigorov on 01/05/2016.
 * Stores default colors to color the programm table view
 */
public enum ColorsEnum {

    BRIGHT_PINK("#ff6666"),
    BRIGHT_YELLOW("#ffff66"),
    BRIGHT_GREEN("#99ff66"),
    BRIGHT_SKYBLUE("#66ffcc"),
    BRIGHT_BLUE("#99ccff"),
    BRIGHT_ORANGE("#ff9966"),
    BRIGHT_RED("#ff6666");

    private String value;

    private ColorsEnum (String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static final List<ColorsEnum> VALUES =   Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ColorsEnum randomColor() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
