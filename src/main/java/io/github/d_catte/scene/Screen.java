package io.github.d_catte.scene;

public abstract class Screen {
    boolean visible;
    int id;

    public void close() {
        this.visible = false;
    }

    public int getId() {
        return this.id;
    }
}
