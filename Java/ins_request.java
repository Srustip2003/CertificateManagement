import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;

public class ins_request {
    public static JFrame mainWindow;
    public static JLabel lblTitleAdd;
    public static JLabel lblTitleRemove;

    public static JTextField editACerti;
    public static JLabel editACertiBack;
    public static JComboBox<String> comboACerti;
    public static JLabel btnAddCerti;

    public static JComboBox<String> comboRBranch;
    public static JComboBox<String> comboRCerti;
    public static JLabel btnRemoveCerti;

    public static DefaultComboBoxModel<String> certiList;
    public static ArrayList<String> bList;

    public static void guiRequest() {
        int fWidth = 600;
        int fHeight = 300;

        mainWindow = new JFrame();
        mainWindow.setSize(fWidth, fHeight);

        lblTitleAdd = new JLabel();
        lblTitleAdd.setIcon(new ImageIcon("img/lblAddCertificate.png"));
        lblTitleAdd.setBounds(50, 10, 300, 25);
        mainWindow.add(lblTitleAdd);

        editACerti = new HintTextField("New Certificate Name");
        editACerti.setHorizontalAlignment(JTextField.CENTER);
        editACerti.setBounds(20, 50, 250, 40);
        editACerti.setOpaque(false);
        editACerti.setBorder(null);
        editACertiBack = new JLabel();
        editACertiBack.setIcon(new ImageIcon("img/editbox.png"));
        editACertiBack.setLayout(new BorderLayout());
        editACertiBack.setBounds(20, 50, 258, 48);
        mainWindow.add(editACerti);
        mainWindow.add(editACertiBack);

        getBranchList();
        comboACerti = new JComboBox<String>(new Vector<String>(bList));
        comboACerti.setBounds(20, 105, 250, 40);
        comboACerti.setSelectedIndex(0);
        mainWindow.add(comboACerti);

        btnAddCerti = new JLabel();
        btnAddCerti.setIcon(new ImageIcon("img/btnManageAddCerti.png"));
        btnAddCerti.setBounds(20, 170, 250, 48);
        mainWindow.add(btnAddCerti);

        btnAddCerti.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String cName = editACerti.getText();
                String mBranch = comboACerti.getSelectedItem().toString();
                if (cName == null || cName.trim().isEmpty() || cName.equals("New Certificate Name")) {
                    popupMsg.infoBox("Enter Certificate Name!", "ERROR");
                    return;
                }
                addNewCertificate(cName, mBranch);
            }
        });

        //// REMOVE CERTIFICATE //////

        getCertiList("IT");

        lblTitleRemove = new JLabel();
        lblTitleRemove.setIcon(new ImageIcon("img/lblRemoveCertificate.png"));
        lblTitleRemove.setBounds(350, 10, 290, 25);
        mainWindow.add(lblTitleRemove);

        comboRBranch = new JComboBox<String>(new Vector<String>(bList));
        comboRBranch.setBounds(310, 50, 250, 40);
        comboRBranch.setSelectedIndex(0);
        mainWindow.add(comboRBranch);

        comboRBranch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filter = comboRBranch.getSelectedItem().toString();
                comboRCerti.removeAllItems();
                getCertiList(filter);
                comboRCerti.setModel(certiList);
            }
        });

        comboRCerti = new JComboBox<String>(certiList);
        comboRCerti.setBounds(310, 105, 250, 40);
        comboRCerti.setSelectedIndex(0);
        mainWindow.add(comboRCerti);

        btnRemoveCerti = new JLabel();
        btnRemoveCerti.setIcon(new ImageIcon("img/btnManageRemoveCerti.png"));
        btnRemoveCerti.setBounds(310, 170, 250, 48);
        mainWindow.add(btnRemoveCerti);

        btnRemoveCerti.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String selBranch = comboRBranch.getSelectedItem().toString();
                if (comboRCerti.getItemCount() == 0) {
                    popupMsg.infoBox("No Certificate found!", "ERROR");
                    return;
                }
                String selCerti = comboRCerti.getSelectedItem().toString();
                removeCertificate(selBranch, selCerti);
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        mainWindow.setLocation(dim.width / 2 - mainWindow.getSize().width / 2,
                dim.height / 3 - mainWindow.getSize().height / 2);
        // mainWindow.setUndecorated(true);
        mainWindow.setLayout(null);
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
        mainWindow.requestFocusInWindow();
    }

    public static void addNewCertificate(String cName, String mBranch) {
        String dbURL = "jdbc:mysql://127.0.0.1:3306/cms";
        String username = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;

        String query = "INSERT INTO c_list (branch, certi) value ('" + mBranch + "', '" + cName + "')";
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate(query);
            popupMsg.infoBox("Successfully Added!", "SUCCESS");
            String selBranch = comboRBranch.getSelectedItem().toString();
            comboRCerti.removeAllItems();
            getCertiList(selBranch);
            comboRCerti.setModel(certiList);
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } finally {
        }
    }

    public static void getBranchList() {
        bList = new ArrayList<String>();
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";
        String query = "SELECT branch FROM b_list";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                // Display values
                bList.add(rs.getString("branch"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getCertiList(String selBranch) {
        certiList = new DefaultComboBoxModel<String>();
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        String query = "SELECT certi FROM c_list WHERE branch = '" + selBranch + "'";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                // Display values
                certiList.addElement(rs.getString("certi"));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCertificate(String selBranch, String selCerti) {
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";
        String query = "DELETE FROM c_List WHERE branch = '" + selBranch + "' && certi = '" + selCerti + "';";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            try (Statement stmt = conn.createStatement()) {
                // cList.remove(index);
                stmt.executeUpdate(query);
                popupMsg.infoBox("Successfully Deleted!", "REMOVED");
                comboRCerti.removeAllItems();
                getCertiList(selBranch);
                comboRCerti.setModel(certiList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        guiRequest();
    }

    public void setVisible(boolean b) {
        guiRequest();
    }
}
