package com.tetris.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utility class for string manipulation operations.
 * <p>
 * Provides helper methods for string formatting used throughout the application.
 * </p>
 */
public class StringOperations {

    /**
     * Private constructor to prevent instantiation.
     */
    private StringOperations(){}

    /**
     * Converts an underscore-separated string to title case.
     * <p>
     * Example: "MOVE_LEFT" becomes "Move Left"
     * </p>
     *
     * @param s the input string with underscores
     * @return the title-cased string with spaces replacing underscores
     */
    public static String toTitleCase(String s) {
        return Arrays.stream(s.split("_"))
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
