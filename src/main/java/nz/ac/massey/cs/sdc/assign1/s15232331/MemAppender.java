package nz.ac.massey.cs.sdc.assign1.s15232331;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import java.util.List;

/**
 * The MemAppender class provides logger functionality that captures system
 * exceptions in a concise and manageable manner
 */
public class MemAppender extends AppenderSkeleton {
    private final int MAX_SIZE = 100;

    private static MemAppender single_instance = null;
    private List<LoggingEvent> logEntries;
    private int numberOfDiscardedLogs;

    private MemAppender(List _list) {
        this.logEntries = _list;
        this.numberOfDiscardedLogs = 0;
    }
    private MemAppender(List _list, Layout _layout) {
        this.logEntries = _list;
        this.layout = _layout;
        this.numberOfDiscardedLogs = 0;
    }

    /**
     * Creates an instance of MemAppender and returns it.
     * If an instance already exists, then the current existing instance is returned     *
     * @return an instance of MemAppender
     */
    public static MemAppender getInstance(List _list) {
        if (single_instance == null) {
            single_instance = new MemAppender(_list);
        }
        return single_instance;
    }

    protected void append(LoggingEvent loggingEvent) {
        if(logEntries.size() >= MAX_SIZE) {
            logEntries.remove(0);
            numberOfDiscardedLogs += 1;
        }
        logEntries.add(loggingEvent);
    }



    /**
     * Returns the class property logEntries as a list of log enteries.
     * @return a list of log entries
     */
    public List<LoggingEvent> getCurrentLogs() {
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


    public void close() {

    }

    public boolean requiresLayout() {
        return false;
    }
}
