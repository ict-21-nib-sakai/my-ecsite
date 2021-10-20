package me.megmilk.myecsite.http.responses;

/**
 * カート数量変更で戻り値として返す JSON の内側
 * <p>
 * 下記の構成
 * <ul>
 *     <li>CartsJson<ul>
 *         <li>CartJson ← <strong>当クラス</strong></li>
 *     </ul></li>
 * </ul>
 */
public class CartJson {
    private int id;
    private int itemId;
    private String color;
    private String maker;
    private int price;
    private int quantity;

    public int getId() {
        return id;
    }

    public CartJson setId(int id) {
        this.id = id;
        return this;
    }

    public int getItemId() {
        return itemId;
    }

    public CartJson setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    public String getColor() {
        return color;
    }

    public CartJson setColor(String color) {
        this.color = color;
        return this;
    }

    public String getMaker() {
        return maker;
    }

    public CartJson setMaker(String maker) {
        this.maker = maker;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public CartJson setPrice(int price) {
        this.price = price;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartJson setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
