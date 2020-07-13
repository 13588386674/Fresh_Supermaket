package freshsupermaket.ui;

import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanAddress;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmCustomerAddressManager_Modify extends JDialog implements ActionListener {
    private BeanAddress pub=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelAddress_Id = new JLabel("地址编号：");
    private JLabel labelCustomer_Id = new JLabel("用户编号：");
    private JLabel labelProvince = new JLabel("省：     ");
    private JLabel labelCity = new JLabel("市      ");
    private JLabel labelArea = new JLabel("区     ");
    private JLabel labelAddress = new JLabel("地址   ");
    private JLabel labelConnect_person = new JLabel("联系人    ");
    private JLabel labelConnect_number = new JLabel("电话     ");


    private JTextField edtAddress_Id = new JTextField(20);
    private JTextField edtCustomer_Id = new JTextField(15);
    private JTextField edtProvince = new JTextField(15);
    private JTextField edtCity = new JTextField(15);
    private JTextField edtArea = new JTextField(15);
    private JTextField edtAddress = new JTextField(15);
    private JTextField edtConnect_person = new JTextField(15);
    private JTextField edtConnect_number = new JTextField(15);
    public FrmCustomerAddressManager_Modify(JDialog f, String s, boolean b,BeanAddress p) {
        super(f, s, b);
        this.pub=p;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelAddress_Id);
        this.edtAddress_Id.setEnabled(false);
        this.edtAddress_Id.setText(String.valueOf(this.pub.getAddress_id()));
        workPane.add(edtAddress_Id);
        workPane.add(labelCustomer_Id);
        this.edtCustomer_Id.setText(p.getCustomer_id());
        workPane.add(edtCustomer_Id);
        workPane.add(labelProvince);
        this.edtProvince.setText(p.getProvince());
        workPane.add(edtProvince);
        workPane.add(labelCity);
        this.edtCity.setText(p.getCity());
        workPane.add(edtCity);
        workPane.add(labelArea);
        this.edtArea.setText(p.getArea());
        workPane.add(edtArea);
        workPane.add(labelAddress);
        this.edtAddress.setText(p.getAddress());
        workPane.add(edtAddress);

        workPane.add(labelConnect_person);
        this.edtConnect_person.setText(p.getConnect_person());
        workPane.add(edtConnect_person);
        workPane.add(labelConnect_number);
        this.edtConnect_number.setText(String.valueOf(p.getConnect_number()));
        workPane.add(edtConnect_number);
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
                FrmCustomerAddressManager_Modify.this.pub=null;
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
            pub.setAddress_id(Integer.valueOf(this.edtAddress_Id.getText()));
            pub.setCustomer_id(this.edtCustomer_Id.getText());
            pub.setProvince(this.edtProvince.getText());
            pub.setCity(this.edtCity.getText());
            pub.setArea(this.edtArea.getText());
            pub.setAddress(this.edtAddress.getText());
            pub.setConnect_person(this.edtConnect_person.getText());
            if(isNumeric.isNumeric(this.edtConnect_number.getText())) {
                pub.setConnect_number(this.edtConnect_number.getText());
            }
            else {
                JOptionPane.showMessageDialog(null, "号码为数字", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                (new CustomerManager()).ModifyAddress(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                this.pub=null;
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public BeanAddress getPub() {
        return pub;
    }
}
