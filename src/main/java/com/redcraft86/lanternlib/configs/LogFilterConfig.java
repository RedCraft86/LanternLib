package com.redcraft86.lanternlib.configs;

import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import net.minecraft.resources.ResourceLocation;

import me.fzzyhmstrs.fzzy_config.config.*;
import me.fzzyhmstrs.fzzy_config.annotations.*;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;

import com.redcraft86.lanternlib.LanternLib;

public class LogFilterConfig extends Config {
    public LogFilterConfig() {
        super(ResourceLocation.fromNamespaceAndPath(LanternLib.MOD_ID, "log-filter"));
    }

    @RequiresAction(action = Action.RESTART)
    @Comment("Global switch to enable/disable the log filter system as a whole.")
    public boolean enabled = true;

    @Comment("""
        Prints a shadow log alongside the actual log output.
        Can be used to see the raw line that will get filtered.
    """)
    public boolean printShadow = false;

    @Comment("If System.out.print logs should be affected by the filters.")
    public boolean filterConsole = true;

    @Comment("List of phrases to filter out logs.")
    public ValidatedList<String> phrases = new ValidatedString("phrase", "^(?!\\\\s*$).+").toList();

    @Comment("List of regular expressions to filter out logs. Supports partial matches.")
    public ValidatedList<String> regexes = new ValidatedString("expression", "^(?!\\\\s*$).+").toList();
}