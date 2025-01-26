package io.github.d_catte.data;

import java.util.ArrayList;
import java.util.List;

public class Member {
    public final String name;
    private final List<StatusContainer> statuses = new ArrayList<>();
    public final String profession;
    public final boolean isPlayer;

    public Member(String name, StatusContainer defaultStatus, String profession, boolean isPlayer) {
        this.name = name;
        this.statuses.add(defaultStatus);
        this.profession = profession;
        this.isPlayer = isPlayer;
    }

    public List<StatusContainer> getStatuses() {
        return statuses;
    }

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
