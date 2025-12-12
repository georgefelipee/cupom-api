package com.example.cupom_api.shared;

import java.util.regex.Pattern;

public final class CodeSanitizer {
    public static final Pattern ALPHANUMERIC_6 = Pattern.compile("^[A-Z0-9]{6}$");
    private CodeSanitizer() {}

    public static String sanitize(String raw) {
        return raw == null ? null : raw.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
    }

    public static boolean isValid(String sanitized) {
        return sanitized != null && ALPHANUMERIC_6.matcher(sanitized).matches();
    }
}