package com.loggex.target;

public final class TargetFactory {

    private TargetFactory() {
        throw new IllegalStateException("Utility class");
    }
    public static void create(TargetConfiguration configuration) {
        var target = configuration.configure();
        TargetService.getInstance().add(target) ;
    }

    public static void clear() {
        TargetService.getInstance().clear();
    }
}
