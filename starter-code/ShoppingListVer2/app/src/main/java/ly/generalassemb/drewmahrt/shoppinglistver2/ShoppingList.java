package ly.generalassemb.drewmahrt.shoppinglistver2;

/**
 * Created by williamtygret on 2/2/16.
 */
public class ShoppingList {
    public int id;
    public String name;
    public String description;
    public double price;
    public String type;

    public ShoppingList(int id, String name, String description, double price, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;

    }
}
