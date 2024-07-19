package com.aguilasa.texttochord.utils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {

    public static List<Path> findFiles(String startDir, String... extensions) throws IOException {
        List<Path> result = new ArrayList<>();
        Path startPath = Paths.get(startDir);

        Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                for (String extension : extensions) {
                    if (file.toString().endsWith(extension)) {
                        result.add(file);
                        break;
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return result;
    }

    public static void main(String[] args) {
        try {
            String startDir = "C:/path/to/start/directory"; // Substitua pelo diretório inicial desejado
            List<Path> docFiles = findFiles(startDir, ".doc", ".docx");

            docFiles.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
