package com.mobiquityinc.solution;

import com.mobiquityinc.pojo.Item;
import com.mobiquityinc.pojo.Package;
import com.mobiquityinc.util.PrintHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class PackageOptimizerTest {

    @Test
    public void testDetermineItemIdsFittingToPackageTest1() {
        Package parcel1 = new Package(80);

        Item item1 = new Item(1, 20.50, "€", 30.00);
        Item item2 = new Item(2, 31.20, "€", 20.00);
        Item item3 = new Item(3, 44.70, "€", 40.00);
        Item item4 = new Item(4, 03.88, "€", 102.00);
        Item item5 = new Item(5, 13.15, "€", 35.00);
        Item item6 = new Item(6, 16.10, "€", 45.00);
        Item item7 = new Item(7, 82.45, "€", 50.00);
        Item item8 = new Item(8, 45.25, "€", 40.00);
        Item item9 = new Item(9, 24.30, "€", 32.00);
        Item item10 = new Item(10, 15.75, "€", 25.00);
        Item item11 = new Item(11, 32.80, "€", 33.00);
        Item item12 = new Item(12, 28.20, "€", 10.00);
        Item item13 = new Item(13, 63.50, "€", 42.00);
        Item item14 = new Item(14, 19.72, "€", 45.00);
        Item item15 = new Item(15, 70.00, "€", 30.00);

        List<Item> items = Arrays.asList(item1, item2, item3, item4, item5,
                item6, item7, item8, item9, item10, item11, item12, item13, item14, item15);

        parcel1.addItems(items);

        List<Item> selectedItems = PackageOptimizer.determineItemIdsFittingToPackage(parcel1);
        String resultStr = PrintHelper.getListOfSelectedItemIdsAsPritableString(selectedItems);
        Assert.assertEquals("4,5,6,9,14", resultStr);
    }

    @Test
    public void testDetermineItemIdsFittingToPackageTest2() {
        Package parcel1 = new Package(81);

        Item item1 = new Item(1, 53.38, "€", 45.00);
        Item item2 = new Item(2, 88.62, "€", 98.00);
        Item item3 = new Item(3, 78.48, "€", 3.00);
        Item item4 = new Item(4, 72.30, "€", 76.00);
        Item item5 = new Item(5, 30.18, "€", 9.00);

        List<Item> items = Arrays.asList(item1, item2, item3, item4, item5);

        parcel1.addItems(items);

        List<Item> selectedItems = PackageOptimizer.determineItemIdsFittingToPackage(parcel1);
        String resultStr = PrintHelper.getListOfSelectedItemIdsAsPritableString(selectedItems);
        Assert.assertEquals("4", resultStr);
    }

    @Test
    public void testDetermineItemIdsFittingToPackageTest3() {
        Package parcel1 = new Package(8);

        Item item1 = new Item(1, 15.3, "€", 34.00);

        List<Item> items = Arrays.asList(item1);

        parcel1.addItems(items);

        List<Item> selectedItems = PackageOptimizer.determineItemIdsFittingToPackage(parcel1);
        String resultStr = PrintHelper.getListOfSelectedItemIdsAsPritableString(selectedItems);
        Assert.assertEquals("-", resultStr);
    }

    @Test
    public void testDetermineItemIdsFittingToPackageTest4() {
        Package parcel1 = new Package(75);

        Item item2 = new Item(2, 14.55, "€", 74.00);
        Item item3 = new Item(3, 3.98, "€", 16.00);
        Item item4 = new Item(4, 26.24, "€", 55.00);
        Item item5 = new Item(5, 63.69, "€", 52.00);
        Item item7 = new Item(7, 60.02, "€", 74.00);

        List<Item> items = Arrays.asList(item2, item3, item4, item5,
                item7);
        Collections.sort(items);

        parcel1.addItems(items);

        List<Item> selectedItems = PackageOptimizer.determineItemIdsFittingToPackage(parcel1);
        String resultStr = PrintHelper.getListOfSelectedItemIdsAsPritableString(selectedItems);
        Assert.assertEquals("2,7", resultStr);
    }

    @Test
    public void testDetermineItemIdsFittingToPackageTest5() {
        Package parcel1 = new Package(56);

        Item item2 = new Item(2, 33.80, "€", 40.00);
        Item item3 = new Item(3, 43.15, "€", 10.00);
        Item item4 = new Item(4, 37.97, "€", 16.00);
        Item item5 = new Item(5, 46.81, "€", 36.00);
        Item item6 = new Item(6, 48.77, "€", 79.00);
        Item item8 = new Item(8, 19.36, "€", 79.00);
        Item item9 = new Item(9, 6.76, "€", 64.00);

        List<Item> items = Arrays.asList(item2, item3, item4, item5,
                item6, item8, item9);
        Collections.sort(items);

        parcel1.addItems(items);

        List<Item> selectedItems = PackageOptimizer.determineItemIdsFittingToPackage(parcel1);
        String resultStr = PrintHelper.getListOfSelectedItemIdsAsPritableString(selectedItems);
        Assert.assertEquals("8,9", resultStr);
    }

    @Test
    public void testDetermineItemIdsFittingToPackageNoFittingResult() {
        Package parcel1 = new Package(10);

        Item item1 = new Item(1, 20.50, "€", 30.00);
        Item item2 = new Item(2, 31.20, "€", 20.00);
        Item item3 = new Item(3, 44.70, "€", 40.00);
        Item item4 = new Item(4, 13.88, "€", 102.00);
        Item item5 = new Item(5, 13.15, "€", 35.00);


        List<Item> items = Arrays.asList(item1, item2, item3, item4, item5);
        parcel1.addItems(items);

        List<Item> selectedItems = PackageOptimizer.determineItemIdsFittingToPackage(parcel1);
        String resultStr = PrintHelper.getListOfSelectedItemIdsAsPritableString(selectedItems);
        Assert.assertEquals(PackageOptimizer.DEFAULT_NO_RES_STR, resultStr);

    }

    @Test
    public void testDetermineItemIdsFittingToPackageEmptyItemsList() {
        Package parcel1 = new Package(80);
        List<Item> itemsList = new ArrayList();
        parcel1.addItems(itemsList);

        List<Item> selectedItems = PackageOptimizer.determineItemIdsFittingToPackage(parcel1);
        String resultStr = PrintHelper.getListOfSelectedItemIdsAsPritableString(selectedItems);
        Assert.assertEquals(PackageOptimizer.DEFAULT_NO_RES_STR, resultStr);
    }

}
