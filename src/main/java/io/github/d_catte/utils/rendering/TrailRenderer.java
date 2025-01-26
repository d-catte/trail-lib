package io.github.d_catte.utils.rendering;

import io.github.d_catte.utils.Command;
import io.github.d_catte.utils.Text;

import java.net.URI;

public interface TrailRenderer {
    /**
     * Creates a new Window
     * @param width Window's width
     * @param height Window's height
     * @param screenTitle The Window's title
     */
    void createWindow(double width, double height, String screenTitle);

    /**
     * Requests the UI to be redrawn.
     * This is constantly called for ImGUI.
     */
    void updateUI();

    /**
     * Resets the UI
     */
    void clearUI();

    /**
     * Renders a button at the specified location with specified traits
     * @param relativePosX Relative position from the left of the Window (in percent: 0.5 = 50%)
     * @param relativePosY Relative position from the top of the Window (in percent: 05 = 50%)
     * @param width The width of the button
     * @param height The height of the button
     * @param text The Text that the button should contain
     * @param clickCallback The code executed when a button is clicked
     * @return The button
     */
    Object renderButton(float relativePosX, float relativePosY, double width, double height, Text text, Command[] clickCallback);

    /**
     * Renders text at the specified location with specified traits
     * @param relativePosX Relative position from the left of the Window (in percent: 0.5 = 50%)
     * @param relativePosY Relative position from the top of the Window (in percent: 05 = 50%)
     * @param text The Text that the text should contain
     * @return The Text
     */
    Object renderText(float relativePosX, float relativePosY, Text text);

    /**
     * Renders text that the user can edit at the specified location with the specified traits
     * @param relativePosX Relative position from the left of the Window (in percent: 0.5 = 50%)
     * @param relativePosY Relative position from the top of the Window (in percent: 05 = 50%)
     * @param text The Text that the text field should contain
     * @param enterCallback The code executed when the user pressed Enter or exits the text field
     * @return The TextField
     */
    Object renderTextField(float relativePosX, float relativePosY, Text text, Command[] enterCallback);

    /**
     * Renders a scrollable list of renderable elements
     * @param relativePosX Relative position from the left of the Window (in percent: 0.5 = 50%)
     * @param relativePosY Relative position from the top of the Window (in percent: 05 = 50%)
     * @param width The width of the list
     * @param height The height of the list
     * @param visuals All code that should be run to render visuals (in order from top to bottom)
     * @return The List
     */
    Object renderList(float relativePosX, float relativePosY, double width, double height, RenderGroup visuals);

    /**
     * Renders an image
     * @param relativePosX Relative position from the left of the Window (in percent: 0.5 = 50%)
     * @param relativePosY Relative position from the top of the Window (in percent: 05 = 50%)
     * @param width The width of the image
     * @param height The height of the image
     * @param image The file path or URL to the image
     * @return The Image
     */
    Object renderImage(float relativePosX, float relativePosY, double width, double height, URI image);

    /**
     * Gets the dimensions of Text
     * @param text Text to be measured
     * @return Integer array (x, y)
     */
    int[] calculateTextSize(Text text);
}
