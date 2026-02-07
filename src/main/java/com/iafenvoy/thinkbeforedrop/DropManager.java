package com.iafenvoy.thinkbeforedrop;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public final class DropManager {
    private static long lastDropTime = 0;
    private static int lastSlot = -1;
    private static boolean dropped = false;

    private static boolean shouldHandleDrop(ItemStack stack) {
        TBDConfig config = TBDConfig.INSTANCE;
        if (!config.general.enabled.getValue()) return false;
        if (DropRules.stackInList(config.rules.excludeItems.getValue(), stack)) return false;
        for (Map.Entry<BooleanSupplier, Predicate<ItemStack>> entry : DropRules.allRules())
            if (entry.getKey().getAsBoolean() && entry.getValue().test(stack))
                return true;
        return DropRules.stackInList(config.rules.customItems.getValue(), stack);
    }

    public static boolean shouldThrow(ItemStack stack, int slot) {
        TBDConfig config = TBDConfig.INSTANCE;
        if (slot != lastSlot) {
            lastDropTime = 0;
            dropped = false;
        }
        if (!shouldHandleDrop(stack) || dropped) return true;
        long now = System.currentTimeMillis();
        if (now - lastDropTime >= config.general.minSecond.getValue() * 1000 && now - lastDropTime <= config.general.maxSecond.getValue() * 1000) {
            if (stack.getCount() != 1)
                dropped = true;
            lastDropTime = 0;
            return true;
        }
        lastDropTime = now;
        lastSlot = slot;
        return false;
    }

    public static Component getWarningText() {
        return Component.translatable("thinkbeforedrop.warning");
    }
}
