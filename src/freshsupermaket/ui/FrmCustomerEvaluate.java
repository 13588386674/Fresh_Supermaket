package freshsupermaket.ui;

import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.control.OrderManager;
import freshsupermaket.model.BeanOrder;

import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
public class FrmCustomerEvaluate extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnSure = new Button("确认收货");
    private Button btnEvaluate = new Button("订单评价");
    private Button btnLookDetail =new Button("查看订单详情");
    private Object tblTitle[]={"订单编号","优惠券编号","地址编号","原价","实付","订单状态","内容","评价时间","星级"};
    private Object tblData[][];
    List<BeanOrder> pubs;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            pubs=(new OrderManager()).LoadOrder();
            tblData =new Object[pubs.size()][9];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getOrder_id()+"";
                tblData[i][1]=pubs.get(i).getCoupon_id();
                tblData[i][2]=pubs.get(i).getAddress_id();
                tblData[i][3]=pubs.get(i).getOriginal_price();
                tblData[i][4]=pubs.get(i).getSettlement_price();
                tblData[i][5]=pubs.get(i).getOrder_state();
                tblData[i][6]=pubs.get(i).getContent();
                tblData[i][7]=pubs.get(i).getEvaluate_date();
                tblData[i][8]=pubs.get(i).getStar();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmCustomerEvaluate(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnSure);
        toolBar.add(btnEvaluate);
        toolBar.add(btnLookDetail);
//        toolBar.add(this.btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnSure.addActionListener(this);
        this.btnEvaluate.addActionListener(this);
        this.btnLookDetail.addActionListener(this);
//        this.btnDelete.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==this.btnSure){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanOrder p=this.pubs.get(i);
            try {
                (new OrderManager()).Sure(p);
            }catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }

            this.reloadTable();

        }
        else if(e.getSource()==this.btnEvaluate){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanOrder p=this.pubs.get(i);
            FrmOrderEvaluate dlg=new FrmOrderEvaluate(null,"评价订单",true,p);
            dlg.setVisible(true);
            if(dlg.getGood()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==btnLookDetail){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择订单","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanOrder p=this.pubs.get(i);
            FrmLookDetail dlg=new FrmLookDetail(null,"订单详情",true,p.getOrder_id());
            dlg.setVisible(true);
        }

    }
}
