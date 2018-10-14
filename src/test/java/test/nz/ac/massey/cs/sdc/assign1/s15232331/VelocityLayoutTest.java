package test.nz.ac.massey.cs.sdc.assign1.s15232331;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.RootLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;


public class VelocityLayoutTest {

    private Logger logger;
    private Level level;
    private LoggingEvent loggingEvent;

    @Before
    public void setUp() throws Exception {
        logger = Logger.getLogger("VelocityLayoutTestLogger");
        level = Level.toLevel(1);
        loggingEvent = new LoggingEvent("org.apache.log4j.Logger", logger, level, );
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void format() {
    }
}