package com.redcraft86.lanternlib.configs;

import java.util.List;

import com.redcraft86.lanternlib.LanternLib;
import com.redcraft86.lanternlib.utils.JsonConfig;

public class LogFilterCfg extends JsonConfig {
    public static LogFilterCfg INSTANCE = null;

    public static boolean PRINT_SHADOW = false;
    public static boolean FILTER_CONSOLE = true;
    public static List<String> PHRASES = null;
    public static List<String> REGEX = null;

    private LogFilterCfg() {
        super(LanternLib.MOD_ID + "-logfilter");
    }

    public static LogFilterCfg getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LogFilterCfg();
        }
        return INSTANCE;
    }

    @Override
    protected void saveData() {
        setBoolValue("printShadow", PRINT_SHADOW);
        setBoolValue("filterConsole", FILTER_CONSOLE);
        setStringArray("phrases", PHRASES);
        setStringArray("regex", REGEX);
    }

    @Override
    protected void loadData() {
        PRINT_SHADOW = getBoolValue("printShadow", false);
        FILTER_CONSOLE = getBoolValue("filterConsole", true);
        PHRASES = getStringArray("phrases", List.of("Failed to process update information"));
        REGEX = getStringArray("regex", List.of());
    }
}
