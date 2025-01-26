package io.github.d_catte.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Inventory {
    private final List<ItemStack> items = new ArrayList<>();

    public Inventory() {}

    /**
     * Adds an ItemStack to the Inventory
     * @param itemStack ItemStack to add
     */
    public void addItemStack(ItemStack itemStack) {
        containsItem(itemStack).ifPresentOrElse(
                (originalStack) -> originalStack.mergeItemStacks(itemStack),
                () -> this.items.add(itemStack)
        );
    }

    public Optional<ItemStack> containsItem(ItemStack stack) {
        return containsItem(stack.name);
    }

    /**
     * Finds if the inventory already contains the specified item
     * @param item Item's name
     * @return If the item exists
     */
    public Optional<ItemStack> containsItem(String item) {
        for (ItemStack stack : this.items) {
            if (stack.name.equals(item)) {
                return Optional.of(stack);
            }
        }
        return Optional.empty();
    }

    /**
     * Removes the specified ItemStack (including amount) from the Inventory
     * @param itemStack The ItemStack (including amount) to remove
     * @return True if success and False if the ItemStack cannot be removed
     */
    public boolean removeItemStack(ItemStack itemStack) {
        // Use references instead of Atomics
        // This is required due to the ifPresentOrElse statement
        var ref = new Object() {
            boolean flag;
        };
        containsItem(itemStack).ifPresentOrElse(
                (originalStack) -> {
                    if (originalStack.canConsume(itemStack.count)) {
                        if (!originalStack.consume(itemStack.count)) {
                            this.items.remove(originalStack);
                        }
                        ref.flag = true;
                    } else {
                        ref.flag = false;
                    }
                },
                () -> ref.flag = false
        );
        return ref.flag;
    }

    /**
     * Gets the amount of an ItemStack are present
     * @param item Item name to query
     * @return The amount of items of that type in the Inventory
     */
    public short itemStackCount(String item) {
        // Use references instead of Atomics
        // This is required due to the ifPresentOrElse statement
        var ref = new Object() {
            short amount;
        };
        containsItem(item).ifPresentOrElse(
                (originalStack) -> ref.amount = originalStack.count,
                () -> ref.amount = 0
        );
        return ref.amount;
    }
}
