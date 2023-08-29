import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import org.apache.commons.io.FileUtils;

public class std_certiList {
    public static String mID = "";
    public static String mBranch = "";

    public static JFrame mainWindow;
    public static JList<String> certiList;
    public static DefaultListModel<String> cList;

    public static JLabel btnDelete;
    public static JLabel btnView;
    public static JLabel btnDownload;

    public static void guiUpload() {
        int fWidth = 310;
        int fHeight = 550;

        mainWindow = new JFrame();
        mainWindow.getContentPane().setBackground(new Color(45, 156, 219));
        mainWindow.setSize(fWidth, fHeight);

        cList = new DefaultListModel<>();
        getStudentCertificate();

        certiList = new JList<>(cList);
        certiList.setBounds((fWidth - 250) / 2, 15, 250, 320);
        mainWindow.add(certiList);

        btnDelete = new JLabel();
        btnDelete.setIcon(new ImageIcon("img/btnRemoveDoc.png"));
        btnDelete.setBounds((fWidth - 258) / 2, 350, 258, 48);
        mainWindow.add(btnDelete);

        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = certiList.getSelectedIndex();
                String certi = (String) certiList.getSelectedValue();
                deleteCertificate(index, certi);
            }
        });

        btnView = new JLabel();
        btnView.setIcon(new ImageIcon("img/btnViewDoc.png"));
        btnView.setBounds((fWidth - 258) / 2, 410, 258, 48);
        mainWindow.add(btnView);

        btnView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String certi = (String) certiList.getSelectedValue();
                viewCertificate(certi);
            }
        });

        btnDownload = new JLabel();
        btnDownload.setIcon(new ImageIcon("img/btnDownloadDock.png"));
        btnDownload.setBounds((fWidth - 258) / 2, 460, 258, 48);
        mainWindow.add(btnDownload);

        btnDownload.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String certi = (String) certiList.getSelectedValue();
                String url = replaceURL(getURLLink(certi));

                File source = new File(url);
                File dest = new File("C:\\Users\\akash\\Downloads\\" + mID + ".jpg");
                try {
                    FileUtils.copyFile(source, dest);
                    popupMsg.infoBox("Successfully Download In Downloads Folder!", "DOWNLOADED");
                } catch (IOException e1) {
                    popupMsg.infoBox("Error Downloading File!", "ERROR");
                    e1.printStackTrace();
                }
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(dim.width / 2 - mainWindow.getSize().width / 2,
                dim.height / 3 - mainWindow.getSize().height / 2);
        mainWindow.setLayout(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }

    public static void viewCertificate(String certi) {
        viewFile frame = new viewFile();
        frame.setVisible(replaceURL(getURLLink(certi)));
    }

    public static String getURLLink(String certi) {
        String url = "";

        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            try (Statement stmt = conn.createStatement()) {
                String query = "SELECT c_link FROM std_certi WHERE uid='" + mID + "' && certi='" + certi + "';";
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                url = rs.getString(1);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String replaceURL(String uri) {
        String url = uri.replace("$", "\\");
        return url;
    }

    public static void deleteCertificate(int index, String certi) {
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";
        String query = "DELETE FROM std_certi WHERE uid = '" + mID + "' && certi = '" + certi + "';";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            try (Statement stmt = conn.createStatement()) {
                // cList.remove(index);
                stmt.executeUpdate(query);
                cList.remove(index);
                certiList.setModel(cList);
                popupMsg.infoBox("Successfully Deleted!", "REMOVED");
            } catch (SQLException e) {
                popupMsg.infoBox("Error Deleting File!", "Error");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getStudentCertificate() {
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        String query = "SELECT * FROM std_certi WHERE uid = '" + mID + "';";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                // Display values
                cList.addElement(rs.getString("certi"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        mID = "M001";
        guiUpload();
    }

    public void setVisible(String uName, String branch) {
        mBranch = branch;
        mID = uName;
        guiUpload();
    }
}
