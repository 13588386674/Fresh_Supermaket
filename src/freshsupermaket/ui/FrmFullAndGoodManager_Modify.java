package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.DiscountManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanFullAndGood;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmFullAndGoodManager_Modify extends JDialog implements ActionListener {
    BeanFullAndGood pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

    private JLabel labelGoodid = new JLabel("商品编号");
    private JLabel labelFullReductionid = new JLabel("满折编号");
    private JLabel labelStarteDate = new JLabel("起始日期");
    private JLabel labelEndDate = new JLabel("结束日期");

    private JTextField edtGoodid=new JTextField(20);
    private JTextField edtFullReductionid=new JTextField(20);
    private JTextField edtStartDate=new JTextField(20);
    private JTextField edtEndDate=new JTextField(20);
    public FrmFullAndGoodManager_Modify(JDialog f, String s, boolean b, BeanFullAndGood c) {
        super(f, s, b);
        this.pub= c;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelGoodid);
        this.edtGoodid.setEnabled(false);
        this.edtGoodid.setText(String.valueOf(c.getGood_id()));
        workPane.add(edtGoodid);
        workPane.add(labelFullReductionid);
        this.edtFullReductionid.setEnabled(false);
        this.edtFullReductionid.setText(String.valueOf(c.getFull_reduction_id()));
        workPane.add(edtFullReductionid);
        workPane.add(labelStarteDate);
        this.edtStartDate.setText(String.valueOf(c.getStart_date()));
        workPane.add(edtStartDate);
        workPane.add(labelEndDate);
        this.edtEndDate.setText(String.valueOf(c.getEnd_date()));
        workPane.add(edtEndDate);




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
                FrmFullAndGoodManager_Modify.this.pub=null;
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
            BeanFullAndGood pub1=new BeanFullAndGood();
            if(isNumeric.isNumeric(this.edtGoodid.getText())) {
                pub1.setGood_id(Integer.valueOf(this.edtGoodid.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null, "商品编号为数字", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric(this.edtFullReductionid.getText())) {
                pub1.setFull_reduction_id(Integer.valueOf(this.edtFullReductionid.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null, "满折编号为数字", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                pub1.setStart_date(sdf.parse(this.edtStartDate.getText()));
                pub1.setEnd_date(sdf.parse(this.edtEndDate.getText()));
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(null,"请输入正确的时间格式","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }


            try {
                (new DiscountManager()).ModifyFullAndGood(pub,pub1);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

    public BeanFullAndGood getPub() {
        return pub;
    }
}
