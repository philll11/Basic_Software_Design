import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MemAppender memAppender = MemAppender.getInstance();

        Logger logger = Logger.getLogger("TEMP");



        memAppender.storeLogEntries(logger.warn("failed"));

    }
}
