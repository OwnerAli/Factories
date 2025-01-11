package dev.viaduct.factories.utils;

public class RankingUtil {

    /**
     * Converts a number into a ranking string with an ordinal suffix.
     *
     * @param number The number to convert.
     * @return A string with the number and its ordinal suffix (e.g., "1st", "2nd").
     */
    public static String getRanking(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Number must be greater than 0");
        }

        int lastDigit = number % 10;
        int lastTwoDigits = number % 100;

        // Handle special cases for 11th, 12th, and 13th
        if (lastTwoDigits >= 11 && lastTwoDigits <= 13) {
            return number + "th";
        }

        // Handle general cases
        return switch (lastDigit) {
            case 1 -> number + "st";
            case 2 -> number + "nd";
            case 3 -> number + "rd";
            default -> number + "th";
        };
    }

}

