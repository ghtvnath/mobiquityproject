package com.mobiquityinc.util;

import com.mobiquityinc.pojo.Item;

import java.util.Comparator;
import java.util.List;

public class PrintHelper {

    private static final String DEF_NORES_STR = "-";

    public static String getListOfSelectedItemIdsAsPritableString (List<Item> selectedItems) {
        StringBuilder sb = new StringBuilder();
        if (selectedItems == null || selectedItems.size() == 0) {
            sb.append(DEF_NORES_STR);
        } else{
            selectedItems.sort(new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getId() - o2.getId();
                }
            });
            for (Item item:selectedItems) {
                sb.append(item.getId()).append(",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

}
