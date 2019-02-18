package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojo.Item;
import com.mobiquityinc.pojo.Package;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class PackerTest {


    @Test
    public void testConvertFileItemsToPackages() throws APIException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test_file.txt").getFile());
        String strFilePath = file.getAbsolutePath();

        List<Package> packages = Packer.convertFileItemsToPackages(strFilePath);
        Assert.assertTrue(packages.size() > 0);
        Assert.assertEquals(5, packages.get(0).getItemList().size());
    }

    @Test(expected = APIException.class)
    public void testConvertFileItemsToPackagesInvalid() throws APIException {
        String strFilePath = "/invalid/file/path";

        List<Package> packages = Packer.convertFileItemsToPackages(strFilePath);
    }

    @Test
    public void testParseLine() {
        Package parcel = Packer.parseLine("75 : (1,85.31,€29) (2,14.55,€74) (3,7.55,€74) (4,33.50,€102)");

        Assert.assertNotNull(parcel);
        Assert.assertEquals(75, parcel.getMaximumWeight());
        Assert.assertNotNull(parcel.getItemList());
        Assert.assertEquals(2, parcel.getItemList().size()); // items with weight 85.31 and cost €102 should be excluded
        Assert.assertEquals(3, parcel.getItemList().get(0).getId()); // item of less weight should be ordered first if the cost is equal
    }

    @Test
    public void testParseEmptyLine() {
        Package parcel = Packer.parseLine(" ");

        Assert.assertNull(parcel);
    }

    @Test
    public void testParseInvalidLine() {
        Package parcel = Packer.parseLine("75  (1,85.31,€29) (2,14.55,€74) (3,7.55,€74) (4,33.50,€102)");

        Assert.assertNull(parcel);
    }

    @Test
    public void testParseStringToItem() {
        Item item = Packer.parseStringToItem("2,14.55,€74");
        Assert.assertEquals(2, item.getId());
        Assert.assertEquals(Double.valueOf(14.55), item.getWeight());
        Assert.assertEquals("€", item.getCurrency());
        Assert.assertEquals(Double.valueOf(74), item.getCost());
    }

    @Test
    public void testParseStringToItemWithUSD() {
        Item item = Packer.parseStringToItem("2,14.55,USD 74");
        Assert.assertEquals("USD", item.getCurrency());
        Assert.assertEquals(Double.valueOf(74), item.getCost());
    }

    @Test
    public void testParseStringToItemInvalid() {
        Item item = Packer.parseStringToItem("2,14.55,");
        Assert.assertNull(item);
    }
}
