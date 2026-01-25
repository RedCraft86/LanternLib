package com.redcraft86.lanternlib.configs;

import java.util.List;
import java.util.ArrayList;
import com.redcraft86.lanternlib.LanternLib;
import com.redcraft86.lanternlib.api.config.*;

public final class LogFilterCfg extends JsonConfig {
    public static LogFilterCfg INSTANCE = null;

    public static LogFilterCfg get() {
        if (INSTANCE == null) {
            INSTANCE = JsonConfig.createConfig(LogFilterCfg.class);
        }
        return INSTANCE;
    }

    public LogFilterCfg() {
        super(LanternLib.MOD_ID + "-logfilter");
    }

    @Config
    @Comment("-- NOTE: To disable this entirely, set false to booleans and clear out phrases and regexes -- //")
    @Comment()
    @Comment("Whether a shadow log should be printed alongside actual log outputs.")
    @Comment("Can be useful to look at the actual line that will get filtered before being logged.")
    public boolean printShadow = false;

    @Config
    @Comment("Whether System.out.print logs should be affected by the filters.")
    public boolean filterConsole = true;

    @Config
    @Comment("List of phrases to filter out logs.")
    public List<String> phrases = new ArrayList<>(List.of("Failed to process update information"));

    @Config
    @Comment("List of regular expressions to filter out logs. Supports partial matches.")
    public List<String> regexes = new ArrayList<>();
}
