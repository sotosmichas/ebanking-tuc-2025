package com.bank.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class StorageManagerImpl implements StorageManager {

    @Override
    public void load(Storable s, String filePath) throws IOException {
        String data = Files.readString(Paths.get(filePath));
        s.unmarshal(data);
    }

    @Override
    public void save(Storable s, String filePath, boolean append) throws IOException {
        String content = s.marshal();

        if (append) {
            Files.writeString(
                    Paths.get(filePath),
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } else {
            Files.writeString(Paths.get(filePath), content);
        }
    }
}
