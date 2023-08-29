import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;

public class viewFile {
    public static void guiViewFile(String url) {
        int fWidth = 0;
        int fHeight = 0;

        System.out.println(url);

        JFrame mainWindow = new JFrame();
        try {
            BufferedImage bimg = ImageIO.read(new File(url));
            fWidth = bimg.getWidth();
            fHeight = bimg.getHeight();
            mainWindow.setSize(fWidth, fHeight);

            JLabel doc = new JLabel();
            doc.setIcon(new ImageIcon(url));
            doc.setBounds(0, 0, fWidth, fHeight);
            mainWindow.add(doc);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            mainWindow.setLocation(dim.width / 2 - mainWindow.getSize().width / 2,
                    dim.height / 2 - mainWindow.getSize().height / 2);
            mainWindow.setLayout(null);
            mainWindow.setResizable(false);
            mainWindow.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setVisible(String url) {
        guiViewFile(url);
    }
}
