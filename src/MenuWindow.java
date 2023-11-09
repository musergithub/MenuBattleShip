import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuWindow extends JFrame {
    private MenuPanel menuPanel;
    public MenuWindow()
    {
        final int WIDTH = 500;
        final int HEIGHT = 800;
        setTitle("Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        ImageIcon icon = new ImageIcon(getClass().getResource("/Menu_Icon.png"));
        setIconImage(icon.getImage());

        try {
            BufferedImage backgroundImage = ImageIO.read(getClass().getResource("/Main_Background.png"));
            Image scaledImage = backgroundImage.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            menuPanel = new MenuPanel(scaledImage);
            setContentPane(menuPanel);
        } catch (IOException e) {
            System.out.println("Background image loading error.");
        }

        setVisible(true);
    }
}
