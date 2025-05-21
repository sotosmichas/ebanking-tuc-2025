package com.bank.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE;

    public static LocalDate fromFilename(String filename) {
        return LocalDate.parse(filename.replace(".csv", ""), FORMATTER);
    }

    public static String toFilename(LocalDate date) {
        return date.format(FORMATTER) + ".csv";
    }

    public static boolean isOverdue(LocalDate dueDate, LocalDate today) {
        return dueDate.isBefore(today);
    }

    public static long daysBetween(LocalDate from, LocalDate to) {
        return ChronoUnit.DAYS.between(from, to);
    }
}
