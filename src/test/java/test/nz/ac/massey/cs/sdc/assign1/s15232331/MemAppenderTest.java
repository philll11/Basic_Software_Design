/**
 * How do we test using different Appenders
 * In StressTest.java, do we have to include assertEquals statements for each test to be valid?
 * In StressTest.java, how do we reset MemAppender each time we run a test
 */


package test.nz.ac.massey.cs.sdc.assign1.s15232331;

import nz.ac.massey.cs.sdc.assign1.s15232331.MemAppender;
import nz.ac.massey.cs.sdc.assign1.s15232331.VelocityLayout;
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

public class MemAppenderTest {

    private MemAppender memAppenderWithArrayList, memAppenderWithLinkedList;
    private Logger logger, rootLogger;

    @Before
    public void setUp() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        memAppenderWithArrayList.clear(
                new ArrayList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );
        memAppenderWithLinkedList.clear(
                new LinkedList(), new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );

//        Field instance = MemAppender.class.getDeclaredField("single_instance");
//        instance.setAccessible(true);
//        instance.set(null, null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstanceTestToVerifyOnlyOneInstanceIsCreated() {
        MemAppender memAppenderFirstInstance = MemAppender.getInstance(
                new ArrayList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}"
                )
        );
        MemAppender memAppenderSecondInstance = MemAppender.getInstance(
                new ArrayList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}"
                )
        );

        assertEquals(memAppenderFirstInstance, memAppenderSecondInstance);
    }

    @Test
    public void linkedListCapabilitiesTest() {
        logger = Logger.getLogger("AppendLogger");
        memAppenderWithLinkedList = MemAppender.getInstance(
                new LinkedList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );

        logger.addAppender(memAppenderWithLinkedList);

        logger.warn("Warn Message");

        LinkedList<LoggingEvent> events = (LinkedList<LoggingEvent>) memAppenderWithLinkedList.getCurrentLogs();
        assertEquals("Warn Message", events.get(0).getMessage());
    }

    @Test
    public void correctLoggerLevelManagementTest() {
        logger = Logger.getLogger("AppendLogger");
        memAppenderWithArrayList = MemAppender.getInstance(
                new ArrayList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );

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
        memAppenderWithArrayList = MemAppender.getInstance(
                new ArrayList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );

        rootLogger.addAppender(memAppenderWithArrayList);

        rootLogger.warn("Warn Message");

        ArrayList<LoggingEvent> events = (ArrayList<LoggingEvent>) memAppenderWithArrayList.getCurrentLogs();

        assertEquals("root", events.get(0).getLoggerName());
    }

    @Test
    public void maxSizeListLimitCorrectImplementationTest() {
        logger = Logger.getLogger("AppendLogger");
        memAppenderWithArrayList = MemAppender.getInstance(
                new ArrayList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );

        logger.addAppender(memAppenderWithArrayList);

        for(int i = 0; i < 110; ++i) {
            logger.debug("Debug Message");
        }

        assertEquals(100, memAppenderWithArrayList.getCurrentLogs().size());
    }
}