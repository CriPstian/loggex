import com.loggex.LoggExLevel;
import com.loggex.target.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ConsoleTargetTest {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void beforeEach() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void afterEach() {
        System.setOut(originalOut);
        TargetFactory.clear();
    }

    @Test
    void callsWriteMethodWhenCalled() {
        TargetFactory.create(ConsoleTarget.fromConfiguration(LoggExLevel.ERROR));

        var message = "Test message but with varrargs - %d %s";

        LoggExFactory.getLogger().error(message, 99, "test");

        assertEquals("[CONSOLE][root][ERROR]: Test message but with varrargs - 99 test\n", out.toString(), "should be called with the message as an argument");
    }

    @Test
    void shouldWriteOnlyErrorAsDefault() {
        TargetFactory.create(ConsoleTarget.fromConfiguration(LoggExLevel.ERROR));
        var message = "Test message";
        var expectedMessage = "";
        LoggExFactory.getLogger().debug(message);

        assertEquals(expectedMessage, out.toString(), "nothing should be written to the console");
    }

    @Test
    void shouldNotWriteOtherLevelsWhenErrorOnlyEnabled() {
        TargetFactory.create(ConsoleTarget.fromConfiguration(LoggExLevel.ERROR));
        var message = "Test message";
        var consoleLoggEx = LoggExFactory.getLogger();
        consoleLoggEx.debug(message);
        consoleLoggEx.info(message);
        consoleLoggEx.warning(message);

        assertEquals("", out.toString(), "nothing should be written to the console");
    }

    @Test
    void shouldWriteAllLevelsWhenDebugIsEnabled() {
        TargetFactory.create(ConsoleTarget.fromConfiguration(LoggExLevel.DEBUG));
        var message = "Test message";
        var expectedMessage = "";
        var consoleLoggEx = LoggExFactory.getLogger();
        consoleLoggEx.debug(message);
        expectedMessage += "[CONSOLE][root][DEBUG]: " + message + '\n';
        consoleLoggEx.info(message);
        expectedMessage += "[CONSOLE][root][INFO]: " + message + '\n';
        consoleLoggEx.warning(message);
        expectedMessage += "[CONSOLE][root][WARNING]: " + message + '\n';
        consoleLoggEx.error(message);
        expectedMessage += "[CONSOLE][root][ERROR]: " + message + '\n';

        assertEquals(expectedMessage, out.toString(), "The write method should be called with the message as an argument");
    }

    @Test
    void shouldLogToConsoleButNotToFile() {
        TargetFactory.create(ConsoleTarget.fromConfiguration(LoggExLevel.DEBUG));
        TargetFactory.create(FileTarget.fromConfiguration(LoggExLevel.ERROR, "", "test.log"));
        var message = "no matter what I write this will not create a file";
        LoggExFactory.getLogger().warning(message);

        assertEquals("[CONSOLE][root][WARNING]: " + message + '\n', out.toString(), "should write to console");
        assertFalse(Files.exists(Path.of("test.log")), "should not write to file");
    }
}
