package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.DiscountManager;
import freshsupermaket.model.BeanCoupon;
import freshsupermaket.model.BeanCustomer;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmCouponManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加优惠券");
    private Button btnModify = new Button("修改优惠券");
    private Button btnDelete = new Button("删除优惠券");
    private Object tblTitle[]={"优惠券编号","内容","适用金额","减免金额","起始日期","结束日期"};
    private Object tblData[][];
    List<BeanCoupon> pubs=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable() {
        try {
            pubs=(new DiscountManager()).LoadCoupon();

            tblData = new Object[pubs.size()][6];
            for (int i = 0; i < pubs.size(); i++) {
                tblData[i][0] = pubs.get(i).getCoupon_id() + "";
                tblData[i][1] = pubs.get(i).getContent();
                tblData[i][2] = pubs.get(i).getSuitable_amount();
                tblData[i][3] = pubs.get(i).getCredit_amount();
                tblData[i][4] = pubs.get(i).getStart_date();
                tblData[i][5] = pubs.get(i).getEnd_date();
            }
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmCouponManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(this.btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(1200, 800);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //System.exit(0);
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==this.btnAdd){
            FrmCouponManager_Add dlg=new FrmCouponManager_Add(this,"添加优惠券",true);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择优惠券","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCoupon c=this.pubs.get(i);
            FrmCouponManager_Modify dlg=new FrmCouponManager_Modify(this,"修改优惠券",true,c);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择优惠券","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCoupon c=this.pubs.get(i);
            if(JOptionPane.showConfirmDialog(this,"确定删除"+c.getCoupon_id()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    if(!(new CheckForeign()).CheckCouponForeign(c.getCoupon_id())){
                        JOptionPane.showMessageDialog(null,"该优惠劵已被使用不能删除","错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    (new DiscountManager()).DeleteCoupon(c.getCoupon_id());
                    this.reloadTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        }

    }
}
