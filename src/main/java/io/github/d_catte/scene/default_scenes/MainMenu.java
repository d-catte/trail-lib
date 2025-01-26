package io.github.d_catte.scene.default_scenes;

import io.github.d_catte.TrailApplication;
import io.github.d_catte.data.UIComponent;
import io.github.d_catte.scene.Scene;
import io.github.d_catte.scene.Screen;
import io.github.d_catte.utils.Command;
import io.github.d_catte.utils.Text;
import io.github.d_catte.utils.TranslatableText;
import io.github.d_catte.utils.Translations;
import io.github.d_catte.utils.rendering.ComponentType;
import io.github.d_catte.utils.rendering.RenderGroup;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Scene {
    private int index;
    private boolean finished;

    @Override
    public List<Screen> getScreens() {
        return createScreens();
    }

    @Override
    public int getScreenIndex() {
        return this.index;
    }

    @Override
    public void setScreenIndex(int i) {
        this.index = i;
    }

    @Override
    public boolean finished() {
        return this.finished;
    }

    @Override
    public void setFinished() {
        this.finished = true;
    }

    private List<Screen> createScreens() {
        List<Screen> screens = new ArrayList<>();

        // Introduction Screen
        screens.add(new Screen() {
            private int[] size;
            @Override
            public List<String> getProfessionShownList() {
                return null;
            }

            @Override
            public List<UIComponent> getUIComponents() {
                return intro(getSize());
            }

            @Override
            public int[] getSize() {
                return this.size;
            }

            @Override
            public void setSize(int width, int height) {
                this.size[0] = width;
                this.size[1] = height;
            }
        });
        // Game Configuration Screen
        screens.add(new Screen() {
            private int[] size;
            @Override
            public List<String> getProfessionShownList() {
                return null;
            }

            @Override
            public List<UIComponent> getUIComponents() {
                return loadGame(getSize());
            }

            @Override
            public int[] getSize() {
                return this.size;
            }

            @Override
            public void setSize(int width, int height) {
                this.size[0] = width;
                this.size[1] = height;
            }
        });

        return screens;
    }

    private List<UIComponent> intro(int[] size) {
        List<UIComponent> components = new ArrayList<>();
        // Title String
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Text)
                .setPosition(0.5, 0.1, size)
                .setText(new TranslatableText(
                        Translations.TITLE,
                        Color.BLACK,
                        24
                ))
                .build()
        );
        // Continue Button
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Button)
                .setPosition(0.5, 0.5, size)
                .setText(new TranslatableText(
                        Translations.CONTINUE,
                        Color.BLACK,
                        16
                ))
                .setCommand(
                        new Command("goTo 1") // This will go to the save-file selection
                )
                .build()
        );
        // Start Button
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Button)
                .setPosition(0.5, 0.65, size)
                .setText(new TranslatableText(
                        Translations.NEW_GAME,
                        Color.BLACK,
                        16
                ))
                .setCommand(
                        new Command("goTo 2") // This will go to the game editor
                )
                .build()
        );
        // Version Number
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Text)
                .setPosition(0.9, 0.9, size)
                .setText(new Text(
                        TrailApplication.VERSION,
                        Color.BLACK,
                        8
                ))
                .build()
        );
        return components;
    }

    private List<UIComponent> loadGame(int[] size) {
        List<UIComponent> components = new ArrayList<>();

        // Title
        components.add(new UIComponent.Builder()
                .setType(ComponentType.Text)
                .setPosition(0.5, 0.1, size)
                .setText(new TranslatableText(
                        Translations.TITLE_2,
                        Color.BLACK,
                        24
                ))
                .build()
        );
        // List
        components.add(new UIComponent.Builder()
                .setType(ComponentType.List)
                .setPosition(0.5, 0.6, size)
                .setSize(new int[] {300, 500})
                .setRenderGroup(
                        new RenderGroup(
                                createGameSaveButtons()
                        )
                )
                .build()
        );
        return components;
    }

    private UIComponent[] createGameSaveButtons() {
        UIComponent[] components = new UIComponent[TrailApplication.SAVES.length];
        for (int i = 0; i < TrailApplication.SAVES.length; i++) {
            String name = TrailApplication.SAVES[i];
            components[i] = new UIComponent.Builder()
                    .setType(ComponentType.Button)
                    .setPosition(0.5, 0.5, new int[] {300, 500})
                    .setText(new Text(
                            name
                    ))
                    .setCommand(
                            new Command("loadGame", name)
                    )
                    .build();
        }
        return components;
    }
}
