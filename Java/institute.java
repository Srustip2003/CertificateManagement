import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class institute {
    public static JFrame instituteWindow;
    public static JLabel btnLogout;
    public static JLabel btnClose;
    public static JLabel btnAdd;
    public static JLabel btnList;
    public static JLabel btnRequest;
    public static JLabel lblWelcome;
    // private static JLabel lblDate;
    // private static JLabel lblTime;
    public static JLabel lblTopNav;

    public static void guiInstitute() {
        int fWidth = 675;
        int fHeight = 392;

        instituteWindow = new JFrame();
        instituteWindow.getContentPane().setBackground(new Color(225, 225, 225));
        instituteWindow.setSize(fWidth, fHeight);

        lblWelcome = new JLabel();
        lblWelcome.setFont(new Font("Tahoma", 1, 20));
        lblWelcome.setText("Welcome, Institute");
        lblWelcome.setBounds(10, 50, 500, 50);
        instituteWindow.add(lblWelcome);

        btnClose = new JLabel();
        btnClose.setIcon(new ImageIcon("img/btnClose.png"));
        btnClose.setBounds(640, 2, 50, 50);
        instituteWindow.add(btnClose);
        btnClose.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                instituteWindow.dispose();
            }
        });

        btnLogout = new JLabel();
        btnLogout.setIcon(new ImageIcon("img/btnLogout.png"));
        btnLogout.setBounds(10, 18, 80, 23);
        instituteWindow.add(btnLogout);

        btnLogout.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                instituteWindow.dispose();
                initBoot nFrame = new initBoot();
                nFrame.setVisible(true);

            }
        });

        lblTopNav = new JLabel();
        lblTopNav.setIcon(new ImageIcon("img/lblTopNav.png"));
        lblTopNav.setBounds(0, 0, 675, 59);
        instituteWindow.add(lblTopNav);

        btnAdd = new JLabel();
        btnAdd.setIcon(new ImageIcon("img/btnAddStudent.png"));
        btnAdd.setBounds(140, 123, 114, 141);
        instituteWindow.add(btnAdd);

        btnAdd.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ins_addStudent nFrame = new ins_addStudent();
                nFrame.setVisible(true);
            }
        });

        btnList = new JLabel();
        btnList.setIcon(new ImageIcon("img/btnListStudent.png"));
        btnList.setBounds(286, 123, 114, 141);
        instituteWindow.add(btnList);

        btnList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ins_listStudent nFrame = new ins_listStudent();
                nFrame.setVisible(true);
            }
        });

        btnRequest = new JLabel();
        btnRequest.setIcon(new ImageIcon("img/btnRequest.png"));
        btnRequest.setBounds(432, 123, 114, 141);
        instituteWindow.add(btnRequest);

        btnRequest.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ins_request nFrame = new ins_request();
                nFrame.setVisible(true);
            }
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        instituteWindow.setLocation(dim.width / 2 - instituteWindow.getSize().width / 2,
                dim.height / 3 - instituteWindow.getSize().height / 2);
        instituteWindow.setUndecorated(true);
        instituteWindow.setLayout(null);
        instituteWindow.setResizable(false);
        instituteWindow.setVisible(true);
    }

    public static void main(String[] args) {
        guiInstitute();

    }

    public void setVisible(boolean b) {
        guiInstitute();
    }
}
