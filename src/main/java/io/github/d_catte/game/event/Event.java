package io.github.d_catte.game.event;

import io.github.d_catte.TrailApplication;
import io.github.d_catte.data.StatusContainer;
import io.github.d_catte.utils.Translations;
import io.github.d_catte.utils.rendering.RenderUtils;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Events are random actions that occur throughout the journey.
 * They can represent a member dying, a status being applied, a town being visiting, or another random trail event
 * @param type The type of Event
 * @param translationKey The translation for the subtitle that appears on the screen
 * @param pathToScreen The path to the screen json
 * @param additionalData Any additional data from the EventType
 * @param chance The chance that the event is selected randomly
 * @param statusToApply If a status is applied (This will automatically add the status to a random player and create
 *                      a new status inflicted event)
 */
public record Event(EventType type, String translationKey, String pathToScreen, float chance,
                    @Nullable StatusContainer statusToApply, String... additionalData) {

    public String getSubtitle() {
        switch (type) {
            case Death, StatusInflicted -> {
                return additionalData[0] + " " + Translations.getTranslatedText(translationKey) + additionalData[1]; // The additional data will be: player, status
            }
            case RandomTrailEvent -> {
                return Translations.getTranslatedText(translationKey);
            }
            case TownVisit -> {
                return Translations.getTranslatedText(translationKey) + " " + additionalData[0]; // The additional data will be a town name
            }
        }
        return RenderUtils.EMPTY_STR;
    }

    public Event copyInstance() {
        return new Event(this.type, this.translationKey, this.pathToScreen, this.chance, this.statusToApply, this.additionalData);
    }

    /**
     * Selects a random event and randomly determines if it should be applied
     */
    public static void selectRandomEvent() {
        int index = ThreadLocalRandom.current().nextInt(0, TrailApplication.gameInstance.events.size());
        Event event = TrailApplication.gameInstance.events.get(index);
        if (ThreadLocalRandom.current().nextFloat() < event.chance) {
            EventManager.currentEvents.add(event.copyInstance());
        }
    }
}
