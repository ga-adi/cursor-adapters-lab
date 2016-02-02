package ly.generalassemb.drewmahrt.shoppinglistver2;

/**
 * Created by charlie on 2/2/16.
 */
public class GroceryItem {
    private int mId;
    private String mItemName;
    private String mDescription;
    private String mPrice;
    private String mType;

    public GroceryItem(int id, String itemName, String description, String price, String type) {
        mId = id;
        mItemName = itemName;
        mDescription = description;
        mPrice = price;
        mType = type;
    }

    public int getId() {
        return mId;
    }

    public String getItemName() {
        return mItemName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getType() {
        return mType;
    }
}
