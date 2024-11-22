package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Represents a splash screen that is shown for three seconds once the user open the app
public class SplashScreen extends JWindow {
    private BufferedImage myPicture;

    public SplashScreen() {
        try {
            myPicture = ImageIO.read(new File("src/main/Image/fms.png"));
        } catch (IOException e) {
            System.out.println("Pic not Found");
        }

        ImageIcon splashImage = new ImageIcon(myPicture);
        JLabel imageLabel = new JLabel(splashImage);
        getContentPane().add(imageLabel);

        setSize(splashImage.getIconWidth(), splashImage.getIconHeight());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(
                (screenSize.width - getWidth()) / 2,
                (screenSize.height - getHeight()) / 2);
    }

    // EFFECTS: make the splash screen visible to user once they openned the app
    public void showSplash(int time) {
        setVisible(true);

        Timer timer = new Timer(time, e -> {
            setVisible(false);
            dispose();
        });

        timer.setRepeats(false);
        timer.start();
    }
}


