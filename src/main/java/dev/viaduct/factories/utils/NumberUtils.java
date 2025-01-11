package dev.viaduct.factories.utils;

public class NumberUtils {

    public static String formatNumber(double number) {
        if (number >= 1_000_000_000) {
            return String.format("%.1fB", number / 1_000_000_000); // Billions
        } else if (number >= 1_000_000) {
            return String.format("%.1fM", number / 1_000_000); // Millions
        } else if (number >= 1_000) {
            return String.format("%.1fK", number / 1_000); // Thousands
        } else {
            return String.format("%.1f", number); // Keep as-is for smaller numbers
        }
    }

}
