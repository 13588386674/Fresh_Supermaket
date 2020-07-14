package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.SystemUserManager;
import freshsupermaket.model.BeanSystemUser;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmSystemUserManager_Add extends JDialog implements ActionListener {
    private BeanSystemUser user=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelUserid = new JLabel("账号：");
    private JLabel labelUsername = new JLabel("姓名：");
    private JLabel labelUserpwd =new JLabel("密码");

    private JTextField edtUserid = new JTextField(20);
    private JTextField edtUsername = new JTextField(20);
    private JTextField edtUserpwd = new JTextField(20);
    public FrmSystemUserManager_Add(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelUserid);
        workPane.add(edtUserid);
        workPane.add(labelUsername);
        workPane.add(edtUsername);
        workPane.add(labelUserpwd);
        workPane.add(edtUserpwd);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 180);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if(e.getSource()==this.btnOk){
            String userid=this.edtUserid.getText();
            String username=this.edtUsername.getText();
            String userpwd=this.edtUserpwd.getText();
            user=new BeanSystemUser();
            user.setSystem_user_id(userid);
            user.setSystem_user_name(username);
            user.setPassword(userpwd);

            try {
                if((new CheckForeign()).CheckSystemUser(user.getSystem_user_id())==false){
                    JOptionPane.showMessageDialog(null,"存在管理员","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                (new SystemUserManager()).CreateSystemUser(user);
                this.setVisible(false);
            } catch (BaseException e1) {
                this.user=null;
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public BeanSystemUser getUser() {
        return user;
    }
}
