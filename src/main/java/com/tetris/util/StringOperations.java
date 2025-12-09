package com.tetris.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringOperations {
    private StringOperations(){}

    public static String toTitleCase(String s) {
        return Arrays.stream(s.split("_"))
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
