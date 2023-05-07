package com.loggex.target;

import com.loggex.LoggExLevel;

import java.util.concurrent.locks.ReentrantLock;

public class ConsoleTarget extends Target {
    private static final String TARGET = "CONSOLE";
    private final ReentrantLock lock = new ReentrantLock();

    private ConsoleTarget(LoggExLevel level) {
        super(level, TARGET);
    }

    @Override
    protected void write(String message) {
        lock.lock();
        try {
            System.out.println(message);
        } finally {
            lock.unlock();
        }
    }

    public static Configuration fromConfiguration(LoggExLevel level) {
        return new Configuration(level);
    }

    private static class Configuration extends TargetConfiguration {
        private Configuration(LoggExLevel level) {
            super(level);
        }

        protected Target configure() {
            return new ConsoleTarget(level);
        }
    }

}
