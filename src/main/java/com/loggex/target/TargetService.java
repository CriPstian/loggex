package com.loggex.target;

import com.loggex.LoggExLevel;

import java.util.ArrayList;
import java.util.List;

public final class TargetService {
    private static TargetService INSTANCE = null;
    private final List<Target> targets;

    private TargetService() {
        this.targets = new ArrayList<>();
    }

    public static synchronized TargetService getInstance() {
        if (INSTANCE == null) {
            return INSTANCE = new TargetService();
        }
        return INSTANCE;
    }

    void add(Target target) {
        this.targets.add(target);
    }

    List<Target> getForLevel(LoggExLevel level) {
        return this.targets.stream()
                .filter(target -> level.compareTo(target.getLevel()) >= 0)
                .toList();
    }

    void clear() {
        this.targets.clear();
    }
}
