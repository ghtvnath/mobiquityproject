package com.mobiquityinc.solution;

import com.mobiquityinc.pojo.Item;
import com.mobiquityinc.pojo.Package;

import java.util.ArrayList;
import java.util.List;

public class PackageOptimizer {

    protected static final String DEFAULT_NO_RES_STR = "-";

    public static String determineItemIdsFittingToPackage(Package parcel) {
        if (parcel == null || parcel.getItemList() == null || parcel.getItemList().size() == 0) {
            return DEFAULT_NO_RES_STR;
        }
        int maxWeight = parcel.getMaximumWeight();
        List<Item> itemsList = parcel.getItemList();
        int numberOfItems = itemsList.size();

        double[][] solutionMatrix = new double[numberOfItems + 1][maxWeight + 1];

        int i, j;

        for (i = 0; i <= numberOfItems; i++) {
            for (j = 0; j <= maxWeight; j++) {
                if (i == 0 || i == 0) {
                    solutionMatrix[i][j] = 0;
                } else if (Math.ceil(itemsList.get(i - 1).getWeight()) <= j) {
                    solutionMatrix[i][j] = Math.max(solutionMatrix[i - 1][j],
                            solutionMatrix[i - 1][j - (int) Math.floor(itemsList.get(i - 1).getWeight())] + itemsList.get(i - 1).getCost());

                } else
                    solutionMatrix[i][j] = solutionMatrix[i - 1][j];
            }
        }

        double optimalCostOfPackage = solutionMatrix[numberOfItems][maxWeight];

        System.out.println("optimalCostOfPackage-" + optimalCostOfPackage);

        j = maxWeight;

        StringBuilder sb = new StringBuilder();

        for (i = numberOfItems; i > 0 && optimalCostOfPackage > 0; i--) {
            // if the corresponding column in the row above has the same above, continue to check the row above
            if (optimalCostOfPackage == solutionMatrix[i - 1][j]) {
                continue;
            } else {
                // Item in the position has been involved in optimal weight solution
                sb.append(itemsList.get(i - 1).getId()).append(",");
                System.out.println(itemsList.get(i - 1).getId());
                optimalCostOfPackage = optimalCostOfPackage - itemsList.get(i - 1).getCost();
                j = j - (int) Math.floor(itemsList.get(i - 1).getWeight());
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb = new StringBuilder(DEFAULT_NO_RES_STR);
        }

        return sb.toString();

    }
}
