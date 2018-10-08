# Basic_Software_Design

This application was built as a submission for an assignment in my Software Engineering Design and Construction (159.251) paper.
The assignment required us to construct and store logging information, implement Velocity pattern layout, test our application and write a Maven build script.
Below is a more detailed look into what was required for each task.

# Assignment Specification
1.  Implement a log4j appender (MemAppender.java).
    a.  MemAppender stores all log entries in a list, they are not printed at the console.
    b.  There can be only one instance of MemAppender, this is enforced by using the Singleton pattern.
    c.  logs can be accessed using the following non-static method:
            java.util.List<org.apache.log4j.spi.LoggingEvent>
            MemAppender.getCurrentLogs()
    d.  The list returned by getCurrentLogs() must not be modifiable.
    e.  MemAppender has a property maxSize, if the number of logs reaches maxSize, the oldest logs are discarded. The number of discarded logs is counted, and this count can be accessed using the getDiscardedLogCount() method in MemAppender that returns this count as a long.
    f.  The constructor of MemAppender can be used to set the list to be used to store logs.
2.  Implement a layout (VelocityLayout.java)
    a.  VelocityLayout basically works like PatternLayout, but uses Velocity as a template engine
    b.  Variable to be supported:
        (category)
        (using the default toString() representation)
        (message)
        (priority)
        (thread)
        (line separator)
    c.  This means that the variable syntax is different, e.g. use @{m} instead of %m
3.  Write tests that test your appender and layout in combination with different loggers, levels and appenders.
    a.  Use JUnit4 for testing.
    b.  Aim for good test coverage and precise asserts.
    c.  Tests should be in the package.
4.  Write tests to stress-test your appender/layout by creating a large amount of log statements.
    a.  These tests are methods in a test class StressTest.java
    b.  Use these tests to compare the performance between MemAppender using a LinkedList, MemAppender using an ArrayList, ConsoleAppender and FileAppender - measure time and memory consumption (using JConsole or VisualVM or any profiler).
    c.  Use these scripts to compare the performance between PatternLayout and VelocityLayout.
    d.  Stress tests should test performance before and after maxSize has been reached.
    e.  Write a short report summarising your findings (embed screenshots of memory usage charts in this reports taken from VisualVM).
    f.  The report name should be performance-analysis.pdf.
5.  Write an Maven build script.
    a.  The Maven script should be used the build the project including compiling, testing, measuring test coverage, dependency analysis.
    b.  Use the jacoco Maven plugin for measuring test coverage.
    c.  Use the jdepend Maven plugin for dependency analysis.

