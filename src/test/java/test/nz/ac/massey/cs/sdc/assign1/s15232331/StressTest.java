package test.nz.ac.massey.cs.sdc.assign1.s15232331;

import nz.ac.massey.cs.sdc.assign1.s15232331.MemAppender;
import nz.ac.massey.cs.sdc.assign1.s15232331.VelocityLayout;
import org.apache.log4j.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class StressTest {

    private final String LOG_CLASS_PATH = "src\\test\\resources\\logs.txt";

    private Logger logger;

    @Before
    public void setUp() throws Exception {
        Thread.sleep(5_000);
        Field instance = MemAppender.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void linkedListStreetTest() throws InterruptedException {
        logger = Logger.getLogger("LinkedListAppendLogger");

        logger.addAppender(
                MemAppender.getInstance(
                        new LinkedList()
                )
        );

        for(int i = 0; i < 100; ++i) {
            logger.warn("linkedListStreetTest");
        }
    }

    @Test
    public void arrayListStreetTest() {
        logger = Logger.getLogger("ArrayListAppendLogger");

        logger.addAppender(
                MemAppender.getInstance(
                        new LinkedList()
                )
        );

        for(int i = 0; i < 100; ++i) {
            logger.warn("arrayListStreetTest");
        }
    }

    @Test
    public void consoleAppenderStressTest() throws IOException {
        BasicConfigurator.configure();
        logger = Logger.getLogger("ConsoleAppendLogger");

        for(int i = 0; i < 100; ++i) {
            logger.warn("consoleAppenderStressTest");
        }
    }

    @Test
    public void fileAppenderStressTest() throws IOException {
        PrintWriter writer = new PrintWriter(LOG_CLASS_PATH);
        writer.print("");
        writer.close();

        logger = Logger.getLogger("fileAppendLogger");

        logger.addAppender(
                new FileAppender(
                        new VelocityLayout(),
                        LOG_CLASS_PATH
                )
        );

        for(int i = 0; i < 100; ++i) {
            logger.warn("fileAppenderStressTest");
        }
    }

    @Test
    public void patternLayoutStressTest() {
        BasicConfigurator.configure();
        logger = Logger.getLogger("ArrayListAppendLogger");

        logger.addAppender(
                new ConsoleAppender(
                        new PatternLayout()
                )
        );

        for(int i = 0; i < 100; ++i) {
            logger.warn("patternLayoutStressTest");
        }
    }

    @Test
    public void velocityLayoutStressTest() {
        BasicConfigurator.configure();
        logger = Logger.getLogger("ArrayListAppendLogger");

        logger.addAppender(
                new ConsoleAppender(
                        new VelocityLayout()
                )
        );

        for(int i = 0; i < 100; ++i) {
            logger.warn("velocityLayoutStressTest");
        }
    }

}