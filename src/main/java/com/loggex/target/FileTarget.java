package com.loggex.target;

import com.loggex.LoggExLevel;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileTarget extends Target {
    private static final String TARGET = "FILE";
    private final String path;
    private final String fileName;

    private FileTarget(LoggExLevel level, String path, String fileName) {
        super(level, TARGET);
        this.path = path;
        this.fileName = fileName;
    }

    @Override
    protected void write(String message) {
        var file = new File(path + fileName);
        if(!file.exists()) {
            try {
                if(!file.createNewFile()) {
                    // failed to create file
                    return;
                }
            } catch (IOException e) {
                // exception creating file
                return;
            }
        }
        try(var channel = FileChannel.open(Path.of(path + fileName), StandardOpenOption.APPEND)) {
            channel.write(ByteBuffer.wrap((message + '\n').getBytes()));
        } catch (IOException e) {
            // exception writing to file
        }
    }

    public static Configuration fromConfiguration(LoggExLevel level, String path, String fileName) {
        return new Configuration(level, path, fileName);
    }

    private static class Configuration extends TargetConfiguration {
        private final String path;
        private final String fileName;
        private Configuration(LoggExLevel level, String path, String fileName) {
            super(level);
            this.path = path;
            this.fileName = fileName;
        }

        protected Target configure() {
            return new FileTarget(level, path, fileName);
        }
    }
}
