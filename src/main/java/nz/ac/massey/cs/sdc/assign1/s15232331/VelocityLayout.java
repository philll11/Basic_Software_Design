package nz.ac.massey.cs.sdc.assign1.s15232331;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.Properties;

public class VelocityLayout extends Layout {

    private VelocityEngine velocityEngine;
    private Template template;
    private VelocityContext velocityContext;
    private StringWriter stringWriter;
    private String pattern;

    public VelocityLayout(String _pattern) {
        this.velocityContext = new VelocityContext();
        this.stringWriter = new StringWriter();
        this.pattern = _pattern;
    }


    public String format(LoggingEvent loggingEvent) {
        Velocity.init();

        velocityContext.put("p", loggingEvent.getLevel());
        velocityContext.put("t", loggingEvent.getThreadName());
        velocityContext.put("c", loggingEvent.getLoggerName());
        velocityContext.put("d", loggingEvent.getTimeStamp());
        velocityContext.put("m", loggingEvent.getMessage());
        velocityContext.put("n", "\n");

        Velocity.evaluate(velocityContext, stringWriter, "PackageTemplateVelocity", pattern);

        return stringWriter.toString();
    }

    public boolean ignoresThrowable() {
        return false;
    }

    public void activateOptions() {

    }
}
