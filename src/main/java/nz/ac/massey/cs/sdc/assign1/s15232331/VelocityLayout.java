package nz.ac.massey.cs.sdc.assign1.s15232331;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

public class VelocityLayout extends Layout {

    private VelocityEngine velocityEnginee;
    private Template template;
    private VelocityContext velocityContext;
    private StringWriter stringWriter;
    private String pattern;

    public VelocityLayout(String _pattern) {
        this.velocityEnginee = new VelocityEngine();
        this.velocityContext = new VelocityContext();
        this.stringWriter = new StringWriter();
        this.pattern = _pattern;
    }


    public String format(LoggingEvent loggingEvent) {
        velocityEnginee.init();

        Template template = velocityEnginee.getTemplate(pattern);

        velocityContext.put("c", loggingEvent.getLoggerName());
        velocityContext.put("d", loggingEvent.getTimeStamp());
        velocityContext.put("m", loggingEvent.getMessage());
        velocityContext.put("p", loggingEvent.getLevel());
        velocityContext.put("t", loggingEvent.getThreadName());
        velocityContext.put("n", " ");

        template.merge(velocityContext, stringWriter);

        return stringWriter.toString();
    }

    public boolean ignoresThrowable() {
        return false;
    }

    public void activateOptions() {

    }
}
