package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.DiscountManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanTimePromotion;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FrmTimePromotionManager_Add extends JDialog implements ActionListener {
    BeanTimePromotion pub=null;
    private JPanel toolBar = new JPanel();
    private JPanel workPane=new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");

//    private JLabel labelPromotionid = new JLabel("促销编号");
    private JLabel labelGoodid = new JLabel("商品编号");
    private JLabel labelPrice = new JLabel("促销价格");
    private JLabel labelQuantity =new JLabel("促销数量");
    private JLabel labelStarteDate = new JLabel("起始日期");
    private JLabel labelEndDate = new JLabel("结束日期");

//    private JTextField edtPromotionId= new JTextField(20);
    private JTextField edtGoodid=new JTextField(20);
    private JTextField edtPrice=new JTextField(20);
    private JTextField edtQuantity = new JTextField(20);
    private JTextField edtStartDate=new JTextField(20);
    private JTextField edtEndDate=new JTextField(20);
    public FrmTimePromotionManager_Add(JDialog f, String s, boolean b) {
        super(f, s, b);

        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

//        workPane.add(labelPromotionid);
//        workPane.add(edtPromotionId);
        workPane.add(labelGoodid);
        workPane.add(edtGoodid);
        workPane.add(labelPrice);
        workPane.add(edtPrice);
        workPane.add(labelQuantity);
        workPane.add(edtQuantity);
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
            pub=new BeanTimePromotion();
            if(isNumeric.isNumeric(this.edtGoodid.getText())) {
                pub.setGood_id(Integer.valueOf(this.edtGoodid.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"商品编号为数字","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric1(this.edtPrice.getText())){
                pub.setPrice(Double.valueOf(this.edtPrice.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"促销价格为正数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric(this.edtQuantity.getText())){
                pub.setQuantity(Integer.valueOf(this.edtQuantity.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null,"促销数量为数字","错误",JOptionPane.ERROR_MESSAGE);
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
                if(!(new CheckForeign()).CheckTimePromotionForeign(pub.getGood_id())){
                    JOptionPane.showMessageDialog(null,"该商品不存在","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                (new DiscountManager()).CreateTimePromotion(pub);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

    }

    public BeanTimePromotion getPub() {
        return pub;
    }
}
