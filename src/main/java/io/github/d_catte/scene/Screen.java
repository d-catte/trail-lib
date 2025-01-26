package io.github.d_catte.scene;

import io.github.d_catte.data.Member;
import io.github.d_catte.data.UIComponent;
import io.github.d_catte.utils.rendering.TrailRenderer;

import java.util.List;

public interface Screen {
    /**
     * Whether to show the screen to the player or not based on their profession
     * @param member Member that is being checked
     * @return If they should see the screen
     */
    default boolean showToPlayer(Member member) {
        return member.isPlayer && (getProfessionShownList() == null || getProfessionShownList().contains(member.profession));
    }

    List<String> getProfessionShownList();

    /**
     * Renders the UIComponents
     * @param renderer TrailRenderer instance
     */
    default void renderUIComponents(TrailRenderer renderer) {
        renderer.clearUI();
        for (UIComponent component : getUIComponents()) {
            component.render(renderer);
        }
    }

    List<UIComponent> getUIComponents();

    default void onResize(int width, int height, TrailRenderer renderer) {
        setSize(width, height);
        renderUIComponents(renderer);
    }

    int[] getSize();
    void setSize(int width, int height);
}
