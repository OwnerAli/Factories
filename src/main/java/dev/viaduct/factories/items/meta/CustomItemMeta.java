package dev.viaduct.factories.items.meta;

public interface CustomItemMeta {

    /**
     * Applies the metadata logic to a given context.
     *
     * @param context The context for the metadata, e.g., an event or a player action.
     * @return true if the condition passes, false otherwise.
     */
    boolean apply(Object context);

}
