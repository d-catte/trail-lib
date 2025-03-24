package io.github.d_catte.game;

import io.github.d_catte.data.Member;
import io.github.d_catte.data.StatusContainer;

import java.util.Optional;

/**
 * Items that will give buffs when consumed
 * @author Dylan Catte, Ben Westover, Noah Sumerauer, Micah Lee
 * @version 1.0
 */
public class BuffedItemStack extends ItemStack {
    public final StatusContainer buffedStatus;
    public BuffedItemStack(String name, StatusContainer buffedStatus) {
        super(name);
        this.buffedStatus = buffedStatus;
    }

    /**
     * Constructor that sets the local variables to the inputed variables.
     * @param name Name of the item
     * @param count Number of the item
     * @param buffedStatus What status the item gives
     */
    public BuffedItemStack(String name, short count, StatusContainer buffedStatus) {
        super(name, count);
        this.buffedStatus = buffedStatus;
    }

    /**
     * Consumes the item (removes at least one from inventory
     * @param member What member of the party it is
     */
    public void consume(Member member) {
        Optional<StatusContainer> container = member.getStatuses().stream().filter(statusContainer -> statusContainer.equals(this.buffedStatus)).findFirst();
        container.ifPresent(
                statusContainer -> {
                    if (statusContainer.decreaseLevel()) {
                        member.getStatuses().remove(statusContainer);
                    }
                }
        );
    }

    /**
     * Copies the BuffedItemStack without editiing it
     * @return return a copy of the class instance variable
     */
    @Override
    public ItemStack softCopy() {
        return new BuffedItemStack(this.name, this.count, this.buffedStatus);
    }
}
