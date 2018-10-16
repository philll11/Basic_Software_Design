/**
 * Is getInstanceTestToVerifyOnlyOneInstanceIsCreated() correctly evaluating whether the when MemAppenders are the same object?
 * How do we test using different Appenders
 */


package test.nz.ac.massey.cs.sdc.assign1.s15232331;

import nz.ac.massey.cs.sdc.assign1.s15232331.MemAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.RootLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MemAppenderTest {

    private MemAppender memAppenderWithArrayList, memAppenderWithLinkedList;
    private Logger logger, rootLogger;

    @Before
    public void setUp() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = MemAppender.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstanceTestToVerifyOnlyOneInstanceIsCreated() {
        MemAppender memAppenderFirstInstance = MemAppender.getInstance(new ArrayList(), "[${t} | ${p}]: ${c} | Occured at: ${d} | Log Message: ${m} ${n}");
        MemAppender memAppenderSecondInstance = MemAppender.getInstance(new ArrayList(), "[${t} | ${p}]: ${c} | Occured at: ${d} | Log Message: ${m} ${n}");

        int hc1 = memAppenderFirstInstance.hashCode();
        int hc2 = memAppenderFirstInstance.hashCode();

        assertTrue(memAppenderFirstInstance.equals(memAppenderSecondInstance)
                && memAppenderFirstInstance.hashCode() == memAppenderSecondInstance.hashCode());
    }

    @Test
    public void linkedListCapabilitiesTest() {
        logger = Logger.getLogger("AppendLogger");
        memAppenderWithLinkedList = MemAppender.getInstance(new LinkedList(), "[${t} | ${p}]: ${c} | Occured at: ${d} | Log Message: ${m} ${n}");

        logger.addAppender(memAppenderWithLinkedList);

        logger.warn("Warn Message");

        LinkedList<LoggingEvent> events = (LinkedList<LoggingEvent>) memAppenderWithLinkedList.getCurrentLogs();
        assertEquals("Warn Message", events.get(0).getMessage());
    }

    @Test
    public void correctLoggerLevelManagementTest() {
        logger = Logger.getLogger("AppendLogger");
        memAppenderWithArrayList = MemAppender.getInstance(new ArrayList(), "[${t} | ${p}]: ${c} | Occured at: ${d} | Log Message: ${m} ${n}");

        logger.addAppender(memAppenderWithArrayList);
        logger.setLevel(Level.WARN);

        logger.trace("Trace Message");
        logger.debug("Debug Message");
        logger.info("Info Message");
        logger.warn("Warn Message");
        logger.error("Error Message");
        logger.fatal("Fatal Message");

        String[] testStrings = {"Warn Message", "Error Message", "Fatal Message"};
        int i = 0;
        for(LoggingEvent event: memAppenderWithArrayList.getCurrentLogs()) {
            assertEquals(testStrings[i], event.getMessage());
            ++i;
        }

    }

    @Test
    public void rootLoggerCapabilitiesTest() {
        rootLogger = RootLogger.getRootLogger();
        memAppenderWithArrayList = MemAppender.getInstance(new ArrayList(), "[${t} | ${p}]: ${c} | Occured at: ${d} | Log Message: ${m} ${n}");

        rootLogger.addAppender(memAppenderWithArrayList);

        rootLogger.warn("Warn Message");

        ArrayList<LoggingEvent> events = (ArrayList<LoggingEvent>) memAppenderWithArrayList.getCurrentLogs();

        assertEquals("root", events.get(0).getLoggerName());

    }

    @Test
    public void getCurrentLogs() {
    }

    @Test
    public void getDiscardedLogCount() {
    }
}