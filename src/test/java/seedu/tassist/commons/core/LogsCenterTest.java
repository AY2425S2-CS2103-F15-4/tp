package seedu.tassist.commons.core;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class LogsCenterTest {

    @Test
    public void init() {
        Config config = new Config();
        config.setLogLevel(Level.INFO);
        LogsCenter.init(config);
        assertTrue(Logger.getLogger("").getLevel().equals(config.getLogLevel()));
    }

    @Test
    public void getLogger() {
        String loggerName = "test";
        Logger expectedLogger = Logger.getLogger("tassist.test");
        assertTrue(LogsCenter.getLogger(loggerName).equals(expectedLogger));
    }

}
