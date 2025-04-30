package com.prabhu.myapp.helpers;

import java.io.File;

public class FileHelper {
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.delete();
    }
}
