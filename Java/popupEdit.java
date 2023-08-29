import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class popupEdit {
    static JFrame mainWindow;

    public static void editbox(String mID, String edit, int charCount) {
        int fWidth = 300;
        int fHeight = 220;

        mainWindow = new JFrame();
        mainWindow.setSize(fWidth, fHeight);

        JTextField editText = new HintTextField("New " + edit);
        editText.setHorizontalAlignment(JTextField.CENTER);
        editText.setBounds(20, 50, 250, 40);
        editText.setOpaque(false);
        editText.setBorder(null);
        JLabel editTextBack = new JLabel();
        editTextBack.setIcon(new ImageIcon("img/editbox.png"));
        editTextBack.setLayout(new BorderLayout());
        editTextBack.setBounds(20, 50, 258, 48);
        mainWindow.add(editText);
        mainWindow.add(editTextBack);

        JLabel btnEdit = new JLabel();
        btnEdit.setIcon(new ImageIcon("img/btnSave.png"));
        btnEdit.setBounds(20, 105, 250, 48);
        mainWindow.add(btnEdit);

        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String cName = editText.getText();
                if (cName == null || cName.trim().isEmpty() || cName.equals("New " + edit)) {
                    popupMsg.infoBox("Enter New " + edit + "!", "ERROR");
                    return;
                }
                changeUserDetails(mID, edit, cName);
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(dim.width / 2 - mainWindow.getSize().width / 2,
                dim.height / 3 - mainWindow.getSize().height / 2);
        mainWindow.setLayout(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.requestFocusInWindow();
    }

    public static void changeUserDetails(String mID, String edit, String cName) {
        String dbURL = "jdbc:mysql://127.0.0.1:3306/cms";
        String username = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;

        String query = "UPDATE acc SET " + edit + " = '" + cName + "' WHERE uid = '" + mID + "'";
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate(query);
            popupMsg.infoBox("Successfully Updated!", "SUCCESS");
            std_profile.getProfileDetails(mID);
            std_profile.updateProfileDetails();
            mainWindow.dispose();
            conn.close();
        } catch (SQLException ex) {
            popupMsg.infoBox("Error Updating!", "ERROR");
            System.out.println(ex.toString());
        } finally {
        }
    }
}
