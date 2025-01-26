package io.github.d_catte.data;

import io.github.d_catte.TrailApplication;

import java.util.concurrent.ThreadLocalRandom;

public class StatusContainer {
    public final String name;
    public byte level;
    private final byte maxLevel;
    private final float baseChance;

    public StatusContainer(String name, byte level, byte maxLevel, float baseChance) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.baseChance = baseChance;
    }

    /**
     * Increases the severity level.
     * @return Returns false if the highest level is reached.
     *         This indicates that a member should be killed.
     */
    public boolean increaseLevel() {
        level++;
        if (level >= maxLevel) {
            // TODO trigger death
            return false;
        }
        return true;
    }

    /**
     * Decreases the severity level
     * @return Returns false if the severity level reaches 0.
     *         This indicates that the status should be removed.
     */
    public boolean decreaseLevel() {
        if (level <= 1) {
            return false;
        }
        level--;
        return true;
    }

    /**
     * Rolls the chance of obtaining the status
     * @return If the status should be applied
     */
    public boolean chance() {
        return ThreadLocalRandom.current().nextFloat() < (baseChance * TrailApplication.gameInstance.difficulty);
    }

    /**
     * Gets an instance of the StatusContainer with a level of 1
     * @return new instance of StatusContainer
     */
    public StatusContainer instanceCopy() {
        return new StatusContainer(this.name, (byte) 1, this.maxLevel, this.baseChance);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StatusContainer container) {
            return this.name.equals(container.name);
        }
        return false;
    }
}
