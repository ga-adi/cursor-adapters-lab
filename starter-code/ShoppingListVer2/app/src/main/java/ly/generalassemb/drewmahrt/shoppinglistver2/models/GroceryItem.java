package ly.generalassemb.drewmahrt.shoppinglistver2.models;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Copyright 2016 Boloutare Doubeni
 */
public class GroceryItem implements Serializable {

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

  public enum ItemType { Food, Dairy, Dishes, Poultry, Produce }

  public static final class Builder {
    public static String NAME = "Item Name";
    public static String DESCRIPTION = "";
    public static double PRICE = 0.0;
    public static ItemType ITEM_TYPE = ItemType.Food;

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

  public static class Updater implements Serializable {
    final private long mId;
    private String mName = null;
    private String mDescription = null;
    private Double mPrice;
    private ItemType mType = null;

    public Updater(@NonNull long id) { mId = id; }

    public Updater name(@NonNull String name) {
      if (name.isEmpty() || (name.length() < 2)) {
        throw new IllegalArgumentException("name is too short");
      }
      mName = name;
      return this;
    }

    public Updater description(@NonNull String description) {
      mDescription = description;
      return this;
    }

    public Updater price(Double price) {
      if (price < 0) {
        throw new IllegalArgumentException("price cannot be negative");
      }
      mPrice = price;
      return this;
    }

    public Updater itemType(ItemType itemType) {
      mType = itemType;
      return this;
    }

    public long getId() { return mId; }

    public String getName() { return mName; }

    public String getDescription() { return mDescription; }

    public Double getPrice() { return mPrice; }

    public ItemType getItemType() { return mType; }
  }
}
