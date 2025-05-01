package com.prabhu.myapp.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LoggerHelper {

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
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

    public static void putContext(String key, String value) {
        MDC.put(key, value);
    }

    public static void removeContext(String key) {
        MDC.remove(key);
    }

    public static void clearContext() {
        MDC.clear();
    }
}
