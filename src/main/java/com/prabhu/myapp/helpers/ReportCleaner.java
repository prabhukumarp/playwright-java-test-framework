package com.prabhu.myapp.helpers;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

public class ReportCleaner {
    public static void cleanFolder(File folder, Logger logger) {
        if (folder.exists()) {
            try {
                FileUtils.cleanDirectory(folder);
                logger.info("🧹 Cleaned folder: {}", folder.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Failed to clean folder: " + folder.getAbsolutePath(), e);
            }
        } else {
            folder.mkdirs();
            logger.info("Created folder: {}", folder.getAbsolutePath());
        }
    }
}
