package com.iafenvoy.thinkbeforedrop;

import com.iafenvoy.jupiter.config.container.AutoInitConfigContainer;
import com.iafenvoy.jupiter.config.entry.BooleanEntry;
import com.iafenvoy.jupiter.config.entry.DoubleEntry;
import com.iafenvoy.jupiter.config.entry.ListStringEntry;
import com.iafenvoy.jupiter.config.entry.SeparatorEntry;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class TBDConfig extends AutoInitConfigContainer {
    public static final TBDConfig INSTANCE = new TBDConfig();
    public final General general = new General();
    public final Rules rules = new Rules();

    public TBDConfig() {
        super(/*? >=1.21 {*/ResourceLocation.fromNamespaceAndPath/*?} else {*//*new ResourceLocation*//*?}*/(ThinkBeforeDrop.MOD_ID, "client"), "screen.thinkbeforedrop.client.title", "./config/thinkbeforedrop.json");
    }

    @SuppressWarnings("unused")
    public static class General extends AutoInitConfigCategoryBase {
        public final BooleanEntry enabled = BooleanEntry.builder("config.thinkbeforedrop.general.enabled", false).key("enabled").build();
        public final SeparatorEntry s1 = SeparatorEntry.builder().build();
        public final DoubleEntry minSecond = DoubleEntry.builder("config.thinkbeforedrop.general.minSecond", 0.5).key("minSecond").build();
        public final DoubleEntry maxSecond = DoubleEntry.builder("config.thinkbeforedrop.general.maxSecond", 5).key("maxSecond").build();

        public General() {
            super("general", "config.thinkbeforedrop.category.general");
        }
    }

    @SuppressWarnings("unused")
    public static class Rules extends AutoInitConfigCategoryBase {
        public final BooleanEntry weapon = BooleanEntry.builder("config.thinkbeforedrop.rules.weapon", false).key("weapon").build();
        public final BooleanEntry tool = BooleanEntry.builder("config.thinkbeforedrop.rules.tool", false).key("tool").build();
        public final BooleanEntry shulkerBox = BooleanEntry.builder("config.thinkbeforedrop.rules.shulkerBox", false).key("shulkerBox").build();
        public final BooleanEntry armor = BooleanEntry.builder("config.thinkbeforedrop.rules.armor", false).key("armor").build();
        public final BooleanEntry ore = BooleanEntry.builder("config.thinkbeforedrop.rules.ore", false).key("ore").build();
        public final BooleanEntry disc = BooleanEntry.builder("config.thinkbeforedrop.rules.disc", false).key("disc").build();
        public final BooleanEntry uncommon = BooleanEntry.builder("config.thinkbeforedrop.rules.uncommon", false).key("uncommon").build();
        public final BooleanEntry rare = BooleanEntry.builder("config.thinkbeforedrop.rules.rare", false).key("rare").build();
        public final BooleanEntry epic = BooleanEntry.builder("config.thinkbeforedrop.rules.epic", false).key("epic").build();
        public final BooleanEntry enchanted = BooleanEntry.builder("config.thinkbeforedrop.rules.enchanted", false).key("enchanted").build();
        public final BooleanEntry enchantedBook = BooleanEntry.builder("config.thinkbeforedrop.rules.enchantedBook", false).key("enchantedBook").build();
        public final BooleanEntry book = BooleanEntry.builder("config.thinkbeforedrop.rules.book", false).key("book").build();
        public final SeparatorEntry s1 = SeparatorEntry.builder().build();
        public final ListStringEntry customItems = ListStringEntry.builder("config.thinkbeforedrop.rules.customItems", List.of()).key("customItems").build();
        public final ListStringEntry excludeItems = ListStringEntry.builder("config.thinkbeforedrop.rules.excludeItems", List.of()).key("excludeItems").build();

        public Rules() {
            super("rules", "config.thinkbeforedrop.category.rules");
        }
    }
}
