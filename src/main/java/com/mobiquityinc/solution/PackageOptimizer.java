package com.mobiquityinc.solution;

import com.mobiquityinc.pojo.Item;
import com.mobiquityinc.pojo.Package;

import java.util.ArrayList;
import java.util.List;

public class PackageOptimizer {

    protected static final String DEFAULT_NO_RES_STR = "-";

    public static List<Item> determineItemIdsFittingToPackage(Package parcel) {
        if (parcel == null || parcel.getItemList() == null || parcel.getItemList().size() == 0) {
            return null;
        }
        int maxWeight = parcel.getMaximumWeight();
        List<Item> itemsList = parcel.getItemList();
        int numberOfItems = itemsList.size();

        /* create a 2D array matrix with columns equal to maxweight+1 and
        * rows number of items+1
        *
        * For each weight from 0-80, we consider the items that contribute to optimal
        * package for that particular weight
        */
        double[][] solutionMatrix = new double[numberOfItems + 1][maxWeight + 1];

        int i, j;

        for (i = 0; i <= numberOfItems; i++) {
            for (j = 0; j <= maxWeight; j++) {
                if (i == 0 || i == 0) {         // top row and left most column will have 0. First weight that is
                                                // considered is 0 hence cannot have any item fitting
                    solutionMatrix[i][j] = 0;
                } else if (Math.ceil(itemsList.get(i - 1).getWeight()) <= j) {
                    // if the item of the weight is less than the weight that is being considered
                    // check if adding the item would make it the optimal cost.
                    // otherwise just use the optimal value without using it
                    solutionMatrix[i][j] = Math.max(solutionMatrix[i - 1][j],
                            solutionMatrix[i - 1][j - (int) Math.floor(itemsList.get(i - 1).getWeight())] + itemsList.get(i - 1).getCost());

                } else
                    // if the weight of the item is not less than the target weight, that item cannot be considered
                    // hence the optimal solution without adding the item is considered
                    solutionMatrix[i][j] = solutionMatrix[i - 1][j];
            }
        }

        // by the time the matrix is filled, the cost at the most right bottom index
        // has the optimal cost of package
        double optimalCostOfPackage = solutionMatrix[numberOfItems][maxWeight];

        System.out.println("optimalCostOfPackage-" + optimalCostOfPackage);

        List<Item> selectedItemsList = new ArrayList<>();

        j = maxWeight;
        for (i = numberOfItems; i > 0 && optimalCostOfPackage > 0; i--) {
            // if the corresponding column in the row above has the same above, continue to check the row above
            if (optimalCostOfPackage == solutionMatrix[i - 1][j]) {
                continue;
            } else {
                // Item in the position has been involved in optimal weight solution
                selectedItemsList.add(itemsList.get(i - 1));

                // now check what are the items that can fit into package when
                // the weight = weight - (weight_of_the_selected_item)
                optimalCostOfPackage = optimalCostOfPackage - itemsList.get(i - 1).getCost();
                j = j - (int) Math.floor(itemsList.get(i - 1).getWeight());
            }
        }

        return selectedItemsList;

    }
}
