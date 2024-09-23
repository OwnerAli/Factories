package dev.viaduct.factories.blueprints.progress.impl;

import dev.viaduct.factories.blueprints.progress.BlueprintProgress;
import dev.viaduct.factories.blueprints.progress.Progressable;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemStackProgressable extends Progressable {

    private final ItemStack itemStack;

    public ItemStackProgressable(ItemStack itemStack) {
        super(itemStack.getAmount(), ClickType.LEFT);
        this.itemStack = itemStack;
    }

    @Override
    public void progress(InventoryClickEvent event, BlueprintProgress blueprintProgress, Map<Progressable, Integer> progressMap) {
        if (event.getCursor() == null) return;

        if (!event.getCursor().isSimilar(itemStack)) return;
        ItemStack cursor = event.getCursor();

        int cursorAmount = cursor.getAmount();

        Integer currentProgress = progressMap.get(this);
        if (currentProgress == null) currentProgress = 0;

        int amountNeeded = Math.min(cursorAmount, itemStack.getAmount() - currentProgress);

        progressMap.put(this, currentProgress + amountNeeded);
        cursor.setAmount(cursorAmount - amountNeeded);
        super.progress(event, blueprintProgress, progressMap);
    }

    @Override
    public List<String> getDescription(double progress) {
        return List.of((itemStack.hasItemMeta() ? itemStack.getItemMeta().getDisplayName() : itemStack.getType().name()) + " &f» " +
                (int) progress + "/" + itemStack.getAmount());
    }

}
