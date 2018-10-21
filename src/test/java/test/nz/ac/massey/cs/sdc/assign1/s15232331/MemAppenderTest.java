package test.nz.ac.massey.cs.sdc.assign1.s15232331;

import nz.ac.massey.cs.sdc.assign1.s15232331.MemAppender;
import nz.ac.massey.cs.sdc.assign1.s15232331.VelocityLayout;
import org.apache.log4j.*;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.RootLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class MemAppenderTest {

    private final String LOG_CLASS_PATH = "src\\test\\resources\\logs.txt";

    private MemAppender memAppenderWithArrayList;
    private Logger logger, rootLogger;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field instance = MemAppender.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @After
    public void tearDown() {
        memAppenderWithArrayList = null;
        logger = null;
        rootLogger = null;
    }

    @Test
    public void getInstanceTestToVerifyOnlyOneInstanceIsCreated() {
        MemAppender memAppenderFirstInstance = MemAppender.getInstance(
                new ArrayList()
        );
        MemAppender memAppenderSecondInstance = MemAppender.getInstance(
                new ArrayList()
        );

        assertEquals(memAppenderFirstInstance, memAppenderSecondInstance);
    }

    @Test
    public void linkedListCapabilitiesTest() {
        logger = Logger.getLogger("AppendLogger");
        MemAppender memAppenderWithLinkedList = MemAppender.getInstance(
                new LinkedList()
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
                new ArrayList()
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
                new ArrayList()
        );
        rootLogger.addAppender(memAppenderWithArrayList);

        rootLogger.warn("Warn Message");

        ArrayList<LoggingEvent> events = (ArrayList<LoggingEvent>) memAppenderWithArrayList.getCurrentLogs();

        assertEquals("root", events.get(0).getLoggerName());
    }

    @Test
    public void velocityLayoutWithConsoleAppenderTest() throws IOException {
        PrintWriter writer = new PrintWriter(LOG_CLASS_PATH);
        writer.print("");
        writer.close();

        logger = Logger.getLogger("AppendLogger");
        logger.addAppender(
                new FileAppender(
                        new VelocityLayout("Log Message: ${m}"),
                        LOG_CLASS_PATH
                )
        );

        logger.warn("Velocity Layout With Console Appender Test");

        BufferedReader br = new BufferedReader(
                new FileReader(LOG_CLASS_PATH)
        );
        String firstLine = br.readLine();

        assertEquals("Log Message: Velocity Layout With Console Appender Test", firstLine);

    }

    @Test
    public void maxSizeListLimitCorrectImplementationTest() {
        logger = Logger.getLogger("AppendLogger");
        memAppenderWithArrayList = MemAppender.getInstance(
                new LinkedList()
        );
        logger.addAppender(memAppenderWithArrayList);

        for(int i = 0; i < 110; ++i) {
            logger.debug("Debug Message");
        }

        assertEquals(100, memAppenderWithArrayList.getCurrentLogs().size());
    }
}