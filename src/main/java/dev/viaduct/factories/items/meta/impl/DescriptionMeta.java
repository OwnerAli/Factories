package dev.viaduct.factories.items.meta.impl;

import dev.viaduct.factories.items.meta.CustomItemMeta;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Setter
public class DescriptionMeta implements CustomItemMeta {

    private List<String> description;

    public DescriptionMeta(String... description) {
        this.description = new ArrayList<>(List.of(description));
    }

    protected void addDescriptionLine(String line) {
        this.description.add(line);
    }

    @Override
    public void modifyItem(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();

        if (lore == null) {
            lore = new ArrayList<>();
            lore.add(" ");
            lore.addAll(description);
            itemMeta.setLore(lore);
        } else {
            lore.add(" ");
            lore.addAll(description);
        }
        itemStack.setItemMeta(itemMeta);
    }

}
