package com.adelean;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ProductZipService {

    Path unzipFolder() {

        try (var fos = new FileInputStream("src/main/resources/xml.zip");
             ZipInputStream zis = new ZipInputStream(fos);
        ) {
            ZipEntry zipEntry = zis.getNextEntry();

            if (zipEntry == null) {
                throw new NoSuchElementException("The zip file is empty");
            }
            var fileName = zipEntry.getName();


            return Path.of("src/main/resources/" + fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
