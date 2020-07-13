package freshsupermaket.ui;

import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanCustomer;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import freshsupermaket.control.CustomerManager;

public class FrmCustomerManager_Modify extends JDialog implements ActionListener {
    BeanCustomer pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelId = new JLabel("用户编号");
    private JLabel labelName = new JLabel("姓名   ");
//    private JLabel labelSex = new JLabel("性别");
    private JLabel labelPwd = new JLabel("密码    ");
    private JLabel labelNumber = new JLabel("手机号码");
    private JLabel labelEmail = new JLabel("邮箱    ");
    private JLabel labelCity = new JLabel("所在城市");
//    private JLabel labelIsMember =new JLabel("是否会员");
    private JLabel labelFinishDate = new JLabel("结束日期");

    private JTextField edtId=new JTextField(20);
    private JTextField edtName=new JTextField(20);
    //    private JTextField edtSex=new JTextField(20);
    private JTextField edtPwd=new JTextField(20);
    private JTextField edtNumber=new JTextField(20);
    private JTextField edtEmail=new JTextField(20);
    private JTextField edtCity=new JTextField(20);
    //    private JTextField edtIsMember=new JTextField(20);
    private JTextField edtMfinishDate= new JTextField(20);
    private JComboBox comboBox1=null;
    private JComboBox comboBox2=null;
    public FrmCustomerManager_Modify(JDialog f, String s, boolean b, BeanCustomer c) {
        super(f, s, b);
        this.pub= c;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        this.edtId.setEnabled(false);
        this.edtId.setText(c.getCustomer_id());
        workPane.add(edtId);
        workPane.add(labelName);
        this.edtName.setText(c.getCustomer_name());
        workPane.add(edtName);
        workPane.add(labelPwd);
        this.edtPwd.setText(c.getPassword());
        workPane.add(edtPwd);
//        workPane.add(labelCustomerSex);
//        this.edtSex.setText(c.getCustomer_sex());
//        workPane.add(edtSex);
        workPane.add(labelNumber);
        this.edtNumber.setText(String.valueOf(c.getCustomer_number()));
        workPane.add(edtNumber);
        workPane.add(labelEmail);
        this.edtEmail.setText(c.getEmail());
        workPane.add(edtEmail);
        workPane.add(labelCity);
        this.edtCity.setText(c.getCity());
        workPane.add(edtCity);
//        workPane.add(labelIsMember);
//        this.edtIsMember.setText(String.valueOf(c.isMember()));
//        workPane.add(edtIsMember);
        workPane.add(labelFinishDate);
        this.edtMfinishDate.setText(String.valueOf(c.getFinish_date()));
        workPane.add(edtMfinishDate);
        //设置性别和会员信息
        String[] sex=new String[3];
        String[] IsMember=new String[3];
        sex[0]="";sex[1]="男";sex[2]="女";
        IsMember[0]="";IsMember[1]="是";IsMember[2]="否";
        this.comboBox1=new JComboBox(sex);
        workPane.add(this.comboBox1);
        this.comboBox2=new JComboBox(IsMember);
        workPane.add(this.comboBox2);



        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 340 );
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
                FrmCustomerManager_Modify.this.pub=null;
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
            pub.setCustomer_name(this.edtName.getText());
            pub.setPassword(this.edtPwd.getText());
            if(isNumeric.isNumeric(this.edtNumber.getText())) {
                pub.setCustomer_number(this.edtNumber.getText());
            }
            else {
                JOptionPane.showMessageDialog(null,"号码为数字","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            pub.setEmail(this.edtEmail.getText());
            pub.setCity(this.edtCity.getText());
            if(this.comboBox1.getSelectedIndex()>0){
                if (this.comboBox1.getSelectedItem().toString().equals("女")){
                    pub.setCustomer_sex("女");
                }
                else{
                    pub.setCustomer_sex("男");
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"请选择性别","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (this.comboBox2.getSelectedIndex()>0){
                if (this.comboBox2.getSelectedItem().toString().equals("是")){
                    pub.setMember(true);
                }
                else{
                    pub.setMember(false);
                    pub.setFinish_date(null);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,"请选择会员类型","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(pub.isMember()){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    pub.setFinish_date(sdf.parse(this.edtMfinishDate.getText()));
                } catch (ParseException parseException) {
                    JOptionPane.showMessageDialog(null,"请输入正确的时间格式","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                (new CustomerManager()).ModifyCustomer(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public BeanCustomer getPub() {
        return pub;
    }

}

