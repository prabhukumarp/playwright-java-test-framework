package com.prabhu.myapp.helpers;
import java.io.File;

public class DirectoryHelper {
    public static void ensureDirectoryExists(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}