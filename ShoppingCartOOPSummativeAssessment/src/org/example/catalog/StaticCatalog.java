package org.example.catalog;

import org.example.model.Item;

import java.util.List;

public class StaticCatalog implements Catalog{
    @Override
    public List<Item> getCatalogItems() {
        return List.of();
    }

    @Override
    public void addItemToCatalog(Item item) {

    }

    @Override
    public void removeItemFromCatalog(Item item) {

    }

    @Override
    public Item findItemByName(String itemName) {
        return null;
    }

    @Override
    public Item findItemBySKU(String sku) {
        return null;
    }
}
