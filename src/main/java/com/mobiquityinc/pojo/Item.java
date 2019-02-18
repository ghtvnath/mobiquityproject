package com.mobiquityinc.pojo;

/**
 * Item class represents an item in the Parcel
 */
public class Item  implements Comparable<Item>{
    private int id;
    private Double weight;
    private String currency;
    private Double cost;

    public Item(int id, Double weight, String currency, Double cost) {
        this.id = id;
        this.weight = weight;
        this.currency = currency;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Item) {
            Item other = (Item) obj;
            return this.id == other.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }



    @Override
    public String toString() {
        return String.format("Item {id : %d, cost currency : %s, cost value : %.2f, weight : %.2f",
                this.id, this.currency, this.cost, this.weight);
    }

    /**
     * An item is make comparable by 'cost divided by weight' factor.
     * Ordering is done by descending order.
     * This is helpful in sorting the items as higher the cost more initially
     * they will appear in the sorted list. If cost is similar, the item that
     * weighs less will take precedence.
     *
     */
    @Override
    public int compareTo(Item o) {
        int result = 0;
        Double rate1 = cost / weight;
        Double rate2 = o.getCost() / o.getWeight();

        return rate2.compareTo(rate1);
    }
}
