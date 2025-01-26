package io.github.d_catte.utils.rendering;

import io.github.d_catte.utils.Command;
import io.github.d_catte.utils.Text;

import java.net.URI;

public class ImGUIRenderer implements TrailRenderer {
    @Override
    public void createWindow(double width, double height, String screenTitle) {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void clearUI() {

    }

    @Override
    public Object renderButton(float relativePosX, float relativePosY, double width, double height, Text text, Command[] clickCallback) {

    }

    @Override
    public Object renderText(float relativePosX, float relativePosY, Text text) {

    }

    @Override
    public Object renderTextField(float relativePosX, float relativePosY, Text text, Command[] enterCallback) {

    }

    @Override
    public Object renderList(float relativePosX, float relativePosY, double width, double height, RenderGroup visuals) {

    }

    @Override
    public Object renderImage(float relativePosX, float relativePosY, double width, double height, URI image) {

    }

    @Override
    public int[] calculateTextSize(Text text) {
        return new int[0];
    }
}
