package freshsupermaket.ui;

import javax.lang.model.element.NestingKind;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.SystemUserManager;
import freshsupermaket.model.BeanCustomer;
import freshsupermaket.model.BeanSystemUser;
import freshsupermaket.util.BaseException;

public class FrmLogin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private JButton btnLogin = new JButton("登陆");
    private JButton btnCancel = new JButton("退出");
    private JButton btnRegister = new JButton("注册");

    private String type;
    private JLabel labelUser = new JLabel("用户：");
    private JLabel labelPwd = new JLabel("密码：");
    private JTextField edtUserId = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JComboBox cmbUserType=null;
    public FrmLogin(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnRegister);
        toolBar.add(btnLogin);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUser);
        workPane.add(edtUserId);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 240);

        String[] usertype=new String[2];
        usertype[0]="顾客";usertype[1]="管理员";
        this.cmbUserType=new JComboBox(usertype);
        workPane.add(this.cmbUserType);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        this.btnRegister.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.cmbUserType.getSelectedItem().toString().equals("管理员")) {
            if (e.getSource() == this.btnLogin) {
                SystemUserManager sum = new SystemUserManager();
                String userid = this.edtUserId.getText();
                String pwd = new String(this.edtPwd.getPassword());
                try {
                    BeanSystemUser user = sum.loadUser(userid);
                    if (pwd.equals(user.getPassword())) {
                        SystemUserManager.currentLoginUser = user;
                        this.type="管理员";
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "密码错误", "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                }


            } else if (e.getSource() == this.btnCancel) {
                System.exit(0);
            } else if (e.getSource() == this.btnRegister) {
                FrmRegister dlg = new FrmRegister(this, "注册", true);
                dlg.setVisible(true);
            }
        }
        else if (this.cmbUserType.getSelectedItem().toString().equals("顾客")){
            if (e.getSource() == this.btnLogin) {
                CustomerManager sum = new CustomerManager();
                String userid = this.edtUserId.getText();
                String pwd = new String(this.edtPwd.getPassword());
                try {
                    BeanCustomer user = sum.LoadCustomer(userid);
                    if (pwd.equals(user.getPassword())) {
                        CustomerManager.currentLoginCustomer = user;
                        this.type="顾客";
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "密码错误", "错误提示", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "错误提示", JOptionPane.ERROR_MESSAGE);
                }


            } else if (e.getSource() == this.btnCancel) {
                System.exit(0);
            } else if (e.getSource() == this.btnRegister) {
                FrmCustomerManager_Add dlg = new FrmCustomerManager_Add(this, "注册", true);
                dlg.setVisible(true);
            }
        }
    }
    public String gettype(){
        return this.type;
    }


}
