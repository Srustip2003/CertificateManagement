import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class student {
    public static String mID = "";
    public static String mBranch = "";
    public static String dName = "";

    public static JFrame mainWindow;
    public static JLabel btnLogout;
    public static JLabel btnClose;
    public static JLabel btnProfile;
    public static JLabel btnDocs;
    public static JLabel btnList;
    public static JLabel lblWelcome;
    // private static JLabel lblDate;
    // private static JLabel lblTime;
    public static JLabel lblTopNav;

    public static void guiStudent() throws SQLException {
        int fWidth = 675;
        int fHeight = 392;

        mainWindow = new JFrame();
        mainWindow.getContentPane().setBackground(new Color(225, 225, 225));
        mainWindow.setSize(fWidth, fHeight);

        lblWelcome = new JLabel();
        lblWelcome.setFont(new Font("Tahoma", 1, 20));
        lblWelcome.setText("Welcome, " + dName);
        lblWelcome.setBounds(10, 50, 500, 50);
        mainWindow.add(lblWelcome);

        btnClose = new JLabel();
        btnClose.setIcon(new ImageIcon("img/btnClose.png"));
        btnClose.setBounds(640, 2, 50, 50);
        mainWindow.add(btnClose);
        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mainWindow.dispose();
            }
        });

        btnLogout = new JLabel();
        btnLogout.setIcon(new ImageIcon("img/btnLogout.png"));
        btnLogout.setBounds(10, 18, 80, 23);
        mainWindow.add(btnLogout);

        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mainWindow.dispose();
                initBoot nFrame = new initBoot();
                nFrame.setVisible(true);

            }
        });

        lblTopNav = new JLabel();
        lblTopNav.setIcon(new ImageIcon("img/lblTopNav.png"));
        lblTopNav.setBounds(0, 0, 675, 59);
        mainWindow.add(lblTopNav);

        btnProfile = new JLabel();
        btnProfile.setIcon(new ImageIcon("img/btnProfile.png"));
        btnProfile.setBounds(140, 123, 114, 141);
        mainWindow.add(btnProfile);

        btnProfile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                std_profile nFrame = new std_profile();
                nFrame.setVisible(mID);
            }
        });

        btnDocs = new JLabel();
        btnDocs.setIcon(new ImageIcon("img/btnUpload.png"));
        btnDocs.setBounds(286, 123, 114, 141);
        mainWindow.add(btnDocs);

        btnDocs.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                std_upload nFrame = new std_upload();
                nFrame.setVisible(mID, mBranch);
            }
        });

        btnList = new JLabel();
        btnList.setIcon(new ImageIcon("img/btnDocs.png"));
        btnList.setBounds(432, 123, 114, 141);
        mainWindow.add(btnList);

        btnList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                std_certiList nFrame = new std_certiList();
                nFrame.setVisible(mID, mBranch);
            }
        });

        getUserBranch();

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(dim.width / 2 - mainWindow.getSize().width / 2,
                dim.height / 3 - mainWindow.getSize().height / 2);
        mainWindow.setUndecorated(true);
        mainWindow.setLayout(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }

    public static void getUserBranch() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        Connection conn = DriverManager.getConnection(dbURL, username, password);
        Statement stmt = conn.createStatement();
        String query = "SELECT branch FROM acc WHERE uid='" + mID + "';";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        mBranch = rs.getString(1);
        conn.close();
    }

    public static void main(String[] args) throws SQLException {
        guiStudent();
        mID = "20104075";
    }

    public void setVisible(String uName, String displayName) throws SQLException {
        mID = uName;
        dName = displayName;
        guiStudent();
    }
}
