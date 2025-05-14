package com.bank.storage;

public interface Storable {
    String marshal();
    void unmarshall(String data);
}

