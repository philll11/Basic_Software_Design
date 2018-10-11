

// Should maxSize have the option to be set by the user?
// How do we test our code?
// Is getInstance() meant to be static


import org.apache.log4j.spi.LoggingEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * The MemAppender class provides logger functionality that captures system
 * exceptions in a concise and manageable manner
 */
public class MemAppender {
    private static MemAppender single_instance = null;
    private List logEntries;
    private int maxSize;
    private int numberOfDiscardedLogs;

//  WriterAppender appender = new WriterAppender();
//  Writer writer = new StringWriter();
//  appender.setWriter(writer);
//  appender.setLayout(new SimpleLayout());
//  Logger.getRootLogger().addAppender(appender);

    private MemAppender() {
        this.logEntries = new ArrayList();
        this.maxSize = 100;
        this.numberOfDiscardedLogs = 0;
    }

    /**
     * Creates an instance of MemAppender and returns it.
     * If an instance already exists, then the current existing instance is returned     *
     * @return an instance of MemAppender
     */
    public static MemAppender getInstance() {
        if (single_instance == null) {
            single_instance = new MemAppender();
        }
        return single_instance;
    }



    public void storeLogEntries(LoggingEvent event) {
        if(logEntries.size() >= maxSize) {
            logEntries.remove(0);
            numberOfDiscardedLogs += 1;
        }
        logEntries.add(event);
    }



    /**
     * Returns the class property logEntries as a list of log enteries.
     * @return a list of log entries
     */
    public List getCurrentLogs() {
        return logEntries;
    }

    /**
     * Returns the number of discarded logs that have been discarded because the number
     * of logs contained in logEntries has reached the value contained in maxSize
     * @return the number of discarded logs as an int
     */
    public int getDiscardedLogCount() {
        return numberOfDiscardedLogs;
    }


}
