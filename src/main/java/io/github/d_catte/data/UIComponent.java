package io.github.d_catte.data;

import io.github.d_catte.TrailApplication;
import io.github.d_catte.utils.Command;
import io.github.d_catte.utils.Text;
import io.github.d_catte.utils.rendering.ComponentType;
import io.github.d_catte.utils.rendering.RenderGroup;
import io.github.d_catte.utils.rendering.RenderUtils;
import io.github.d_catte.utils.rendering.TrailRenderer;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.Optional;

public class UIComponent {
    public ComponentType type;
    public int[] position;
    public Optional<int[]> size;
    public Optional<Text> text;
    public Optional<Command[]> commands;
    public Optional<URI> imageURI;
    public Optional<RenderGroup> group;

    public UIComponent(ComponentType type, int[] position, Optional<int[]> size, Optional<Text> text, Optional<URI> imageURI, Optional<RenderGroup> group, Optional<Command[]> commands) {
        this.type = type;
        this.position = position;
        this.size = size;
        this.text = text;
        this.imageURI = imageURI;
        this.commands = commands;
        this.group = group;
    }

    public UIComponent(ComponentType type, double[] relativePosition, int[] windowSize, Optional<int[]> size, Optional<Text> text, Optional<URI> imageURI, Optional<RenderGroup> group, Optional<Command[]> commands) {
        this(type, RenderUtils.relativeToAbsolutePositioning(windowSize[0], windowSize[1], relativePosition[0], relativePosition[1]), size, text, imageURI, group, commands);
    }

    private UIComponent(Builder builder) {
        this.type = builder.type;
        this.position = builder.position;
        this.size = Optional.ofNullable(builder.size);
        this.text = Optional.ofNullable(builder.text);
        this.commands = Optional.ofNullable(builder.commands);
        this.imageURI = Optional.ofNullable(builder.imageUri);
        this.group = Optional.ofNullable(builder.renderGroup);
    }

    public static class Builder {
        private ComponentType type;
        private int[] position = new int[] {0, 0};
        private int[] size;
        private Text text;
        private Command[] commands;
        private URI imageUri;
        private RenderGroup renderGroup;

        public Builder setType(ComponentType type) {
            this.type = type;
            return this;
        }

        public Builder setPosition(double relativeX, double relativeY, int[] windowSize) {
            this.position = RenderUtils.relativeToAbsolutePositioning(windowSize[0], windowSize[1], relativeX, relativeY);
            return this;
        }

        public Builder setPosition(int x, int y) {
            this.position = new int[] {x, y};
            return this;
        }

        public Builder setSize(int[] size) {
            this.size = size;
            return this;
        }

        public Builder setText(Text text) {
            this.text = text;
            return this;
        }

        public Builder setCommand(Command... commands) {
            this.commands = commands;
            return this;
        }

        public Builder setImageUri(URI imageUri) {
            this.imageUri = imageUri;
            return this;
        }

        public Builder setRenderGroup(RenderGroup renderGroup) {
            this.renderGroup = renderGroup;
            return this;
        }

        public UIComponent build() {
            return new UIComponent(this);
        }
    }

    /**
     * Executes all Commands attached to this UI.
     * This should be run when a callback is called
     */
    public void executeCommands() {
        this.commands.ifPresent(
                executables -> {
                    for (Command command : executables) {
                        command.parseCommand().run();
                    }
                }
        );
    }

    /**
     * Renders the UIComponent
     * @param renderer TrailRenderer instance
     * @return Object that was rendered.
     *         This object must be added to whatever the parent was if using Swing.
     *         If using ImGUI, this Object may be null
     */
    @Nullable
    public Object render(TrailRenderer renderer) {
        switch (this.type) {
            case Button -> {
                if (this.size.isEmpty()) {
                    if (this.text.isEmpty()) {
                        TrailApplication.LOGGER.warn("Missing or Invalid Size for UIComponent");
                        return null;
                    } else {
                        this.size = Optional.of(renderer.calculateTextSize(this.text.get()));
                    }
                }

                if (this.text.isEmpty()) {
                    this.text = Optional.of(RenderUtils.EMPTY_TEXT);
                }

                checkForEmptyCommands();
                return renderer.renderButton(this.position[0], this.position[1], this.size.get()[0], this.size.get()[1], this.text.get(), this.commands.get());
            }
            case Text -> {
                if (this.text.isEmpty()) {
                    TrailApplication.LOGGER.warn("Missing or Invalid Text for UIComponent");
                    return null;
                }
                return renderer.renderText(this.position[0], this.position[1], this.text.get());
            }
            case TextField -> {
                if (this.text.isEmpty()) {
                    TrailApplication.LOGGER.warn("Missing or Invalid Text for UIComponent");
                    return null;
                }

                checkForEmptyCommands();
                return renderer.renderTextField(this.position[0], this.position[1], this.text.get(), this.commands.get());
            }
            case List -> {
                if (this.size.isEmpty()) {
                    TrailApplication.LOGGER.warn("Missing or Invalid Size for UIComponent");
                    return null;
                }

                if (this.group.isEmpty()) {
                    TrailApplication.LOGGER.warn("Missing or Invalid Group for UIComponent");
                    return null;
                }

                return renderer.renderList(this.position[0], this.position[1], this.size.get()[0], this.size.get()[1], this.group.get());
            }
            case Image -> {
                if (this.size.isEmpty()) {
                    TrailApplication.LOGGER.warn("Missing or Invalid Size for UIComponent");
                    return null;
                }

                if (this.imageURI.isEmpty()) {
                    TrailApplication.LOGGER.warn("Missing or Invalid URI for UIComponent");
                    return null;
                }

                return renderer.renderImage(this.position[0], this.position[1], this.size.get()[0], this.size.get()[1], this.imageURI.get());
            }
            default -> {
                return null;
            }
        }
    }

    private void checkForEmptyCommands() {
        if (this.commands.isEmpty()) {
            this.commands = Optional.of(new Command[0]);
        }
    }
}
