package freshsupermaket.ui;

import com.sun.javafx.sg.prism.web.NGWebView;
import com.sun.org.apache.bcel.internal.generic.NEW;
import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.OrderManager;
import freshsupermaket.model.BeanCoupon;
import freshsupermaket.model.BeanOrder;
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
public class FrmConsumerStatic extends JDialog implements ActionListener {
    private JPanel toolBar =new JPanel();
    private Object tblTitle[]={"订单编号","优惠编号","地址编号","应付价格","实付价格"};
    private JLabel labelSumOrder=new JLabel();
    private JLabel labelSumMoney= new JLabel();
    private JLabel labelSumReduction =new JLabel();
    private Object tblData[][];
    List<BeanOrder> pubs=null;
    BeanOrder order=new BeanOrder();
    double SumOrder=0;
    double SumMoney=0;
    double SumReduction=0;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable GoodTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            pubs =(new OrderManager()).LoadCustomerOrder();
            //order表中的orderstate,content,star分别用于临时存放总单数，总消费金额，总优惠金额
            order=(new OrderManager()).StaticReductionMoney();
            this.labelSumOrder.setText("总消费单数："+order.getOrder_state());
            if(order.getContent()!=null){
                Double d = Double.parseDouble(order.getContent());
                DecimalFormat df = new DecimalFormat("0.00");
                String s = df.format(d);
                this.labelSumMoney.setText("总消费金额："+s);
            }
            else {
                this.labelSumMoney.setText("总消费金额："+"0.00");
            }
            if(order.getStar()!=null){
                Double d = Double.parseDouble(order.getStar());
                DecimalFormat df = new DecimalFormat("0.00");
                String s = df.format(d);
                this.labelSumReduction.setText("总优惠："+s);
            }
            else {
                this.labelSumReduction.setText("总优惠："+"0.00");
            }


            tblData =new Object[pubs.size()][5];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getOrder_id();
                tblData[i][1]=pubs.get(i).getCoupon_id();
//                BeanGoodType t=this.goodTypeMap_id.get(pubs.get(i).getGood_type());
//                tblData[i][2]=t==null?"":t.getType_name();
                tblData[i][2]=pubs.get(i).getAddress_id();
                tblData[i][3]=pubs.get(i).getOriginal_price();
                tblData[i][4]=pubs.get(i).getSettlement_price();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.GoodTable.validate();
            this.GoodTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmConsumerStatic(Frame f, String s, boolean b) {
        super(f, s, b);



        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(labelSumOrder);
        toolBar.add(labelSumMoney);
        toolBar.add(labelSumReduction);


        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();

        this.getContentPane().add(new JScrollPane(this.GoodTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
//        this.btnCancel.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }
}
