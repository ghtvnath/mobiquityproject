package com.mobiquityinc.pojo;


import java.util.ArrayList;
import java.util.List;

/**
 * Package class represents a parcel and list of items that is
 * considered to be placed inside that parcel.
 *
 * It has also the maximumWeight variable which is declared as final
 * as it cannot be changed after initialized.
 */
public class Package {
    private final int maximumWeight;
    List<Item> itemList;

    // Package will be allowed to construct using maximumWeight as the input.
    // maximum weight of the package cannot be changed after the package is created
    public Package(int maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public int getMaximumWeight() {
        return maximumWeight;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void addItem(Item item) {
        if (itemList == null) {
            itemList = new ArrayList();
        }
        itemList.add(item);
    }

    public void addItems(List<Item> itemsList) {
        if (itemList == null) {
            itemList = new ArrayList();
        }
        itemList.addAll(itemsList);
    }

}
