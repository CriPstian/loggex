# LoggEx
Logger exercise

java version: 17

### Quick walkthrough
Factory to build targets based on configurations in [TargetFactory.java](./src/main/java/com/loggex/target/TargetFactory.java).<br />
The `.create` method receives a `TargetConfig` object that contains the target configuration.<br />
This will build the target and store it in memory by using the [TargetService.java](./src/main/java/com/loggex/target/TargetService.java) class.<br />

The `LoggEx` class is the main class to be used by the user. It has the methods `info`, `debug`, `warning` and `error`.<br />
It has a factory class called [LoggExFactory.java](./src/main/java/com/loggex/target/LoggExFactory.java) that will create a new logger via the `getLogger` static methods.<br />

### Could be improved
- [ ] queue for writing to blocked resources
- [ ] message pattern configuration
- [ ] package level logging configuration
- [ ] read configuration from environment or file

### Examples
#### Creating a logger with level info named 'Main'
```Java
import com.loggex.LoggExLevel;

public class Main {
    public static void main(String[] args) {
        Logger logger = LogggExFactory.getLogger(LoggExLevel.INFO,  Main.class);
        logger.info("Hello world!");
    }
}
```
#### Creating a new target
Please take a look into test [FileTargetTest.java](./src/test/java/FileTargetTest.java) for an example.
