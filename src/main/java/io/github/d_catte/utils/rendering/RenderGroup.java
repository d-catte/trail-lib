package io.github.d_catte.utils.rendering;

import io.github.d_catte.data.UIComponent;

import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderGroup {
    private final List<UIComponent> elements = new ArrayList<>();
    public RenderGroup(UIComponent... elements) {
        Collections.addAll(this.elements, elements);
    }

    /**
     * Renders all UIComponents in order from top to bottom
     * @param renderer TrailRenderer instance
     * @return Object that encapsulates all the UIComponents if using Swing.
     *         Returns null if using ImGUI
     */
    @Nullable
    public Object render(TrailRenderer renderer) {
        if (renderer instanceof SwingRenderer) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            for (UIComponent component : this.elements) {
                panel.add((Component) component.render(renderer));
            }
            return panel;
        } else {
            for (UIComponent component : this.elements) {
                component.render(renderer);
            }
            return null;
        }
    }

    /**
     * Removes an element and moves all existing elements up
     * @param index The index to remove
     */
    public void removeElement(int index) {
        // Move all elements up
        for (int i = index; i < this.elements.size() - 1; i++) {
            this.elements.set(i, this.elements.get(i + 1));
        }
    }

    /**
     * Adds an element at an index and moves other elements down
     * @param index Index to place new UIComponent
     * @param component New UIComponent
     */
    public void addElement(int index, UIComponent component) {
        // Move all elements down
        for (int i = this.elements.size()-1; i >= index; i--) {
            this.elements.set(i + 1, this.elements.get(i));
        }
        this.elements.set(index, component);
    }

    /**
     * Gets all UIComponents in the RenderGroup
     * @return All UIComponents
     */
    public List<UIComponent> getComponents() {
        return this.elements;
    }
}
