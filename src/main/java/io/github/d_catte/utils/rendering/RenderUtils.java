package io.github.d_catte.utils.rendering;

import io.github.d_catte.utils.Text;

public final class RenderUtils {
    public static final String EMPTY_STR = "";
    public static final Text EMPTY_TEXT = new Text(EMPTY_STR);

    public static int[] relativeToAbsolutePositioning(int windowWidth, int windowHeight, double relativeX, double relativeY) {
        return new int[] {
                (int) Math.round(windowWidth * relativeX),
                (int) Math.round(windowHeight * relativeY)
        };
    }

    public static void centeredCoordinates(int[] pos, int width, int height) {
        pos[0] -= width / 2;
        pos[1] -= height / 2;
    }
}
