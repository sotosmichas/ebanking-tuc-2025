package com.bank.storage;

import java.io.IOException;

public interface StorageManager {
    void load(Storable s, String filePath) throws IOException;
    void save(Storable s, String filePath, boolean append) throws IOException;
}

