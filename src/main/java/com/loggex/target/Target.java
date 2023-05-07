package com.loggex.target;

import com.loggex.LoggExLevel;

public abstract class Target {
    private final LoggExLevel level;
    private final String targetName;

    protected Target(LoggExLevel level, String targetName) {
        this.level = level;
        this.targetName = targetName;
    }

    // This method should be synchronized to avoid multiple threads writing to the same file at the same time
    protected abstract void write(String message);

    public LoggExLevel getLevel() {
        return level;
    }

    public String getTargetName() {
        return targetName;
    }
}
