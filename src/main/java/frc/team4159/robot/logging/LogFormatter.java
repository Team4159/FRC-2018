package frc.team4159.robot.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
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
            buf.append(calcDate(rec.getMillis()));
            buf.append(',');
            buf.append(rec.getMessage());
        }

        buf.append('\n');

        return buf.toString();
    }

    static String calcDate(long millisecs) {
        SimpleDateFormat date_format = new SimpleDateFormat("MM_dd_HH_mm_ss");
        Date resultDate = new Date(millisecs);
        return date_format.format(resultDate);
    }
}
