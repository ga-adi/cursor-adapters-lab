package ly.generalassemb.drewmahrt.shoppinglistver2.models;

import android.support.annotation.NonNull;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class GroceryItem {

  private String mName;
  private String mDescription;
  private double mPrice;
  private ItemType mType;

  private GroceryItem(String name, String description, double price,
                      ItemType type) {
    mName = name;
    mDescription = description;
    mPrice = price;
    mType = type;
  }

  public String getName() { return mName; }

  public String getDescription() { return mDescription; }

  public double getPrice() { return mPrice; }

  public ItemType getItemType() { return mType; }

  public enum ItemType { FOOD;

    @Override
    public String toString() {
      switch (this) {
        case FOOD:
          return "Food";
      }
      return "";
    }
  }

  public static class Builder {
    public static String NAME = "Item Name";
    public static String DESCRIPTION = "";
    public static double PRICE = 0.0;
    public static ItemType ITEM_TYPE = ItemType.FOOD;

    private String mName;
    private String mDescription;
    private double mPrice;
    private ItemType mType;

    public Builder() {
      mName = NAME;
      mDescription = DESCRIPTION;
      mPrice = PRICE;
      mType = ITEM_TYPE;
    }

    public Builder name(@NonNull String name) {
      if (name.isEmpty() || (name.length() < 2)) {
        throw new IllegalArgumentException("name is too short");
      }
      mName = name;
      return this;
    }

    public Builder description(@NonNull String description) {
      mDescription = description;
      return this;
    }

    public Builder price(double price) {
      if (price < 0) {
        throw new IllegalArgumentException("price cannot be negative");
      }
      mPrice = price;
      return this;
    }

    public Builder itemType(ItemType itemType) {
      mType = itemType;
      return this;
    }

    public GroceryItem build() {
      return new GroceryItem(mName, mDescription, mPrice, mType);
    }
  }
}
