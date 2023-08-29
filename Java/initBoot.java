import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class initBoot {
    private static JFrame loginWindow;
    private static JLabel btnLogin;
    private static JLabel btnClose;
    private static JLabel btnForgotPassword;
    private static JLabel usernameBack;
    private static JLabel pwdBack;
    private static JLabel labelBck;
    private static JTextField editUsername;
    private static JPasswordField editPwd;
    private static JRadioButton r1;
    private static JRadioButton r2;
    private static ButtonGroup bg;

    public static void guiLogin() {
        int fWidth = 600;
        int fHeight = 400;

        loginWindow = new JFrame();

        loginWindow.getContentPane().setBackground(new Color(45, 156, 219));
        loginWindow.setSize(fWidth, fHeight);

        editUsername = new HintTextField("ENTER MOODLE ID HERE");
        editUsername.setHorizontalAlignment(JTextField.CENTER);
        editUsername.setBounds((fWidth - 250) / 2, 124, 250, 40);
        editUsername.setOpaque(false);
        editUsername.setBorder(null);
        usernameBack = new JLabel();
        usernameBack.setIcon(new ImageIcon("img/editbox.png"));
        usernameBack.setLayout(new BorderLayout());
        usernameBack.setBounds((fWidth - 258) / 2, 124, 258, 48);
        loginWindow.add(editUsername);
        loginWindow.add(usernameBack);

        editPwd = new HintPasswordField("PASSWORD");
        editPwd.setHorizontalAlignment(JTextField.CENTER);
        editPwd.setBounds((fWidth - 250) / 2, 169, 250, 40);
        editPwd.setOpaque(false);
        editPwd.setBorder(null);
        pwdBack = new JLabel();
        pwdBack.setIcon(new ImageIcon("img/editbox.png"));
        pwdBack.setLayout(new BorderLayout());
        pwdBack.setBounds((fWidth - 258) / 2, 169, 258, 48);
        loginWindow.add(editPwd);
        loginWindow.add(pwdBack);

        r1 = new JRadioButton();
        r2 = new JRadioButton();
        r1.setBounds(187, 216, 20, 20);
        r2.setBounds(303, 216, 20, 20);
        r1.setSelected(true);
        r1.setActionCommand("Student");
        r2.setActionCommand("Institute");

        bg = new ButtonGroup();
        bg.add(r1);
        bg.add(r2);
        loginWindow.add(r1);
        loginWindow.add(r2);

        btnLogin = new JLabel();
        btnLogin.setIcon(new ImageIcon("img/loginBtn.png"));
        btnLogin.setBounds((fWidth - 258) / 2, 250, 258, 48);
        loginWindow.add(btnLogin);

        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String uName = editUsername.getText();
                String uPwd = String.valueOf(editPwd.getPassword());
                String uType = bg.getSelection().getActionCommand().toString();
                if (uName == null || uName.trim().isEmpty() || uName.equals("ENTER MOODLE ID HERE")) {
                    popupMsg.infoBox("Enter Moodle ID!", "ERROR");
                    return;
                }
                if (uPwd == null || uPwd.trim().isEmpty() || uName.equals("PASSWORD")) {
                    popupMsg.infoBox("Enter Password!", "ERROR");
                    return;
                }
                try {
                    loginUser(uName, uPwd, uType);
                } catch (ClassNotFoundException | SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnClose = new JLabel();
        btnClose.setIcon(new ImageIcon("img/btnClose.png"));
        btnClose.setBounds(554, 12, 50, 50);
        loginWindow.add(btnClose);

        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                loginWindow.dispose();
            }
        });

        btnForgotPassword = new JLabel();
        btnForgotPassword.setIcon(new ImageIcon("img/lblForgotPwd.png"));
        btnForgotPassword.setBounds(323, 301, 101, 13);
        loginWindow.add(btnForgotPassword);

        btnForgotPassword.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                popupMsg.infoBox("Please contact Admin to reset your password.", "Info");
            }
        });

        labelBck = new JLabel();
        labelBck.setIcon(new ImageIcon("img/loginBackground.png"));
        labelBck.setBounds((fWidth - 315) / 2, (fHeight - 273) / 2, 315, 273);
        loginWindow.add(labelBck);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        loginWindow.setLocation(dim.width / 2 - loginWindow.getSize().width / 2,
                dim.height / 3 - loginWindow.getSize().height / 2);
        loginWindow.setUndecorated(true);
        loginWindow.setLayout(null);
        loginWindow.setResizable(false);
        loginWindow.setVisible(true);
        loginWindow.requestFocusInWindow();
    }

    public static void loginUser(String uName, String uPwd, String uType) throws ClassNotFoundException, SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/cms";
        String username = "root";
        String password = "";

        Connection conn = DriverManager.getConnection(dbURL, username, password);
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM acc WHERE uid='" + uName + "' && pwd='" + uPwd + "' && type='" + uType
                + "';";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getRow();
        if (count == 0) {
            popupMsg.infoBox("No Account with those creditials found!", "ERROR");
        } else {
            String displayName = rs.getString("name");
            if (uType.equals("Student")) {
                student frame = new student();
                loginWindow.dispose();
                frame.setVisible(uName, displayName);
            } else {
                institute frame = new institute();
                loginWindow.dispose();
                frame.setVisible(true);
            }
        }
        conn.close();
    }

    public static void main(String[] args) {
        guiLogin();
    }

    public void setVisible(boolean b) {
        guiLogin();
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