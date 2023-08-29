import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class std_profile {
    public static String mID = "";
    public static String profileName = "";
    public static String profileBranch = "";
    public static String profileRoll = "";
    public static String profileContact = "";
    public static String profileLink = "";

    public static JFrame mainWindow;

    public static JLabel labelBck;
    public static JLabel btnClose;

    public static JLabel btnChangePassword;

    public static JLabel lblMoodleID;
    public static JLabel lblName;
    public static JLabel lblBranch;
    public static JLabel lblContact;
    public static JLabel lblRoll;

    public static JLabel editName;
    public static JLabel editRoll;
    public static JLabel editContact;

    public static void guiProfile() {
        int fWidth = 687;
        int fHeight = 365;

        getProfileDetails(mID);

        mainWindow = new JFrame();
        mainWindow.getContentPane().setBackground(new Color(45, 156, 219));
        mainWindow.setSize(fWidth, fHeight);

        lblMoodleID = new JLabel();
        lblMoodleID.setText(mID);
        lblMoodleID.setFont(new java.awt.Font("Tahoma", 1, 15));
        lblMoodleID.setBounds(403, 83, 277, 40);
        mainWindow.add(lblMoodleID);

        lblName = new JLabel();
        lblName.setText(profileName);
        lblName.setFont(new java.awt.Font("Tahoma", 1, 15));
        lblName.setBounds(403, 133, 223, 40);
        mainWindow.add(lblName);

        lblBranch = new JLabel();
        lblBranch.setText(profileBranch);
        lblBranch.setFont(new java.awt.Font("Tahoma", 1, 15));
        lblBranch.setBounds(403, 183, 277, 40);
        mainWindow.add(lblBranch);

        lblRoll = new JLabel();
        lblRoll.setText(profileRoll);
        lblRoll.setFont(new java.awt.Font("Tahoma", 1, 15));
        lblRoll.setBounds(403, 233, 223, 40);
        mainWindow.add(lblRoll);

        lblContact = new JLabel();
        lblContact.setText(profileContact);
        lblContact.setFont(new java.awt.Font("Tahoma", 1, 15));
        lblContact.setBounds(403, 284, 223, 40);
        mainWindow.add(lblContact);

        btnClose = new JLabel();
        btnClose.setIcon(new ImageIcon("img/btnCloseBlue.png"));
        btnClose.setBounds(15, 23, 50, 50);
        mainWindow.add(btnClose);

        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mainWindow.dispose();
            }
        });

        btnChangePassword = new JLabel();
        btnChangePassword.setIcon(new ImageIcon("img/btnChangePass.png"));
        btnChangePassword.setBounds(507, 19, 163, 48);
        mainWindow.add(btnChangePassword);

        btnChangePassword.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                std_changePass frame = new std_changePass();
                frame.setVisible(mID);
            }
        });

        editName = new JLabel();
        editName.setBounds(631, 133, 40, 40);
        mainWindow.add(editName);

        editName.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                popupEdit.editbox(mID, "name", 0);
            }
        });

        editRoll = new JLabel();
        editRoll.setBounds(631, 233, 40, 40);
        mainWindow.add(editRoll);

        editRoll.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                popupEdit.editbox(mID, "roll", 0);
            }
        });

        editContact = new JLabel();
        editContact.setBounds(631, 286, 40, 40);
        mainWindow.add(editContact);

        editContact.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                popupEdit.editbox(mID, "contact", 10);
            }
        });

        labelBck = new JLabel();
        labelBck.setIcon(new ImageIcon("img/bckProfile.png"));
        labelBck.setBounds(0, 0, fWidth, fHeight);
        mainWindow.add(labelBck);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(dim.width / 2 - mainWindow.getSize().width / 2,
                dim.height / 3 - mainWindow.getSize().height / 2);
        mainWindow.setUndecorated(true);
        mainWindow.setLayout(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.requestFocusInWindow();

    }

    public static void getProfileDetails(String mID) {
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM acc WHERE uid='" + mID + "';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            profileName = rs.getString("name");
            profileBranch = rs.getString("branch");
            profileRoll = rs.getString("roll");
            profileContact = rs.getString("contact");
            profileLink = rs.getString("p_link");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProfileDetails() {
        lblName.setText(profileName);
        lblRoll.setText(profileRoll);
        lblContact.setText(profileContact);
    }

    public static void main(String[] args) {
        mID = "20104075";
        guiProfile();
    }

    public void setVisible(String uName) {
        mID = uName;
        guiProfile();
    }
}
