package ly.generalassemb.drewmahrt.shoppinglistver2;

/**
 * Created by sbabba on 2/2/16.
 */
public class GroceryList {
    int _id;
    String item_name;
    String description;
    String price;
    String type;

    public GroceryList(int _id, String item_name, String description, String price, String type) {
        this._id = _id;
        this.item_name = item_name;
        this.description = description;
        this.price = price;
        this.type = type;
    }
}
