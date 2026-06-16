package com.redcraft86.lanternlib;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.io.PrintStream;

import com.mojang.logging.LogUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import com.redcraft86.lanternlib.configs.Configs;

public class LogFilter extends AbstractFilter implements Filter {
    public static final class SystemPrintFilter extends PrintStream {
        public SystemPrintFilter() {
            super(System.out);
        }

        @Override
        public void println(String x) {
            if (x != null && (x.startsWith(SHADOW_PREFIX) || canShowLog(x))) {
                super.println(x);
            }
        }

        @Override
        public void print(String s) {
            if (s != null && (s.startsWith(SHADOW_PREFIX) || canShowLog(s))) {
                super.print(s);
            }
        }
    }

    private static final List<Pattern> PATTERNS = new ArrayList<>();
    private static final String SHADOW_PREFIX = "[Shadowed]: ";
    private static String LAST_SHADOW_LOG = "";
    private static LogFilter INSTANCE = null;

    public static LogFilter getOrInit() {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        if (Configs.LOG_FILTER.enabled) {
            INSTANCE = new LogFilter();
            LogUtils.getLogger().info("[LanternLib] LogFilter initialized!");
        }

        return INSTANCE;
    }

    private LogFilter() {
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

        if (Configs.LOG_FILTER.filterConsole) {
            System.setOut(new SystemPrintFilter());
        }

        for (String regex : Configs.LOG_FILTER.regexes) {
            PATTERNS.add(Pattern.compile(regex));
        }
    }

    private static boolean canShowLog(String logString) {
        if (Configs.LOG_FILTER.printShadow && !logString.equals(LAST_SHADOW_LOG)) {
            System.out.println(SHADOW_PREFIX + logString);
            LAST_SHADOW_LOG = logString;
        }

        for (String phrase : Configs.LOG_FILTER.phrases) {
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

    @Override
    public boolean isLoggable(LogRecord record) {
        return canShowLog(String.format("[%s]: %s", record.getLoggerName(), record.getMessage()));
    }

    @Override
    public Result filter(LogEvent event) {
        String msg = String.format("[%s]: %s", event.getLoggerName(), event.getMessage().getFormattedMessage());
        return canShowLog(msg) ? Result.NEUTRAL : Result.DENY;
    }
}
