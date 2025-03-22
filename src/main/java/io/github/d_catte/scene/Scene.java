package io.github.d_catte.scene;

import java.util.List;

public interface Scene {
    List<Screen> getScreens();
    default void showNextScreen() {
        // -1 represents a Scene that has just been made
        if (getScreenIndex() == -1) {
//            updateUI(0, renderer);
            setScreenIndex(0);
        } else {
            int index = getScreenIndex() + 1;
            if (index < getScreens().size()) {
//                updateUI(index, renderer);
                setScreenIndex(index);
            } else {
                setFinished();
            }
        }
    }
    int getScreenIndex();

    /**
     * Sets the screen index to the specified index.
     * It also automatically redraws the UI
     * @param i Screen index
     */
    void setScreenIndex(int i); // TODO Ensure updateUI() is called
    boolean finished();
    void setFinished();
}
