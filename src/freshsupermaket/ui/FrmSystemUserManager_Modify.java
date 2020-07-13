package freshsupermaket.ui;

import freshsupermaket.control.DiscountManager;
import freshsupermaket.control.SystemUserManager;
import freshsupermaket.model.BeanSystemUser;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmSystemUserManager_Modify extends JDialog implements ActionListener {
    BeanSystemUser pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelUserid = new JLabel("账号：");
    private JLabel labelUsername = new JLabel("姓名：");
    private JLabel labelUserpwd =new JLabel("密码  ");

    private JTextField edtUserid = new JTextField(20);
    private JTextField edtUsername = new JTextField(20);
    private JTextField edtUserpwd = new JTextField(20);
    public FrmSystemUserManager_Modify(JDialog f, String s, boolean b, BeanSystemUser c) {
        super(f, s, b);
        this.pub= c;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUserid);
        this.edtUserid.setEnabled(false);
        this.edtUserid.setText(String.valueOf(c.getSystem_user_id()));
        workPane.add(edtUserid);
        workPane.add(labelUsername);
        this.edtUsername.setText(c.getSystem_user_name());
        workPane.add(edtUsername);
        workPane.add(labelUserpwd);
        this.edtUserpwd.setText(c.getPassword());
        workPane.add(edtUserpwd);





        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(310, 400);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                FrmSystemUserManager_Modify.this.pub=null;
            }
        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            this.pub=null;
            return;
        }
        else if(e.getSource()==this.btnOk){
            pub.setSystem_user_id(this.edtUserid.getText());
            pub.setSystem_user_name(this.edtUsername.getText());
            pub.setPassword(this.edtUserpwd.getText());
            try {
                (new SystemUserManager()).ModifySystemUser(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

    public BeanSystemUser getPub() {
        return pub;
    }
    
}
