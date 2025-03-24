package io.github.d_catte.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all the data for a member of the party.
 * @author Dylan Catte, Ben Westover, Noah Sumerauer, Micah Lee
 * @version 1.0
 */
public class Member {
    public final String name;
    private final List<StatusContainer> statuses = new ArrayList<>();
    public final String profession;
    public final boolean isPlayer;

    /**
     * Constructor that sets the local variables to the inputed variables.
     * @param name Name of the member of the party
     * @param defaultStatus The status that the party member starts with
     * @param profession What profession the party member is
     * @param isPlayer If the member of the party is the player (For who makes decisions)
     */
    public Member(String name, StatusContainer defaultStatus, String profession, boolean isPlayer) {
        this.name = name;
        this.statuses.add(defaultStatus);
        this.profession = profession;
        this.isPlayer = isPlayer;
    }

    /**
     * Get the status of the party member
     * @return return the status of the party member
     */
    public List<StatusContainer> getStatuses() {
        return statuses;
    }

    /**
     * Updates the party member's status
     * @param status What status is being applied or taken away from the party member
     */
    public void addStatus(StatusContainer status) {
        if (this.statuses.contains(status)) {
            this.statuses.remove(status);
            // TODO Increase status severity
            // TODO Force status update Scene
        } else {
            this.statuses.add(status);
        }
    }
}
