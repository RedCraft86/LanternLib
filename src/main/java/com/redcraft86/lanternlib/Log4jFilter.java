package com.redcraft86.lanternlib;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.io.PrintStream;

import com.redcraft86.lanternlib.configs.LogFilterCfg;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.AbstractFilter;

public final class Log4jFilter extends AbstractFilter implements Filter {
    private static final List<Pattern> PATTERNS = new ArrayList<>();
    private static final String SHADOW_LOG = "[Shadowed]: ";
    private static Log4jFilter INSTANCE = null;
    private static String LAST_SHADOW = "";

    private Log4jFilter() {
        java.util.logging.Logger.getLogger("").setFilter(this);
        ((Logger)LogManager.getRootLogger()).addFilter(this);

        List<LoggerConfig> offshootLoggers = new ArrayList<>();
        LoggerContext logContext = (LoggerContext)LogManager.getContext(false);
        Map<String, LoggerConfig> map = logContext.getConfiguration().getLoggers();
        for (LoggerConfig logger : map.values()) {
            if (!offshootLoggers.contains(logger)) {
                offshootLoggers.add(logger);
                logger.addFilter(this);
            }
        }

        if (LogFilterCfg.FILTER_CONSOLE) {
            System.setOut(new SystemPrintFilter(System.out));
        }

        for (String regex : LogFilterCfg.REGEX) {
            PATTERNS.add(Pattern.compile(regex));
        }
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return canShowLog(String.format("[%s]: %s", record.getLoggerName(), record.getMessage()));
    }

    @Override
    public Result filter(LogEvent event) {
        String msg = String.format("[%s]: %s", event.getLoggerName(), event.getMessage().getFormattedMessage());
        return canShowLog(msg) ? Result.NEUTRAL : Result.DENY;
    }

    public static Log4jFilter getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        LogFilterCfg.getInstance();
        if (LogFilterCfg.PRINT_SHADOW || !LogFilterCfg.PHRASES.isEmpty() || !LogFilterCfg.REGEX.isEmpty()) {
            INSTANCE = new Log4jFilter();
        }
        return INSTANCE;
    }

    private static boolean canShowLog(String logString) {
        if (LogFilterCfg.INSTANCE == null) {
            return true;
        }

        if (LogFilterCfg.PRINT_SHADOW && !logString.equals(LAST_SHADOW)) {
            System.out.println(SHADOW_LOG + logString);
            LAST_SHADOW = logString;
        }

        for (String phrase : LogFilterCfg.PHRASES) {
            if (logString.contains(phrase)) {
                return false;
            }
        }

        for (Pattern pattern : PATTERNS) {
            if (pattern.matcher(logString).find()) {
                return false;
            }
        }

        return true;
    }

    public static final class SystemPrintFilter extends PrintStream {
        public SystemPrintFilter(PrintStream stream) {
            super(stream);
        }

        @Override
        public void println(String x) {
            if (x != null && (x.startsWith(SHADOW_LOG) || canShowLog(x))) {
                super.println(x);
            }
        }

        @Override
        public void print(String s) {
            if (s != null && (s.startsWith(SHADOW_LOG) || canShowLog(s))) {
                super.print(s);
            }
        }
    }
}
