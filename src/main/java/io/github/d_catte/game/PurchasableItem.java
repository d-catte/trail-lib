package io.github.d_catte.game;

public class PurchasableItem extends ItemStack {
    public final float price;

    public PurchasableItem(String name, float price) {
        super(name);
        this.price = price;
    }

    /**
     * Purchases the ItemStack being sold
     * @param money The amount of money the players' have
     * @param inventory The players' inventory
     * @param amount The amount of the item that should be purchased
     * @return The remaining amount of money the players' have
     */
    public float purchase(float money, Inventory inventory, short amount) {
        float remainingMoney = money - (price * amount);
        if (remainingMoney >= 0) {
            ItemStack purchased = this.softCopy();
            purchased.multiply(amount);
            inventory.addItemStack(purchased);
            return remainingMoney;
        } else {
            // Insufficient funds
            return money;
        }
    }
}
