import com.loggex.LoggExLevel;
import com.loggex.target.LoggExFactory;
import com.loggex.target.Target;
import com.loggex.target.TargetConfiguration;
import com.loggex.target.TargetFactory;
import org.junit.jupiter.api.Test;

public class CreatingANewTargetTest {

    @Test
    void testNewTarget() {
        var configuration = SomeEndpointTarget.configuration();
        TargetFactory.create(configuration);
        var logger = LoggExFactory.getLogger(CreatingANewTargetTest.class);
        logger.info("testing out my new logger");
    }

}

class SomeEndpointTarget extends Target {

    private static final String TARGET_NAME = "SOME_ENDPOINT";

    protected SomeEndpointTarget() {
        super(LoggExLevel.DEBUG, TARGET_NAME);
    }

    @Override
    protected void write(String message) {
        System.out.println("message");
    }

    static Configuration configuration() {
        return new Configuration();
    }

    private static class Configuration extends TargetConfiguration {
        protected Configuration() {
            super(LoggExLevel.DEBUG);
        }

        @Override
        protected Target configure() {
            return new SomeEndpointTarget();
        }
    }
}