package com.prabhu.myapp.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.layout.JsonLayout;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggingInitializer {

    private static FileAppender suiteFileAppender;

    // Initialize suite-level logging with JSON output
    public static void initializeSuiteLogging(String suiteName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String logsDir = "logs" + File.separator + suiteName;
        FileHelper.ensureDirectoryExists(logsDir);
        String logFilePath = logsDir + File.separator + suiteName + "_" + timestamp + ".log";

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        /*JsonLayout layout = JsonLayout.newBuilder()
                .compact(false) // Pretty-print JSON
                .eventEol(true)  // Ensure each event is on a new line
                .withConfiguration(config)
                .build();*/
        JsonLayout layout = JsonLayout.newBuilder()
                .setConfiguration(config)
                .setLocationInfo(true)
                .setProperties(true)
                .setPropertiesAsList(false)
                .setComplete(false)
                .setCompact(true)
                .setEventEol(true)
                .setHeader("[".getBytes(StandardCharsets.UTF_8))
                .setFooter("]".getBytes(StandardCharsets.UTF_8))
                .setCharset(StandardCharsets.UTF_8)
                .setIncludeStacktrace(true)
                .setStacktraceAsString(true)
                .setIncludeNullDelimiter(false)
                .setIncludeTimeMillis(true)
                .setAdditionalFields(null) // Or provide KeyValuePair[]
                .setObjectMessageAsJsonObject(true)
                .build();



        suiteFileAppender = FileAppender.newBuilder()
                .setName("SuiteFileAppender")
                .withFileName(logFilePath)
                .withAppend(false)
                .withBufferedIo(true)
                .setLayout(layout)
                .setConfiguration(config)
                .build();

        suiteFileAppender.start();

        LoggerConfig rootLoggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        rootLoggerConfig.addAppender(suiteFileAppender, null, null);
        context.updateLoggers();
    }

    // Shutdown suite-level logging
    public static void shutdownSuiteLogging() {
        if (suiteFileAppender != null) {
            suiteFileAppender.stop();

            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration config = context.getConfiguration();
            LoggerConfig rootLoggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
            rootLoggerConfig.removeAppender("SuiteFileAppender");
            context.updateLoggers();
        }
    }
}
