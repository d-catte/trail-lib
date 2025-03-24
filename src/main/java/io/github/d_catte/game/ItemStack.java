package io.github.d_catte.game;

/**
 * Keeps the number of a certain item in a stack
 * @author Dylan Catte, Ben Westover, Noah Sumerauer, Micah Lee
 * @version 1.0
 */
public class ItemStack {
    public final String name;
    public short count;

    /**
     * Constructor that sets the local variables to the inputed variable.
     * @param name Name of the item stack
     */
    public ItemStack(String name) {
        this.name = name;
    }

    /**
     * Constructor that sets the local variables to the inputed variables.
     * @param name Name of the item stack
     * @param count Number of item in the item stack
     */
    public ItemStack(String name, short count) {
        this(name);
        this.count = count;
    }

    /**
     * Consumes 1 item
     * @return False if all items have been consumed
     */
    public boolean consume() {
        if (this.count <= 1) {
            return false;
        } else {
            this.count--;
            return true;
        }
    }

    /**
     * Checks if the amount of items can be consumed
     * @param amount The amount of items to consume
     * @return True if the consumption is possible
     */
    public boolean canConsume(short amount) {
        return this.count >= amount;
    }

    /**
     * Consumes a specified amount of items.
     * Make sure canConsume() is checked first.
     * @param amount The amount of items to consume
     * @return False if all items have been consumed
     */
    public boolean consume(short amount) {
        this.count -= amount;
        return this.count > 0;
    }

    /**
     * Merges the amounts of two ItemStacks
     * @param otherStack Other ItemStack to merge with this one
     */
    public void mergeItemStacks(ItemStack otherStack) {
        this.count += otherStack.count;
    }

    /**
     * Multiplies the amount by the value
     * @param by The factor to multiply by
     */
    public void multiply(short by) {
        this.count *= by;
    }

    /**
     * Copies all the ItemStack's data into a new instance
     * @return New instance of ItemStack
     */
    public ItemStack softCopy() {
        return new ItemStack(this.name, this.count);
    }
}
