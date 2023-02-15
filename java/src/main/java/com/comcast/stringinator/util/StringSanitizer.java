package com.comcast.stringinator.util;


import lombok.NonNull;

public class StringSanitizer {

    public static String sanitize(@NonNull final String input) {
        // Replaces space and punctuation from the string.
        return input.replaceAll("[^A-Za-z0-9]+", "");
    }
}
