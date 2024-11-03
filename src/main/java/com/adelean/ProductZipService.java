package com.adelean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ProductZipService {
    Logger logger = LoggerFactory.getLogger(ProductZipService.class);

    Path unzipFolder() {
        logger.info("Unzipping the file");

        try (var fos = new FileInputStream("src/main/resources/xml.zip");
             ZipInputStream zis = new ZipInputStream(fos);
        ) {
            ZipEntry zipEntry = zis.getNextEntry();

            if (zipEntry == null) {
                throw new NoSuchElementException("The zip file is empty");
            }

            var newPath = saveFile(Path.of("src/main/resources/" + zipEntry.getName()), zis);

            zis.closeEntry();

            logger.info("File unzipped successfully to {}", newPath);
            return newPath;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path saveFile(Path newFilePath, ZipInputStream entry) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(newFilePath.toFile())) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = entry.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }

        return newFilePath;
    }
}
