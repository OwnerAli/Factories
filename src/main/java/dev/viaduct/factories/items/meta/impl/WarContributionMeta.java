package dev.viaduct.factories.items.meta.impl;

import dev.viaduct.factories.FactoriesPlugin;
import dev.viaduct.factories.utils.Chat;
import lombok.Setter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

@Setter
public class WarContributionMeta extends DescriptionMeta {

    public static final NamespacedKey CONTRIBUTION_KEY = new NamespacedKey(FactoriesPlugin.getInstance(), "contribution");

    private int contribution;

    public WarContributionMeta(int contribution) {
        this.contribution = contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
        setDescription(List.of(Chat.colorize("&6Contribution: &e" + contribution)));
    }

    @Override
    public void modifyItem(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer()
                .set(CONTRIBUTION_KEY, PersistentDataType.INTEGER, contribution);
        itemStack.setItemMeta(itemMeta);
        super.modifyItem(itemStack);
    }

}