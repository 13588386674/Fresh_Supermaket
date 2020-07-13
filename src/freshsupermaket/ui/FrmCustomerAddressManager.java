package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.OrderManager;
import freshsupermaket.model.BeanAddress;
import freshsupermaket.util.BaseException;
import org.hibernate.annotations.Check;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmCustomerAddressManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加地址");
    private Button btnModify = new Button("修改地址");
    private Button btnDelete = new Button("删除地址");
    private Button btnOk =new Button("确认地址");
//    private JTextField edtKeyword = new JTextField(10);
//    private Button btnSearch = new Button("查询");
    private Object tblTitle[]={"省","市","区","地址","联系人","电话"};
    private Object tblData[][];
    List<BeanAddress> pubs;

    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            pubs=(new CustomerManager()).SearchAddressC(CustomerManager.currentLoginCustomer.getCustomer_id());
            tblData =new Object[pubs.size()][6];
            for(int i=0;i<pubs.size();i++){
//                tblData[i][0]=pubs.get(i).getAddress_id()+"";
//                tblData[i][1]=pubs.get(i).getCustomer_id();
                tblData[i][0]=pubs.get(i).getProvince();
                tblData[i][1]=pubs.get(i).getCity();
                tblData[i][2]=pubs.get(i).getArea();
                tblData[i][3]=pubs.get(i).getAddress();
                tblData[i][4]=pubs.get(i).getConnect_person();
                tblData[i][5]=pubs.get(i).getConnect_number();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmCustomerAddressManager(Frame f, String s, boolean b,int i) {
        super(f, s, b);
        toolBar.setVisible(true);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(this.btnDelete);
        if(i==1) {
            toolBar.add(btnOk);
        }
//        toolBar.add(edtKeyword);
//        toolBar.add(btnSearch);
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
        if(i==1) {
            this.btnOk.addActionListener(this);
        }
//        this.btnSearch.addActionListener(this);
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
            FrmCustomerAddressManager_Add dlg=new FrmCustomerAddressManager_Add(this,"添加地址",true);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择地址","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanAddress p=this.pubs.get(i);
            FrmCustomerAddressManager_Modify dlg=new FrmCustomerAddressManager_Modify(this,"修改地址",true,p);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择地址","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanAddress p=this.pubs.get(i);
            if(JOptionPane.showConfirmDialog(this,"确定删除"+p.getAddress_id()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    if(!(new CheckForeign()).CheckAddressForeign(p.getAddress_id())){
                        JOptionPane.showMessageDialog(null,"该地址不能删除","错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    (new CustomerManager()).DeleteAddress(p.getAddress_id());
                    this.reloadTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        else if(e.getSource()==this.btnOk){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择地址","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanAddress p=this.pubs.get(i);
            try {
                (new OrderManager()).ModifyOrderState(p);


            }catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null,"支付成功","通知",JOptionPane.ERROR_MESSAGE);

            this.reloadTable();
        }
    }
}
