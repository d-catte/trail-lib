package io.github.d_catte.utils.rendering;

import io.github.d_catte.TrailApplication;
import io.github.d_catte.utils.Command;
import io.github.d_catte.utils.Text;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class SwingRenderer implements TrailRenderer {
    private JFrame frame;

    public SwingRenderer() {
    }

    @Override
    public void createWindow(double width, double height, String screenTitle) {
        frame = new JFrame(screenTitle);
        frame.setSize((int) width, (int) height);
        frame.setLayeredPane(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void updateUI() {
        frame.repaint();
    }

    @Override
    public void clearUI() {
        if (frame != null) {
            frame.getContentPane().removeAll();
            frame.revalidate();
            frame.repaint();
        }
    }

    @Override
    public Object renderButton(float relativePosX, float relativePosY, double width, double height, Text text, Command[] clickCallback) {
        JButton button = new JButton(text.textString);
        int[] pos = RenderUtils.relativeToAbsolutePositioning(frame.getWidth(), frame.getHeight(), relativePosX, relativePosY);
        RenderUtils.centeredCoordinates(pos, (int) width, (int) height);
        button.setBounds(pos[0], pos[1], (int) width, (int) height);
        button.addActionListener(e -> {
            for (Command command : clickCallback) {
                command.parseCommand().run();
            }
        });
        this.frame.add(button);
        return button;
    }

    @Override
    public Object renderText(float relativePosX, float relativePosY, Text text) {
        JLabel label = new JLabel(text.textString);
        int[] pos = RenderUtils.relativeToAbsolutePositioning(frame.getWidth(), frame.getHeight(), relativePosX, relativePosY);
        int[] size = calculateTextSize(text);
        RenderUtils.centeredCoordinates(pos, size[0], size[1]);
        label.setBounds(pos[0], pos[1], size[0], size[1]);
        this.frame.add(label);
        return label;
    }

    @Override
    public Object renderTextField(float relativePosX, float relativePosY, Text text, Command[] enterCallback) {
        JTextField textField = new JTextField(text.textString);
        int[] pos = RenderUtils.relativeToAbsolutePositioning(frame.getWidth(), frame.getHeight(), relativePosX, relativePosY);
        int[] size = calculateTextSize(text);
        RenderUtils.centeredCoordinates(pos, size[0], size[1]);
        textField.setBounds(pos[0], pos[1], size[0], size[1]);
        textField.addActionListener(e -> {
            for (Command command : enterCallback) {
                command.parseCommand().run();
            }
        });
        this.frame.add(textField);
        return textField;
    }

    @Override
    public Object renderList(float relativePosX, float relativePosY, double width, double height, RenderGroup visuals) {
        JPanel panel = (JPanel) visuals.render(this);
        int[] pos = RenderUtils.relativeToAbsolutePositioning(frame.getWidth(), frame.getHeight(), relativePosX, relativePosY);
        RenderUtils.centeredCoordinates(pos, (int) width, (int) height);
        panel.setBounds(pos[0], pos[1], (int) width, (int) height);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(pos[0], pos[1], (int) width, (int) height);
        this.frame.add(panel);
        return panel;
    }

    @Override
    public Object renderImage(float relativePosX, float relativePosY, double width, double height, URI imageURI) {
        JLabel imageLabel = null;
        try {
            ImageIcon imageIcon = new ImageIcon(imageURI.toURL());
            Image image = imageIcon.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(image));
            int[] pos = RenderUtils.relativeToAbsolutePositioning(frame.getWidth(), frame.getHeight(), relativePosX, relativePosY);
            RenderUtils.centeredCoordinates(pos, (int) width, (int) height);
            imageLabel.setBounds(pos[0], pos[1], (int) width, (int) height);
        } catch (Exception e) {
            TrailApplication.LOGGER.error("Failed to render image: ", e);
        }
        this.frame.add(imageLabel);
        return imageLabel;
    }

    @Override
    public int[] calculateTextSize(Text text) {
        // Create a temporary component to get FontMetrics
        JLabel label = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, text.fontSize);
        FontMetrics metrics = label.getFontMetrics(font);

        // Calculate the width and height of the text
        int width = metrics.stringWidth(text.textString);
        int height = metrics.getHeight();

        return new int[]{width, height};
    }
}
