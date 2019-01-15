package frc.team4159.robot.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {
    public String format(LogRecord rec) {
        StringBuilder buf = new StringBuilder(1000);

        if (rec.getLevel().toString().equals("CONFIG")) {
            buf.append(rec.getMessage());
        } else {
            buf.append(rec.getLevel());
            buf.append(',');
            buf.append(rec.getMillis());
            buf.append(',');
            buf.append(rec.getMessage());
        }

        buf.append('\n');

        return buf.toString();
    }
}
