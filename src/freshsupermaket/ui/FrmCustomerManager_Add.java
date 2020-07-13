package freshsupermaket.ui;

import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanCustomer;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/* 创建读者，顾客编号由于涉及账户的登录变为字符串*/
public class FrmCustomerManager_Add extends JDialog implements ActionListener {
    BeanCustomer pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelId = new JLabel("用户编号");
    private JLabel labelName = new JLabel("姓    名");
    private JLabel labelSex = new JLabel("性    别");
    private JLabel labelPwd = new JLabel("密    码");
    private JLabel labelNumber = new JLabel("手机号码");
    private JLabel labelEmail = new JLabel("邮    箱");
    private JLabel labelCity = new JLabel("所在城市");
    private JLabel labelIsMember =new JLabel("是否会员");


    private JTextField edtId=new JTextField(20);
    private JTextField edtName=new JTextField(20);
//    private JTextField edtSex=new JTextField(20);
    private JTextField edtPwd=new JTextField(20);
    private JTextField edtNumber=new JTextField(20);
    private JTextField edtEmail=new JTextField(20);
    private JTextField edtCity=new JTextField(20);
//    private JTextField edtIsMember=new JTextField(20);
//    private JTextField edtMfinishDate= new JTextField(20);
    private JComboBox comboBox1=null;
    private JComboBox comboBox2=null;
    public FrmCustomerManager_Add(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        workPane.add(edtId);
        workPane.add(labelName);
        workPane.add(edtName);
//        workPane.add(labelSex);
//        workPane.add(edtSex);
        workPane.add(labelPwd);
        workPane.add(edtPwd);
        workPane.add(labelNumber);
        workPane.add(edtNumber);
        workPane.add(labelEmail);
        workPane.add(edtEmail);
        workPane.add(labelCity);
        workPane.add(edtCity);
//        workPane.add(labelIsMember);
//        workPane.add(edtIsMember);
        //性别和会员使用选项来实现，防止出错
        workPane.add(labelSex);
        workPane.add(labelIsMember);
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
        this.setSize(320,300 );
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

            pub=new BeanCustomer();
            pub.setCustomer_id(this.edtId.getText());
            pub.setCustomer_name(this.edtName.getText());
//            pub.setCustomer_sex(this.co);
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
            pub.setCreate_date(new java.sql.Timestamp(System.currentTimeMillis()));
            if(this.comboBox2.getSelectedItem().toString().equals("是")){
                pub.setMember(true);
                //默认在创建会员的时间增加两年，定为会员的有效期
                Calendar rightNow =Calendar.getInstance();
                rightNow.setTime(pub.getCreate_date());
                rightNow.add(Calendar.YEAR,2);
                pub.setFinish_date(rightNow.getTime());
            }
            else if(this.comboBox2.getSelectedItem().toString().equals("否")) {
                pub.setMember(false);
            }
            else {
                JOptionPane.showMessageDialog(null,"请在会员部分选择是或否","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (this.comboBox1.getSelectedItem().toString().equals("男")){
                pub.setCustomer_sex("男");
            }
            else if (this.comboBox1.getSelectedItem().toString().equals("女")){
                pub.setCustomer_sex("女");
            }
            else {
                JOptionPane.showMessageDialog(null,"请在性别部分选择是或否","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }


            try {
                (new CustomerManager()).CreateCustomer(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                this.pub=null;
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public BeanCustomer getPub() {
        return pub;
    }


}
