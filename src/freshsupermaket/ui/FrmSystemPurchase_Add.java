package freshsupermaket.ui;

import freshsupermaket.control.SystemUserManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanGoodPurchase;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmSystemPurchase_Add extends JDialog implements ActionListener {
    private BeanGoodPurchase pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
//    private JLabel labelId = new JLabel("采购编号：");
    private JLabel labelGoodId = new JLabel("商品编号：");
    private JLabel labelQuantity = new JLabel("采购数量：");
    private JLabel labelstate = new JLabel("状  态：");


//    private JTextField edtId=new JTextField(20);
    private JTextField edtGoodId=new JTextField(20);
    private JTextField edtQuantity=new JTextField(20);

    private JComboBox comboBox=null;

    public FrmSystemPurchase_Add(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//        workPane.add(labelId);
//        workPane.add(edtId);
        workPane.add(labelGoodId);
        workPane.add(edtGoodId);
        workPane.add(labelQuantity);
        workPane.add(edtQuantity);
        //状态使用选项来实现，防止出错
        workPane.add(labelstate);
        //设置状态信息
        String[] state=new String[3];
        state[0]="下单";state[1]="在途";state[2]="入库";
        this.comboBox=new JComboBox(state);
        workPane.add(this.comboBox);

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

            pub=new BeanGoodPurchase();
            if(isNumeric.isNumeric(this.edtGoodId.getText())){
                pub.setGood_id(Integer.valueOf(this.edtGoodId.getText()));
            }
            if(isNumeric.isNumeric(this.edtQuantity.getText())) {
                pub.setQuantity(Integer.valueOf(this.edtQuantity.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"商品编号为数字","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(this.comboBox.getSelectedItem().toString().equals("下单")){
                pub.setState("下单");
            }
            else if(this.comboBox.getSelectedItem().toString().equals("在途")) {
                pub.setState("在途");
            }
            else if(this.comboBox.getSelectedItem().toString().equals("入库")) {
                pub.setState("入库");

            }

            try {
                (new SystemUserManager()).CreateGoodPurchase(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                this.pub=null;
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public BeanGoodPurchase getPub() {
        return pub;
    }
}
