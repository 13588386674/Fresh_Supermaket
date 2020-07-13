package freshsupermaket.ui;

import freshsupermaket.control.DiscountManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanCoupon;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmCouponManager_Add extends JDialog implements ActionListener {
    BeanCoupon pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

//    private JLabel labelId = new JLabel("优惠券编号");
    private JLabel labelContent = new JLabel("内容");
    private JLabel labelSuitable = new JLabel("适用金额");
    private JLabel labelCredit = new JLabel("减免金额");
    private JLabel labelStarteDate = new JLabel("起始日期");
    private JLabel labelEndDate = new JLabel("结束日期");

//    private JTextField edtId=new JTextField(20);
    private JTextField edtContent=new JTextField(20);
    private JTextField edtSuitable=new JTextField(20);
    private JTextField edtCredit=new JTextField(20);
    private JTextField edtStartDate=new JTextField(20);
    private JTextField edtEndDate=new JTextField(20);
    public FrmCouponManager_Add(JDialog f, String s, boolean b) {
        super(f, s, b);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labelContent);
        workPane.add(edtContent);
        workPane.add(labelSuitable);
        workPane.add(edtSuitable);
        workPane.add(labelCredit);
        workPane.add(edtCredit);
        workPane.add(labelStarteDate);
        workPane.add(edtStartDate);
        workPane.add(labelEndDate);
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


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            this.pub=null;
            return;
        }
        else if(e.getSource()==this.btnOk){
            pub=new BeanCoupon();
            pub.setContent(this.edtContent.getText());
            if(isNumeric.isNumeric1(this.edtSuitable.getText())) {
                pub.setSuitable_amount(Double.valueOf(this.edtSuitable.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"价格为正数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric1(this.edtCredit.getText())) {
                pub.setCredit_amount(Double.valueOf(this.edtCredit.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"价格为正数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                pub.setStart_date(sdf.parse(this.edtStartDate.getText()));
                pub.setEnd_date(sdf.parse(this.edtEndDate.getText()));
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(null,"请输入正确的时间格式","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                (new DiscountManager()).CreateCoupon(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

    public BeanCoupon getPub() {
        return pub;
    }
}
