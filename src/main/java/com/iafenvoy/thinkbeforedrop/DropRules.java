package com.iafenvoy.thinkbeforedrop;

import com.iafenvoy.jupiter.config.entry.BooleanEntry;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;

import java.util.*;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

public final class DropRules {
    private static final Map<BooleanSupplier, Predicate<ItemStack>> RULES = new LinkedHashMap<>();

    public static Set<Map.Entry<BooleanSupplier, Predicate<ItemStack>>> allRules() {
        return RULES.entrySet();
    }

    public static void registerStack(BooleanEntry entry, Predicate<ItemStack> predicate) {
        RULES.put(entry::getValue, predicate);
    }

    public static void registerItem(BooleanEntry entry, Predicate<Item> predicate) {
        RULES.put(entry::getValue, stack -> predicate.test(stack.getItem()));
    }

    public static boolean stackInList(List<String> itemOrTag, ItemStack stack) {
        //Either<ITEM,TAG>
        return itemOrTag.stream().<Either<String, String>>map(x -> x.startsWith("#") ? Either.right(x.substring(1)) : Either.left(x))
                .map(x -> x.mapBoth(ResourceLocation::tryParse, ResourceLocation::tryParse))
                .filter(x -> x.map(Objects::nonNull, Objects::nonNull))
                .map(x -> x.mapBoth(BuiltInRegistries.ITEM::getValue, rl -> TagKey.create(Registries.ITEM, rl)))
                .anyMatch(x -> x.map(stack::is, stack::is));
    }

    static {
        TBDConfig.Rules rules = TBDConfig.INSTANCE.rules;
        registerStack(rules.weapon, stack -> stack.is(ItemTags.SWORD_ENCHANTABLE) || stack.is(ItemTags.BOW_ENCHANTABLE) || stack.is(ItemTags.CROSSBOW_ENCHANTABLE) || stack.is(ItemTags.TRIDENT_ENCHANTABLE) || stack.is(ItemTags.ARROWS) || stack.is(ItemTags.MACE_ENCHANTABLE));
        registerStack(rules.tool, stack -> stack.is(ItemTags.AXES) || stack.is(ItemTags.PICKAXES) || stack.is(ItemTags.SHOVELS) || stack.is(ItemTags.HOES));
        registerStack(rules.shulkerBox, stack -> stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock);
        registerStack(rules.armor, stack -> stack.has(DataComponents.EQUIPPABLE));
        // Only ores and sculk use DropExperienceBlock
        registerStack(rules.ore, stack -> stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof DropExperienceBlock && !(blockItem.getBlock() instanceof SculkBlock));
        registerStack(rules.disc, stack -> stack.getItem() instanceof DiscFragmentItem || stack.has(DataComponents.JUKEBOX_PLAYABLE));
        registerStack(rules.uncommon, stack -> stack.get(DataComponents.RARITY) == Rarity.UNCOMMON);
        registerStack(rules.rare, stack -> stack.get(DataComponents.RARITY) == Rarity.RARE);
        registerStack(rules.epic, stack -> stack.get(DataComponents.RARITY) == Rarity.EPIC);
        registerStack(rules.enchanted, ItemStack::isEnchanted);
        registerStack(rules.enchantedBook, stack -> stack.has(DataComponents.STORED_ENCHANTMENTS));
        registerItem(rules.book, item -> item instanceof WritableBookItem || item instanceof WrittenBookItem);
    }
}
