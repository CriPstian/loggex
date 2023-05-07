package com.loggex.target;

import java.util.HashMap;
import java.util.Map;

public final class LoggExFactory {
    private static LoggExFactory INSTANCE = null;
    private final Map<String, LoggEx> loggers;

    private static synchronized LoggExFactory getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new LoggExFactory();
        }
        return INSTANCE;
    }

    public LoggExFactory() {
        this.loggers = new HashMap<>();
    }

    public static LoggEx getLogger() {
        return getLogger("root");
    }

    public static LoggEx getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static LoggEx getLogger(String name) {
        return getInstance().loggers.computeIfAbsent(name, LoggEx::new);
    }
}
