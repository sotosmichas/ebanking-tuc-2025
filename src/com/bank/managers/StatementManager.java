package com.bank.managers;

import com.bank.model.Statements;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDate;


public class StatementManager {
    private static StatementManager instance;
    private final Map<String, List<Statements>> statements;



    private StatementManager() {
        statements = new HashMap<>();
    }

    public static StatementManager getInstance() {
        if (instance == null) {
            instance = new StatementManager();
        }
        return instance;
    }

    public List<Statements> getRecentStatements(String iban, int count) {
        List<Statements> all = statements.getOrDefault(iban, new ArrayList<>());
        int fromIndex = Math.max(all.size() - count, 0);
        return all.subList(fromIndex, all.size());
    }

    public List<Statements> getStatementsForAccount(String iban) {
        return new ArrayList<>(statements.getOrDefault(iban, new ArrayList<>()));
    }

    public List<Statements> getMonthlyStatements(String iban, String monthYear) {
        List<Statements> result = new ArrayList<>();
        List<Statements> all = statements.getOrDefault(iban, new ArrayList<>());

        String[] parts = monthYear.split("/");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid format");

        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);

        for (Statements s : all) {
            if (s.getDate().getMonthValue() == month && s.getDate().getYear() == year) {
                result.add(s);
            }
        }

        return result;
    }

    public void addStatement(String iban, Statements statement) {
        statements.computeIfAbsent(iban, k -> new ArrayList<>()).add(0, statement);

        try {
            Files.createDirectories(Paths.get("data/statements"));
            Files.writeString(
                    Paths.get("data/statements/" + iban + ".csv"),
                    statement.toCsv() + "\n",
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException("Error writing statement to file for " + iban, e);
        }
    }

    public List<Statements> getStatements(String iban) {
        return statements.getOrDefault(iban, new ArrayList<>());
    }

    public Map<String, List<Statements>> getAllStatements() {
        return statements;
    }

    public void loadStatementsFromFile(String iban) {
        Path path = Paths.get("data/statements/" + iban + ".csv");
        if (!Files.exists(path)) return;

        try {
            List<String> lines = Files.readAllLines(path);
            List<Statements> list = new ArrayList<>();
            for (String line : lines) {
                list.add(Statements.fromCsv(line));
            }

            Collections.reverse(list);
            statements.put(iban, list);
        } catch (IOException e) {
            throw new RuntimeException("Error loading statements from file for " + iban, e);
        }
    }
}
