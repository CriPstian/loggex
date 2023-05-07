package com.loggex.target;

import com.loggex.LoggExLevel;

public abstract class  TargetConfiguration {
    LoggExLevel level;

    public TargetConfiguration(LoggExLevel level) {
        this.level = level;
    }

    protected abstract Target configure();
}
