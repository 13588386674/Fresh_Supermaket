package freshsupermaket.ui;

import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.OrderManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanCoupon;
import freshsupermaket.model.BuyCart;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.List;

public class FrmCart  extends JDialog implements ActionListener{
    private JPanel toolBar =new JPanel();
    private Button btnReduce =new Button("移出购物车");
    private Button btnPay =new Button("支付");

    private JLabel labelTotal =new JLabel();
    private JLabel reduction= new JLabel();
    private JLabel finallyTotal =new JLabel();
    private JTextField edtQuantity = new JTextField(10);

    private Object tblTitle[]={"商品编号","商品名称","价格","会员价","最低价","数量","满减编号","折扣","满折信息"};
    private Object tblData[][];
    List<BuyCart> pubs=null;
    double Total=0;
    double reductionMoney=0;
    double finallyTotalMoney=0;
    private double FinallyMoneyAfterReduction=0;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable GoodTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            pubs =(new OrderManager()).LoadCart();
            (new OrderManager()).Total(pubs);
            (new OrderManager()).DropView();
            tblData =new Object[pubs.size()][9];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getGood_id();
                tblData[i][1]=pubs.get(i).getGood_name();
                tblData[i][2]=pubs.get(i).getPrice();
                tblData[i][3]=pubs.get(i).getMemberPrice();
                tblData[i][4]=pubs.get(i).getFinallyPrice();
                tblData[i][5]=pubs.get(i).getQuantity();
                tblData[i][6]=pubs.get(i).getFull_reduction_id();
                tblData[i][7]=pubs.get(i).getDiscount();
                tblData[i][8]=pubs.get(i).getContent();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.GoodTable.validate();
            this.GoodTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmCart(Frame f, String s, boolean b) {
        super(f, s, b);



        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnReduce);
        toolBar.add(edtQuantity);
        toolBar.add(btnPay);
//        toolBar.add(btnCancel);
//        toolBar.add(cmbGoodtype);
        toolBar.add(labelTotal);
        toolBar.add(reduction);
        toolBar.add(finallyTotal);




        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();
        try {
            Total=(new OrderManager()).SearchTotal();
            this.labelTotal.setText("商品金额:"+Total);
            finallyTotalMoney=(new  OrderManager()).SearchFinallyTotal();
            reductionMoney=Total-finallyTotalMoney;
            DecimalFormat df = new DecimalFormat("0.00");
            String s1 = df.format(reductionMoney);
            BeanCoupon c=(new OrderManager()).SearchCoupon(finallyTotalMoney);
            this.reduction.setText("减免金额"+s1+"优惠金额:"+c.getCredit_amount());
            this.finallyTotal.setText("最终金额："+(finallyTotalMoney-c.getCredit_amount()));
//            this.finallyTotal.setText("最终金额"+finallyTotalMoney);
            //记录使用优惠券后的金额，如果之后点击支付和地址，则把该金额更新到order表中
            FinallyMoneyAfterReduction=finallyTotalMoney-c.getCredit_amount();
            (new OrderManager()).ModifyOrderSettlement(FinallyMoneyAfterReduction);
        }catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.getContentPane().add(new JScrollPane(this.GoodTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnPay.addActionListener(this);
//        this.btnCancel.addActionListener(this);

        this.btnReduce.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==this.btnPay){
            FrmCustomerAddressManager dlg= new FrmCustomerAddressManager(null,"顾客地址管理",true,1);
            dlg.setVisible(true);
            this.reloadTable();
        }
//        else if(e.getSource()==this.btnCancel){
//            BeanGood good=new BeanGood();
//            FrmGoodManager_ModifyGood dlg=new FrmGoodManager_ModifyGood(this,"修改商品",true,this.goodTypeMap_name,good);
//            dlg.setVisible(true);
//            if(dlg.getGood()!=null){//刷新表格
//                this.reloadTable();
//            }
//        }
        else if(e.getSource()==this.btnReduce){
            int i=this.GoodTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BuyCart good=this.pubs.get(i);
            if (isNumeric.isNumeric(this.edtQuantity.getText())&&this.edtQuantity.getText()!=null&&!"".equals(this.edtQuantity.getText())){
                if (Integer.valueOf(this.edtQuantity.getText())<=good.getQuantity()){
                    String name=(new CustomerManager().currentLoginCustomer.getCustomer_id());
                    try {
                        (new OrderManager()).ReduceFromCart(good.getGood_id(),name,Integer.valueOf(this.edtQuantity.getText()));
                    }catch (BaseException e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                    }
                    this.reloadTable();
                }
                else {
                    JOptionPane.showMessageDialog(null,  "不符合移除数量","提示",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }else {
                JOptionPane.showMessageDialog(null,  "不符合移除数量","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.reloadTable();
        }
//
    }

    public double getFinallyMoneyAfterReduction() {
        return FinallyMoneyAfterReduction;
    }



}
