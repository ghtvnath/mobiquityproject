package com.mobiquityinc.packer;

import com.mobiquityinc.solution.PackageOptimizer;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.pojo.Item;
import com.mobiquityinc.pojo.Package;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Tharindu
 * <p>
 * Packer class is responsible in
 * - Reading the text file
 * - Parsing each line into maximum weight, and list of items
 * - Converting each line into a Package object
 * - At the end, the file that read will be parsed into a list of Package objects
 * - Then it would invoke determineItemIdsFittingToPackage function in
 * PackageOptimizer class to solve the problem and getting the ids of the items
 * as a String which will optimally fit into the parcel.
 */
public class Packer {

    private static final double MAX_WEIGHT = 100.00;
    private static final double MAX_COST_VALUE = 100.00;

    /**
     * @param filePath
     * @return String - list of ids that optimallly fit into package
     * for each given line in the file of filePath
     * @throws APIException
     */
    public static String pack(String filePath) throws APIException {
        try {
            List<Package> listOfPackages = convertFileItemsToPackages(filePath);

            StringBuilder sb = new StringBuilder();

            for (Package parcel : listOfPackages) {
                sb.append(PackageOptimizer.determineItemIdsFittingToPackage(parcel));
                sb.append(System.lineSeparator());
            }

            System.out.println("*****************SOLUTION*******************");
            System.out.println(sb.toString());

            return sb.toString();
        } catch (APIException apiE) {
            throw apiE;
        } catch (Exception e) {
            e.printStackTrace();
            throw new APIException("Error occurred while solvinng optimal package problem " + e.getMessage());
        }

    }

    /**
     * Method responsible in converting each line of the file
     * into a List of Package objects
     */
    protected static List<Package> convertFileItemsToPackages(String filePath) throws APIException {
        //read the file in the filepath into a Stream
        Path path = Paths.get(filePath);
        List<Package> listOfPackages = null;
        try {
            Stream<String> lines = Files.lines(path);
            // parse each line of the file into a Package object and filter out any null objects
            listOfPackages = lines.map(line -> parseLine(line))
                    .filter(o -> o != null)
                    .collect(Collectors.toList());
            lines.close();
        } catch (IOException ioe) {
            throw new APIException("Error reading the file.");
        }
        return listOfPackages;
    }

    /**
     * Protected method which is responsible in parsing each line into a Package object.
     * It will first split the line by ":" and get the left portion of the string as the
     * maximum weight. Then it will parse the right side of the string into items.
     */
    protected static Package parseLine(String line) {
        if (StringUtils.isEmpty(line)) {
            System.err.println("File contains an empty line");
            return null;
        }
        String[] strArr = line.split(":");
        if (strArr.length != 2) {
            System.err.println("File contains incorrect input line");
            return null;
        }
        try {
            int packageMaxWeight = Integer.valueOf(strArr[0].trim());
            System.out.println("Package maximum weight limit :" + packageMaxWeight);
            Package aPackage = new Package(packageMaxWeight);
            if (packageMaxWeight > MAX_WEIGHT) {
                System.err.println("Invalid package weight defined in input line");
                return aPackage;
            }
            String[] itemsTexts = strArr[1].trim()              // remove leading and trailing spaces, if any
                    .substring(1, strArr[1].length() - 2)       // get the string without first ( symbol and last ) symbol.
                    .split("\\)\\s+\\(");                // split the text by ") ("

            /* filter out the items that meet the requirements :
                Item's weight should be equal or less than to the maximum weight allowed in the package...
                Item's cost should be equal or less than to DEFAULT MAXIMUM COST (100)

                Fltering out items at this level help to improve the algorithm run time at later stage.
            */
            List<Item> itemsList = Arrays.stream(itemsTexts).map(s -> parseStringToItem(s))
                    .filter(o -> o != null)
                    .filter(o -> (o.getWeight() <= packageMaxWeight)
                            && o.getCost() <= MAX_COST_VALUE)
                    .sorted()
                    .collect(Collectors.toList());
            aPackage.addItems(itemsList);

            return aPackage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Given the part of the line as string which contains information regarding a
     * single item, this method will parse the string into an Item object.
     */
    protected static Item parseStringToItem(String str) {
        String[] strList = str.split(",");
        if (strList.length != 3) {
            System.err.println("File line data is not in the correct format.");
            return null;
        }

        //find the index of the place where the cost numbers start
        Matcher matcher = Pattern.compile("\\d").matcher(strList[2]);
        matcher.find();
        int numberIndex = strList[2].indexOf(matcher.group());
        Item item = new Item(Integer.parseInt(strList[0]),
                Double.valueOf(strList[1]),
                strList[2].substring(0, numberIndex).trim(),
                Double.valueOf(strList[2].substring(numberIndex)));
        return item;
    }


}
