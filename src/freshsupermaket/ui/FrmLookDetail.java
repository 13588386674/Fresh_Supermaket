package freshsupermaket.ui;

import freshsupermaket.control.OrderManager;
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
import java.util.List;

public class FrmLookDetail extends JDialog implements ActionListener{
    private JPanel toolBar =new JPanel();

    private Object tblTitle[]={"商品编号","商品名称","价格","会员价","最低价","数量","满减编号","折扣","满折信息"};
    private Object tblData[][];
    List<BuyCart> pubs=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable GoodTable=new JTable(tablmod);
    private void reloadTable(int id){
        try {
            pubs =(new OrderManager()).LoadOrderDetail(id);
            tblData =new Object[pubs.size()][9];
            double sum=0;
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getGood_id();
                tblData[i][1]=pubs.get(i).getGood_name();
//                BeanGoodType t=this.goodTypeMap_id.get(pubs.get(i).getGood_type());
//                tblData[i][2]=t==null?"":t.getType_name();
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
    public FrmLookDetail(Frame f, String s, boolean b,int id) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable(id);

        this.getContentPane().add(new JScrollPane(this.GoodTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }
}
