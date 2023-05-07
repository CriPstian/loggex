package com.loggex.target;

import com.loggex.LoggExLevel;

import java.util.function.Consumer;

import static java.lang.String.format;

public class LoggEx {
    private static final String FORMAT = "[%s][%s][%s]: %s";
    private final TargetService targetService;
    private final String name;

    LoggEx(String name) {
        this.targetService = TargetService.getInstance();
        this.name = name;
    }

    public void debug(String message, Object... args) {
        this.targetService.getForLevel(LoggExLevel.DEBUG).forEach(writeToTargetFn(LoggExLevel.DEBUG, message, args));
    }

    public void info(String message, Object... args) {
        this.targetService.getForLevel(LoggExLevel.INFO).forEach(writeToTargetFn(LoggExLevel.INFO, message, args));
    }

    public void warning(String message, Object... args) {
        this.targetService.getForLevel(LoggExLevel.WARNING).forEach(writeToTargetFn(LoggExLevel.WARNING, message, args));
    }

    public void error(String message, Object... args) {
        this.targetService.getForLevel(LoggExLevel.ERROR).forEach(writeToTargetFn(LoggExLevel.ERROR, message, args));
    }

    private Consumer<Target> writeToTargetFn(LoggExLevel level, String message, Object... args) {
        return (target) -> target.write(format(FORMAT, target.getTargetName(), name, level, format(message, args)));
    }
}
