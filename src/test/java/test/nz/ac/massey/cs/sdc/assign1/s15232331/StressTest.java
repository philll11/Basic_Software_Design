package test.nz.ac.massey.cs.sdc.assign1.s15232331;

import nz.ac.massey.cs.sdc.assign1.s15232331.MemAppender;
import nz.ac.massey.cs.sdc.assign1.s15232331.VelocityLayout;
import org.apache.log4j.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class StressTest {

    private MemAppender memAppenderWithArrayList, memAppenderWithLinkedList;
    private Logger logger;
    private VelocityLayout layout;

    @Before
    public void setUp() throws Exception {
        Field instance = MemAppender.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void linkedListStreetTest() {
        logger = Logger.getLogger("LinkedListAppendLogger");
        memAppenderWithLinkedList = MemAppender.getInstance(
                new LinkedList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );

        logger.addAppender(memAppenderWithLinkedList);

        for(int i = 0; i < 1000; ++i) {
            logger.warn("Warn Message");
        }
    }

    @Test
    public void arrayListStreetTest() {
        logger = Logger.getLogger("ArrayListAppendLogger");
        memAppenderWithLinkedList = MemAppender.getInstance(
                new ArrayList(),
                new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
        );

        logger.addAppender(memAppenderWithLinkedList);

        for(int i = 0; i < 1000; ++i) {
            logger.warn("Warn Message");
        }
    }

    @Test
    public void consoleAppenderStressTest() throws IOException {
        BasicConfigurator.configure();
        logger = Logger.getLogger("ConsoleAppendLogger");

        for(int i = 0; i < 1000; ++i) {
            logger.warn("Warn Message");
        }
    }

    @Test
    public void fileAppenderStressTest() throws IOException {
        logger = Logger.getLogger("fileAppendLogger");

        logger.addAppender(
                new FileAppender(
                        new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}"),
                        "src\\test\\resources\\logs.txt"
                )
        );

        for(int i = 0; i < 1000; ++i) {
            logger.warn("Warn Message");
        }
    }

    @Test
    public void patternLayoutStressTest() {
        BasicConfigurator.configure();
        logger = Logger.getLogger("ArrayListAppendLogger");

        logger.addAppender(
                new ConsoleAppender(
                        new PatternLayout("[%t | %p]: %c | Occurred at: %d | Log Message: %m %n")
                )
        );

        for(int i = 0; i < 1000; ++i) {
            logger.warn("Warn Message");
        }
    }

    @Test
    public void velocityLayoutStressTest() {
        BasicConfigurator.configure();
        logger = Logger.getLogger("ArrayListAppendLogger");

        logger.addAppender(
                new ConsoleAppender(
                        new VelocityLayout("[${t} | ${p}]: ${c} | Occurred at: ${d} | Log Message: ${m} ${n}")
                )
        );

        for(int i = 0; i < 1000; ++i) {
            logger.warn("Warn Message");
        }
    }

    @Test
    public void beforeMaxSizeIsReachedTest() {

    }

    @Test
    public void afterMaxSizeIsReachedTest() {

    }
}