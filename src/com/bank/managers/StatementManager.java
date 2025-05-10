package com.bank.managers;

import com.bank.model.BankAccount;
import com.bank.model.Statements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatementManager {
    private static StatementManager instance;
    private Map<String, List<Statements>> statements;

        public StatementManager() {
            statements = new HashMap<>();
        }
        public static StatementManager getInstance(){
            if(instance == null) {
                instance = new StatementManager();
            }
                return instance;
        }
        public void addStatement(String iban, Statements statement){

            if(!statements.containsKey(iban))
                statements.put(iban,new ArrayList<>());
            statements.get(iban).add(statement);
        }
        public List<Statements> getStatements(String iban){
            if(statements.containsKey(iban)){
               return statements.get(iban);
            }
            return new ArrayList<>();
        }
        public Map<String, List<Statements>> getAllStatements(){
            return statements;
        }
    }
