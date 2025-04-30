package com.prabhu.myapp.helpers;

import java.io.File;

public class FileHelper {
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }

    public static void createDirectoryIfNotExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + path);
            }
        }
    }

    public static void ensureDirectoryExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static String toWebPath(String path) {
        return path.replace("\\", "/");
    }
}
