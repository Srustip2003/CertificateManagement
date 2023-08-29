import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class std_upload {
    public static String mID = "";
    public static String mBranch = "IT";
    public static ArrayList<String> cList;

    public static String uriText = "Click here to select File";
    public static String uriNew = "";
    public static String certiName = "";
    public static String fileExt = "";

    public static JFrame mainWindow;
    public static JLabel lblTitle;
    public static JLabel lblLink;
    public static JComboBox<String> comboCertiList;
    public static JLabel lblURI;
    public static JLabel btnUpload;

    public static void guiUpload() {
        int fWidth = 309;
        int fHeight = 301;

        mainWindow = new JFrame();
        mainWindow.setSize(fWidth, fHeight);

        lblTitle = new JLabel();
        lblTitle.setIcon(new ImageIcon("img/lblUploadCerti.png"));
        lblTitle.setBounds((fWidth - 190) / 2, 10, 190, 25);
        mainWindow.add(lblTitle);

        getCertificateList();
        comboCertiList = new JComboBox<String>(new Vector<String>(cList));
        comboCertiList.setBounds((fWidth - 190) / 2, 45, 190, 30);
        comboCertiList.setSelectedIndex(0);
        mainWindow.add(comboCertiList);

        lblLink = new JLabel();
        lblLink.setFont(new java.awt.Font("Tahoma", 1, 15));
        lblLink.setText("<HTML><U>" + uriText + "</U></HTML>");
        lblLink.setBounds((fWidth - 309) / 2, 90, 309, 20);
        lblLink.setHorizontalAlignment(SwingConstants.CENTER);
        mainWindow.add(lblLink);

        lblLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg");
                fileChooser.setFileFilter(imageFilter);
                int option = fileChooser.showOpenDialog(mainWindow);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    uriText = file.getAbsolutePath();
                    lblLink.setText("<HTML><U>" + uriText + "</U></HTML>");
                    fileExt = FilenameUtils.getExtension(uriText);
                } else {
                    System.out.println("Open command canceled");
                }
            }
        });

        btnUpload = new JLabel();
        btnUpload.setIcon(new ImageIcon("img/btnUploadDoc.png"));
        btnUpload.setBounds((fWidth - 258) / 2, 115, 258, 48);
        mainWindow.add(btnUpload);

        btnUpload.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (comboCertiList.getItemCount() == 0) {
                    popupMsg.infoBox("Select Certificate!", "ERROR");
                    return;
                }
                if (certiName.equals("Click here to select File")) {
                    popupMsg.infoBox("Select File Path!", "ERROR");
                    return;
                }
                uploadFile();
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(dim.width / 2 - mainWindow.getSize().width / 2,
                dim.height / 3 - mainWindow.getSize().height / 2);
        mainWindow.setLayout(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }

    public static void getCertificateList() {
        cList = new ArrayList<String>();
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";
        String query = "SELECT * FROM c_list WHERE branch = '" + mBranch + "';";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                // Display values
                cList.add(rs.getString("certi"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile() {
        String dbURL = "jdbc:mysql://127.0.0.1:3306/cms";
        String username = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;

        certiName = comboCertiList.getSelectedItem().toString();

        if (!certiName.equals("Click here to select File")) {
            addFileToServer();
            String query = "INSERT INTO std_certi (uid, certi, c_link) value ('" + mID + "', '" + certiName
                    + "', '" + uriNew + "')";
            try {
                conn = DriverManager.getConnection(dbURL, username, password);
                stmt = conn.prepareStatement(query);
                stmt.executeUpdate(query);
                popupMsg.infoBox("Successfully Uploaded File!", "SUCCESS");
                conn.close();
            } catch (SQLException ex) {
                popupMsg.infoBox("Error Uploading File!", "ERROR");
                System.out.println(ex.toString());
            } finally {
            }
        }
    }

    public static void addFileToServer() {
        File source = new File(uriText);
        String tempURL = "E:\\Akash\\Java\\Java\\docs\\" + mBranch + "\\" + certiName + "\\" + mID + "." + fileExt;
        uriNew = "E:$Akash$Java$Java$docs$" + mBranch + "$" + certiName + "$" + mID + "." + fileExt;
        File dest = new File(tempURL);
        try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
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
