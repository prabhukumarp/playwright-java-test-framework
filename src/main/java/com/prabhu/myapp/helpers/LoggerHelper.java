package com.prabhu.myapp.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerHelper {

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static void logException(Logger logger, String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void logInfo(Logger logger, String message) {
        logger.info(message);
    }

    public static void logDebug(Logger logger, String message) {
        logger.debug(message);
    }

    public static void logWarn(Logger logger, String message) {
        logger.warn(message);
    }
}
