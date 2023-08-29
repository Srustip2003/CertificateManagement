import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;

public class ins_listStudent {
    public static JFrame mainWindow;
    public static JLabel lblListStudent;
    public static DefaultTableModel tableModel;
    public static JTable stdTable;
    public static JComboBox<String> branchCombo;
    public static ArrayList<String> bList;

    public static JLabel btnView;
    public static JLabel btnDownload;

    public static JList<String> certiList;
    public static DefaultListModel<String> cList;

    public static String tColumn[] = { "MOODLE ID", "NAME", "BRANCH", "ROLL NO.", "CONTACT", "CERTIFICATES" };

    public static void guiListStudent() {
        int fWidth = 680;
        int fHeight = 450;

        mainWindow = new JFrame();
        mainWindow.setSize(fWidth, fHeight);

        lblListStudent = new JLabel();
        lblListStudent.setFont(new java.awt.Font("Tahoma", 1, 24));
        lblListStudent.setText("Student list");
        lblListStudent.setBounds((fWidth - 200) / 2, 15, 270, 20);
        mainWindow.add(lblListStudent);

        getBranchList();
        branchCombo = new JComboBox<String>(new Vector<String>(bList));
        branchCombo.setBounds(505, 60, 140, 30);
        branchCombo.setSelectedIndex(0);
        mainWindow.add(branchCombo);

        branchCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filter = branchCombo.getSelectedItem().toString();
                tableModel.setRowCount(0);
                getStudentDetails(filter);
            }
        });

        tableModel = new DefaultTableModel(tColumn, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        getStudentDetails("All");

        stdTable = new JTable(tableModel);
        stdTable.setBounds(10, 100, 650, 300);
        JScrollPane sp = new JScrollPane();
        sp.setBounds(10, 100, 650, 300);
        sp.setViewportView(stdTable);

        mainWindow.add(sp);

        stdTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    String mID = stdTable.getValueAt(stdTable.getSelectedRow(), 0).toString();
                    String mBranch = stdTable.getValueAt(stdTable.getSelectedRow(), 2).toString();
                    std_certiList frame = new std_certiList();
                    frame.setVisible(mID, mBranch);
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

    public static void viewCertificate(String certi, String mID) {
        viewFile frame = new viewFile();
        frame.setVisible(replaceURL(getURLLink(certi, mID)));
    }

    public static String getURLLink(String certi, String mID) {
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

    public static String getCetiCount(String mID) {
        String count = "0";
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            Statement stmt = conn.createStatement();
            String query = "SELECT count(*) FROM std_certi WHERE uid='" + mID + "';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            count = String.valueOf(rs.getInt(1));
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            count = "N/A";
            popupMsg.infoBox("Error Getting certificate count!", "ERROR");
        }
        return count;
    }

    public static void getStudentDetails(String filter) {
        bList = new ArrayList<String>();
        bList.add("ALL");
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        String query = "";
        if (filter.equals("All")) {
            query = "SELECT * FROM acc WHERE type = 'Student'";
        } else {
            query = "SELECT * FROM acc WHERE type = 'Student' && branch = '" + filter + "'";
        }

        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                // Display values
                String mID = rs.getString("uid");
                String mName = rs.getString("name");
                String mBranch = rs.getString("branch");
                String mRoll = rs.getString("roll");
                String mContact = rs.getString("contact");
                String certiCount = getCetiCount(mID);
                String rData[] = { mID, mName, mBranch, mRoll, mContact, certiCount };
                tableModel.addRow(rData);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getBranchList() {
        bList = new ArrayList<String>();
        bList.add("All");
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

    public static void main(String[] args) {
        guiListStudent();
    }

    public void setVisible(boolean b) {
        guiListStudent();
    }

}
