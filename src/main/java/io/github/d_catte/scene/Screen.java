package io.github.d_catte.scene;

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
