package test.nz.ac.massey.cs.sdc.assign1.s15232331;

import static org.junit.Assert.*;

import nz.ac.massey.cs.sdc.assign1.s15232331.VelocityLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.RootLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;


public class VelocityLayoutTest {

    VelocityLayout velocityLayout;

    private Logger logger, rootLogger;
    private long timeStamp;

    private Throwable throwable;
    private LoggingEvent loggingEventDebug, loggingEventInfo, loggingEventError, loggingEventRootLogger;

    @Before
    public void setUp() throws Exception {
        velocityLayout = new VelocityLayout();

        logger = Logger.getLogger("VelocityLayoutTestLogger");
        rootLogger = RootLogger.getRootLogger();

        timeStamp = new Timestamp(new Date().getTime()).getTime();
        throwable = new Throwable();

        loggingEventDebug = new LoggingEvent("org.apache.log4j.Logger", logger, timeStamp, Level.toLevel(10000), "This is a DEBUG log", throwable);
        loggingEventInfo = new LoggingEvent("org.apache.log4j.Logger", logger, timeStamp, Level.toLevel(20000), "This is a INFO log", throwable);
        loggingEventError = new LoggingEvent("org.apache.log4j.Logger", logger, timeStamp, Level.toLevel(40000), "This is a ERROR log", throwable);
        loggingEventRootLogger = new LoggingEvent("org.apache.log4j.Logger", rootLogger, timeStamp, Level.toLevel(10000), "This is a DEBUG log", throwable);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void formatCorrectReturnStringTestWithDebugLevel() {
        String expected = "[main | DEBUG]: VelocityLayoutTestLogger | Occurred at: " + new Date(timeStamp).toString() + " | Log Message: This is a DEBUG log \n";
        String actual = velocityLayout.format(loggingEventDebug);

        assertEquals(expected, actual);
    }
    @Test
    public void formatCorrectReturnStringTestWithErrorLevel() {
        String expected = "[main | INFO]: VelocityLayoutTestLogger | Occurred at: " + new Date(timeStamp).toString() + " | Log Message: This is a INFO log \n";
        String actual = velocityLayout.format(loggingEventInfo);

        assertEquals(expected, actual);
    }
    @Test
    public void formatCorrectReturnStringTestWithInfoLevel() {
        String expected = "[main | ERROR]: VelocityLayoutTestLogger | Occurred at: " + new Date(timeStamp).toString() + " | Log Message: This is a ERROR log \n";
        String actual = velocityLayout.format(loggingEventError);

        assertEquals(expected, actual);
    }
    @Test
    public void formatCorrectReturnStringTestWithRootLogger() {
        String expected = "[main | DEBUG]: root | Occurred at: " + new Date(timeStamp).toString() + " | Log Message: This is a DEBUG log \n";
        String actual = velocityLayout.format(loggingEventRootLogger);

        assertEquals(expected, actual);
    }
    @Test
    public void passingPatternToVelocityLayoutTest() {
        velocityLayout = new VelocityLayout("[${t} | ${p}]: ${c}");

        String expected = "[main | DEBUG]: VelocityLayoutTestLogger";
        String actual = velocityLayout.format(loggingEventDebug);

        assertEquals(expected, actual);
    }
}