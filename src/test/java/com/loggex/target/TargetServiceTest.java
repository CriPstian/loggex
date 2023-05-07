package com.loggex.target;

import com.loggex.LoggExLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TargetServiceTest {

    @AfterEach
    void beforeEach() {
        TargetService.getInstance().clear();
    }

    @Test
    void shouldAddToListOfTargetsAndRetrieveCorrectOnesThenCleanup() {
        Arrays.stream(LoggExLevel.values())
                .forEach(level -> TargetFactory.create(ConsoleTarget.fromConfiguration(level)));
        IntStream.range(0, LoggExLevel.values().length)
                        .forEach(index -> assertEquals(
                                index + 1,
                                TargetService.getInstance().getForLevel(LoggExLevel.values()[index]).size(),
                                "should add the target to the list of targets"));
        TargetService.getInstance().clear();
        Arrays.stream(LoggExLevel.values())
                .forEach(level -> assertTrue(TargetService.getInstance().getForLevel(level).isEmpty()));
    }
}