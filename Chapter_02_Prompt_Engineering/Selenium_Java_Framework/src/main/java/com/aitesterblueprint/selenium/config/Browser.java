package com.aitesterblueprint.selenium.config;

import java.util.Locale;

public enum Browser {
    CHROME,
    FIREFOX,
    EDGE;

    public static Browser from(String value) {
        try {
            return Browser.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Unsupported browser '" + value + "'. Choose chrome, firefox, or edge.",
                    exception);
        }
    }
}
