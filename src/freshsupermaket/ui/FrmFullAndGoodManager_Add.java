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
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmFullAndGoodManager_Add extends JDialog implements ActionListener {
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
    public FrmFullAndGoodManager_Add(JDialog f, String s, boolean b) {
        super(f, s, b);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        workPane.add(labelGoodid);
        workPane.add(edtGoodid);
        workPane.add(labelFullReductionid);
        workPane.add(edtFullReductionid);
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
            pub=new BeanFullAndGood();
            if(isNumeric.isNumeric(this.edtFullReductionid.getText())) {
                pub.setFull_reduction_id(Integer.valueOf(this.edtFullReductionid.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"满折编号为数字","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric(this.edtGoodid.getText())) {
                pub.setGood_id(Integer.valueOf(this.edtGoodid.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"商品编号为数字","错误",JOptionPane.ERROR_MESSAGE);
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
                if(!(new CheckForeign()).CheckFullAndGoodForeign(pub.getGood_id(),pub.getFull_reduction_id())){
                    JOptionPane.showMessageDialog(null,"不存在此商品或满折","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if((new DiscountManager()).CreateFullAndGood(pub)==false){
                    JOptionPane.showMessageDialog(null,"不能创建相同商品满折信息","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }

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
