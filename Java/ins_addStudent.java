import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ins_addStudent {
    public static JFrame mainWindow;
    public static JTextField editMoodle;
    public static JLabel editMoodleBack;
    public static JTextField editName;
    public static JLabel editNameBack;
    public static JLabel lblAddStudent;
    public static JLabel btnAddStudent;
    public static JComboBox<String> branchList;

    private static String[] branchText = { "IT", "Civil", "Mechanical", "Computer", "Other" };

    private static void guiAddStudent() {
        int fWidth = 310;
        int fHeight = 300;

        mainWindow = new JFrame();
        // mainWindow.getContentPane().setBackground(new Color(225, 225, 225));
        mainWindow.setSize(fWidth, fHeight);

        lblAddStudent = new JLabel();
        lblAddStudent.setIcon(new ImageIcon("img/lblAddStudent.png"));
        lblAddStudent.setBounds(0, 10, 290, 25);
        mainWindow.add(lblAddStudent);

        editMoodle = new HintTextField("MOODLE ID");
        editMoodle.setHorizontalAlignment(JTextField.CENTER);
        editMoodle.setBounds((fWidth - 250) / 2, 50, 250, 40);
        editMoodle.setOpaque(false);
        editMoodle.setBorder(null);
        editMoodleBack = new JLabel();
        editMoodleBack.setIcon(new ImageIcon("img/editbox.png"));
        editMoodleBack.setLayout(new BorderLayout());
        editMoodleBack.setBounds((fWidth - 260) / 2, 50, 258, 48);
        mainWindow.add(editMoodle);
        mainWindow.add(editMoodleBack);

        editName = new HintTextField("NAME");
        editName.setHorizontalAlignment(JTextField.CENTER);
        editName.setBounds((fWidth - 250) / 2, 105, 250, 40);
        editName.setOpaque(false);
        editName.setBorder(null);
        editNameBack = new JLabel();
        editNameBack.setIcon(new ImageIcon("img/editbox.png"));
        editNameBack.setLayout(new BorderLayout());
        editNameBack.setBounds((fWidth - 260) / 2, 105, 258, 48);
        mainWindow.add(editName);
        mainWindow.add(editNameBack);

        branchList = new JComboBox<>(branchText);
        branchList.setBounds((fWidth - 260) / 2, 160, 250, 40);
        branchList.setSelectedIndex(0);
        mainWindow.add(branchList);

        btnAddStudent = new JLabel();
        btnAddStudent.setIcon(new ImageIcon("img/btnAddStudentMain.png"));
        btnAddStudent.setBounds((fWidth - 258) / 2, 215, 258, 48);
        mainWindow.add(btnAddStudent);

        btnAddStudent.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String mID = editMoodle.getText();
                String mName = editName.getText();
                String mBranch = branchList.getSelectedItem().toString();

                if (mID == null || mID.trim().isEmpty() || mID.equals("MOODLE ID")) {
                    popupMsg.infoBox("Enter Moodle ID!", "ERROR");
                    return;
                }
                if (mName == null || mName.trim().isEmpty() || mName.equals("NAME")) {
                    popupMsg.infoBox("Enter Name!", "ERROR");
                    return;
                }
                if (!checkMoodleID(mID)) {
                    popupMsg.infoBox("Moodle ID already exists!", "ERROR");
                    return;
                }
                try {
                    addNewStudent(mID, mName, mBranch);
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
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

    public static void addNewStudent(String mID, String mName, String mBranch)
            throws ClassNotFoundException, SQLException {
        String dbURL = "jdbc:mysql://127.0.0.1:3306/cms";
        String username = "root";
        String password = "";

        Connection conn = null;
        Statement stmt = null;

        String uPass = mID + "@apsit";
        String query = "INSERT INTO acc (uid, name, pwd, branch, roll, contact, type, p_link) value ('" + mID + "', '"
                + mName
                + "', '" + uPass + "', '" + mBranch + "', '', '', 'Student', '')";
        try {
            conn = DriverManager.getConnection(dbURL, username, password);
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate(query);
            popupMsg.infoBox("Account Created Successfully!", "SUCCESS");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            popupMsg.infoBox("Error Creating Account!", "ERROR");
        } finally {
        }
        conn.close();
    }

    public static boolean checkMoodleID(String mID) {
        boolean check = false;
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            Statement stmt = conn.createStatement();
            String query = "SELECT count(*) FROM acc WHERE uid='" + mID + "';";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int count = rs.getInt(1);
            if (count == 0) {
                check = true;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            popupMsg.infoBox("Error Verifying Moodle ID!", "ERROR");
        }
        return check;
    }

    public static void main(String[] args) {
        guiAddStudent();
    }

    public void setVisible(boolean b) {
        guiAddStudent();
    }

}

class HintTextField extends JTextField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public HintTextField(final String hint) {
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
        return showingHint ? "" : super.getText();
    }
}
