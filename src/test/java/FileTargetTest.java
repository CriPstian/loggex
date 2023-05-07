import com.loggex.LoggExLevel;
import com.loggex.target.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FileTargetTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void beforeEach() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void afterEach() throws IOException {
        System.setOut(originalOut);
        TargetFactory.clear();

        Files.deleteIfExists(Path.of("test.log"));
    }

    @Test
    void callsWriteMethodWhenCalled() throws IOException {
        TargetFactory.create(ConsoleTarget.fromConfiguration(LoggExLevel.ERROR));
        TargetFactory.create(FileTarget.fromConfiguration(LoggExLevel.ERROR, "", "test.log"));

        var message = "Test message";

        LoggExFactory.getLogger().error(message);

        assertEquals("[CONSOLE][root][ERROR]: " + message + '\n', out.toString(), "should be called with the message as an argument");
        var lines = Files.readAllLines(Path.of( "test.log"));
        assertEquals("[FILE][root][ERROR]: " + message, lines.get(0), "should contain the message");
    }

    @Test
    void shouldWriteOnlyErrorAsDefault() {
        TargetFactory.create(FileTarget.fromConfiguration(LoggExLevel.ERROR, "", "test.log"));
        var message = "Test message";
        LoggExFactory.getLogger().debug(message);

        assertFalse(Files.exists(Path.of( "test.log")), "should not create the file");
    }

    @Test
    void shouldNotWriteOtherLevelsWhenErrorOnlyEnabled() {
        TargetFactory.create(FileTarget.fromConfiguration(LoggExLevel.ERROR, "", "test.log"));
        var message = "Test message";
        var consoleLoggEx = LoggExFactory.getLogger();
        consoleLoggEx.debug(message);
        consoleLoggEx.info(message);
        consoleLoggEx.warning(message);

        assertFalse(Files.exists(Path.of( "test.log")));
    }

    @Test
    void shouldWriteAllLevelsWhenDebugIsEnabled() throws IOException {
        TargetFactory.create(FileTarget.fromConfiguration(LoggExLevel.DEBUG, "", "test.log"));
        var message = "Test message";
        var consoleLoggEx = LoggExFactory.getLogger();
        consoleLoggEx.debug(message);
        consoleLoggEx.info(message);
        consoleLoggEx.warning(message);
        consoleLoggEx.error(message);


        var lines = Files.readAllLines(Path.of( "test.log"));
        for (LoggExLevel value : LoggExLevel.values()) {
            var expectedMessage = "[FILE][root][" + value + "]: " + message;
            assertEquals(expectedMessage, lines.get(value.ordinal()), "should contain the message");
        }
    }
}
