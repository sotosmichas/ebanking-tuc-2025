package com.bank.util;



import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator {

    private static final Set<String> usedIbans = new HashSet<>();
    private static final Set<String> usedTransactionIds = new HashSet<>();
    private static final Set<String> usedRfCodes = new HashSet<>();

    public static String generateIban(String accountType) {
        String countryCode = "GR";
        String accountTypeCode = accountType.equalsIgnoreCase("Personal") ? "100" : "200";

        String iban;
        do {
            long num = ThreadLocalRandom.current().nextLong(100_000_000_000_000L, 1_000_000_000_000_000L);
            String random15Digit = String.format("%015d", num);
            iban = countryCode + accountTypeCode + random15Digit;
        } while (usedIbans.contains(iban));

        usedIbans.add(iban);
        return iban;
    }

    public static String generateTransactionId() {
        String id;
        do {
            long num = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
            id = "TX" + num;
        } while (usedTransactionIds.contains(id));

        usedTransactionIds.add(id);
        return id;
    }

    public static String generateRfCode() {
        String rfCode;
        do {
            long num = ThreadLocalRandom.current().nextLong(10_000_000L, 100_000_000L);
            rfCode = "RF" + num;
        } while (usedRfCodes.contains(rfCode));

        usedRfCodes.add(rfCode);
        return rfCode;
    }

    // Optional: για reset κατά το testing
    public static void clearAll() {
        usedIbans.clear();
        usedTransactionIds.clear();
        usedRfCodes.clear();
    }
}
