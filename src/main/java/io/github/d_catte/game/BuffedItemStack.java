package io.github.d_catte.game;

import io.github.d_catte.data.Member;
import io.github.d_catte.data.StatusContainer;

import java.util.Optional;

public class BuffedItemStack extends ItemStack {
    public final StatusContainer buffedStatus;
    public BuffedItemStack(String name, StatusContainer buffedStatus) {
        super(name);
        this.buffedStatus = buffedStatus;
    }

    public BuffedItemStack(String name, short count, StatusContainer buffedStatus) {
        super(name, count);
        this.buffedStatus = buffedStatus;
    }

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

    @Override
    public ItemStack softCopy() {
        return new BuffedItemStack(this.name, this.count, this.buffedStatus);
    }
}
