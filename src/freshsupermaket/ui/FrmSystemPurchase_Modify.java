package freshsupermaket.ui;

import freshsupermaket.control.SystemUserManager;
import freshsupermaket.model.BeanGoodPurchase;
import freshsupermaket.model.BeanGoodPurchase;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class FrmSystemPurchase_Modify extends JDialog implements ActionListener {
    BeanGoodPurchase pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelId = new JLabel("采购编号：");
    private JLabel labelGoodId = new JLabel("商品编号：");
    private JLabel labelQuantity = new JLabel("采购数量：");
//    private JLabel labelstate = new JLabel("状  态：");
    
    private JTextField edtId=new JTextField(20);
    private JTextField edtGoodId=new JTextField(20);
    private JTextField edtQuantity=new JTextField(20);
    private JComboBox comboBox=null;
    public FrmSystemPurchase_Modify(JDialog f, String s, boolean b, BeanGoodPurchase c) {
        super(f, s, b);
        this.pub= c;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelId);
        this.edtId.setEnabled(false);
        this.edtId.setText(String.valueOf(c.getPurchase_id()));
        workPane.add(edtId);
        workPane.add(labelGoodId);
        this.edtGoodId.setEnabled(false);
        this.edtGoodId.setText(String.valueOf(c.getGood_id()));
        workPane.add(edtGoodId);
        workPane.add(labelQuantity);
        this.edtQuantity.setEnabled(false);
        this.edtQuantity.setText(String.valueOf(c.getQuantity()));
        workPane.add(edtQuantity);
        //设置性别和会员信息
        String[] state=new String[3];
        state[0]="下单";state[1]="在途";state[2]="入库";
        this.comboBox=new JComboBox(state);
        workPane.add(this.comboBox);



        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 400);
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
                FrmSystemPurchase_Modify.this.pub=null;
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
        else if(e.getSource()==this.btnOk) {

            if (this.comboBox.getSelectedItem().toString().equals("下单")) {
                pub.setState("下单");
            } else if (this.comboBox.getSelectedItem().toString().equals("在途")) {
                pub.setState("在途");
            } else if (this.comboBox.getSelectedItem().toString().equals("入库")) {
                pub.setState("入库");

            }

            try {
                (new SystemUserManager()).ModifyGoodPurchase(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
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
    public BeanGoodPurchase getPub() {
        return pub;
    }

}
