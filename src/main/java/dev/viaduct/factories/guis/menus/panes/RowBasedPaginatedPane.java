package dev.viaduct.factories.guis.menus.panes;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class RowBasedPaginatedPane extends PaginatedPane {

    private final Map<Integer, PaginatedPane> rowPanes = new HashMap<>();
    private final int rowCount;

    /**
     * Creates a new row-based paginated pane
     *
     * @param x starting x position
     * @param y starting y position
     * @param length length of each row
     * @param rows number of rows
     */
    public RowBasedPaginatedPane(int x, int y, int length, int rows) {
        super(x, y, length, rows);
        this.rowCount = rows;

        // Initialize individual row panes
        for (int i = 0; i < rows; i++) {
            rowPanes.put(i, new PaginatedPane(x, y + i, length, 1));
        }
    }

    /**
     * Populates items into specific rows based on a row selector function
     *
     * @param items list of items to populate
     * @param rowSelector function to determine which row an item belongs to (0-based index)
     */
    public void populateItemsByRow(List<GuiItem> items, Function<GuiItem, Integer> rowSelector) {
        // Sort items by row
        Map<Integer, List<GuiItem>> itemsByRow = new HashMap<>();

        for (GuiItem item : items) {
            int row = rowSelector.apply(item);
            if (row >= 0 && row < rowCount) {
                itemsByRow.computeIfAbsent(row, k -> new ArrayList<>()).add(item);
            }
        }

        // Populate each row's pane
        itemsByRow.forEach((row, rowItems) -> {
            PaginatedPane rowPane = rowPanes.get(row);
            if (rowPane != null) {
                rowPane.populateWithGuiItems(rowItems);
            }
        });
    }

    /**
     * Sets the current page for all rows
     *
     * @param page the page number to set
     */
    @Override
    public void setPage(int page) {
        super.setPage(page);
        rowPanes.values().forEach(pane -> pane.setPage(page));
    }

    /**
     * Gets the maximum number of pages across all rows
     *
     * @return the maximum page count
     */
    @Override
    public int getPages() {
        return rowPanes.values().stream()
                .mapToInt(PaginatedPane::getPages)
                .max()
                .orElse(0);
    }

    /**
     * Adds all row panes to the given GUI
     *
     * @param gui the GUI to add the panes to
     */
    public void addToGui(ChestGui gui) {
        rowPanes.values().forEach(gui::addPane);
    }

    /**
     * Gets the pane for a specific row
     *
     * @param row the row number (0-based)
     * @return the PaginatedPane for that row
     */
    public PaginatedPane getRowPane(int row) {
        return rowPanes.get(row);
    }

}