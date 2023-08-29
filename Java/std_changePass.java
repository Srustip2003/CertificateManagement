import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class std_changePass {
    public static String mID = "";

    public static JFrame mainWindow;
    public static JLabel mainLbl;

    private static JPasswordField oldPass;
    private static JLabel oldPassBck;
    private static JPasswordField newPass;
    private static JLabel newPassBck;
    private static JPasswordField confirmPass;
    private static JLabel confirmpassBck;

    private static JLabel btnChangePass;

    public static void guiChangePass() {
        int fWidth = 310;
        int fHeight = 400;

        mainWindow = new JFrame();
        // mainWindow.getContentPane().setBackground(new Color(225, 225, 225));
        mainWindow.setSize(fWidth, fHeight);

        mainLbl = new JLabel();
        mainLbl.setIcon(new ImageIcon("img/lblOldPassword.png"));
        mainLbl.setBounds((fWidth - 123) / 2, 13, 123, 23);
        mainWindow.add(mainLbl);

        oldPass = new HintPasswordField("Old Pass");
        oldPass.setHorizontalAlignment(JTextField.CENTER);
        oldPass.setBounds((fWidth - 250) / 2, 50, 250, 40);
        oldPass.setOpaque(false);
        oldPass.setBorder(null);
        oldPassBck = new JLabel();
        oldPassBck.setIcon(new ImageIcon("img/editbox.png"));
        oldPassBck.setLayout(new BorderLayout());
        oldPassBck.setBounds((fWidth - 260) / 2, 50, 258, 48);
        mainWindow.add(oldPass);
        mainWindow.add(oldPassBck);

        mainLbl = new JLabel();
        mainLbl.setIcon(new ImageIcon("img/lblNewPassword.png"));
        mainLbl.setBounds((fWidth - 123) / 2, 105, 123, 23);
        mainWindow.add(mainLbl);

        newPass = new HintPasswordField("New Pass");
        newPass.setHorizontalAlignment(JTextField.CENTER);
        newPass.setBounds((fWidth - 250) / 2, 140, 250, 40);
        newPass.setOpaque(false);
        newPass.setBorder(null);
        newPassBck = new JLabel();
        newPassBck.setIcon(new ImageIcon("img/editbox.png"));
        newPassBck.setLayout(new BorderLayout());
        newPassBck.setBounds((fWidth - 260) / 2, 140, 258, 48);
        mainWindow.add(newPass);
        mainWindow.add(newPassBck);

        mainLbl = new JLabel();
        mainLbl.setIcon(new ImageIcon("img/lblConfirmPassword.png"));
        mainLbl.setBounds((fWidth - 150) / 2, 195, 200, 15);
        mainWindow.add(mainLbl);

        confirmPass = new HintPasswordField("Confirm Pass");
        confirmPass.setHorizontalAlignment(JTextField.CENTER);
        confirmPass.setBounds((fWidth - 250) / 2, 220, 250, 40);
        confirmPass.setOpaque(false);
        confirmPass.setBorder(null);
        confirmpassBck = new JLabel();
        confirmpassBck.setIcon(new ImageIcon("img/editbox.png"));
        confirmpassBck.setLayout(new BorderLayout());
        confirmpassBck.setBounds((fWidth - 260) / 2, 220, 258, 48);
        mainWindow.add(confirmPass);
        mainWindow.add(confirmpassBck);

        btnChangePass = new JLabel();
        btnChangePass.setIcon(new ImageIcon("img/btnChangePass.png"));
        btnChangePass.setBounds((fWidth - 163) / 2, 270, 163, 48);
        mainWindow.add(btnChangePass);

        btnChangePass.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selOldPass = String.valueOf(oldPass.getPassword());
                if (selOldPass == null || selOldPass.trim().isEmpty() || selOldPass.equals("Old Pass")) {
                    popupMsg.infoBox("Enter Old Password!", "ERROR");
                    return;
                }
                if (checkOldPassword(mID, selOldPass)) {
                    popupMsg.infoBox("Wrong old password!", "ERROR");
                    return;
                }

                String selNewPass = String.valueOf(newPass.getPassword());
                if (selNewPass == null || selNewPass.trim().isEmpty() || selNewPass.equals("New Pass")) {
                    popupMsg.infoBox("Enter New Password!", "ERROR");
                    return;
                }
                String selConfirmPass = String.valueOf(confirmPass.getPassword());
                if (!selNewPass.equals(selConfirmPass)) {
                    popupMsg.infoBox("New Password didn't match", "ERROR");
                    return;
                }

                changeUserPass(selNewPass);
                mainWindow.dispose();
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

    public static void changeUserPass(String newPass) {
        String dbURL = "jdbc:mysql://127.0.0.1:3306/cms";
        String username = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;

        String query = "UPDATE acc SET pwd = '" + newPass + "' WHERE uid = '" + mID + "'";
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate(query);
            popupMsg.infoBox("Successfully Updated Password!", "SUCCESS");
            conn.close();
        } catch (SQLException ex) {
            popupMsg.infoBox("Error Updating Password!", "Error");
            System.out.println(ex.toString());
        } finally {
        }
    }

    public static boolean checkOldPassword(String mID, String Pass) {
        boolean check = false;
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            Statement stmt = conn.createStatement();
            String query = "SELECT count(*) FROM acc WHERE uid='" + mID + "' AND pwd='" + Pass + "';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int count = rs.getInt(1);
            if (count == 0) {
                check = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            popupMsg.infoBox("Error Checking Password!", "ERROR");
        }
        return check;
    }

    public static void main(String[] args) {
        mID = "20104075";
        guiChangePass();
    }

    public void setVisible(String uName) {
        mID = uName;
        guiChangePass();
    }
}

class HintPasswordField extends JPasswordField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public HintPasswordField(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            showingHint = true;
        }
    }

    @Override
    public String getText() {
        return showingHint ? "" : super.getPassword().toString();
    }
}
