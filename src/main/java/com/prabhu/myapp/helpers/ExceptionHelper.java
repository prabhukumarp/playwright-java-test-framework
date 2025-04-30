package com.prabhu.myapp.helpers;

import com.prabhu.myapp.exceptions.BaseException;
import org.apache.logging.log4j.Logger;

public class ExceptionHelper {
    public static void logAndThrow(Logger logger, String message, Exception e) {
        logger.error(message, e);
        throw new BaseException(message, e);
    }

    public static void logAndThrow(Logger logger, String message) {
        logger.error(message);
        throw new BaseException(message);
    }

    public static void throwIfNull(Object obj, String message, Logger logger) {
        if (obj == null) {
            logAndThrow(logger, message);
        }
    }

    public static void throwIf(boolean condition, String message, Logger logger) {
        if (condition) {
            logAndThrow(logger, message);
        }
    }
}
