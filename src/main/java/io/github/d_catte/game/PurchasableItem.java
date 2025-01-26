package io.github.d_catte.game;

public class PurchasableItem {
    public final ItemStack soldItem;
    public final float price;

    public PurchasableItem(ItemStack itemStack, float price) {
        this.soldItem = itemStack;
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
            ItemStack purchased = soldItem.softCopy();
            purchased.multiply(amount);
            inventory.addItemStack(purchased);
            return remainingMoney;
        } else {
            // Insufficient funds
            return money;
        }
    }
}
