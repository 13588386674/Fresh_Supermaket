package freshsupermaket.ui;

import freshsupermaket.control.CustomerManager;
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

public class FrmCustomerModify extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
//    private Button btnAdd = new Button("添加顾客");
    private Button btnModify = new Button("修改顾客");
//    private Button btnDelete = new Button("删除顾客");
//    private JTextField edtKeyword = new JTextField(10);
//    private Button btnSearch =new Button("查询");
    private Object tblTitle[]={"顾客ID","姓名","性别","密码","手机号码","邮箱","所在城市","创建日期","是否会员","会员结束日期"};
    private Object tblData[][];
    BeanCustomer pubs=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable() {
        try {
            pubs=(new CustomerManager()).LoadNowCustomer();

            tblData = new Object[1][10];
                tblData[0][0] = pubs.getCustomer_id() + "";
                tblData[0][1] = pubs.getCustomer_name();
                tblData[0][2] = pubs.getCustomer_sex();
                tblData[0][3] = pubs.getPassword();
                tblData[0][4] = pubs.getCustomer_number();
                tblData[0][5] = pubs.getEmail();
                tblData[0][6] = pubs.getCity();
                tblData[0][7] = pubs.getCreate_date();
                tblData[0][8] = pubs.isMember();
                tblData[0][9] = pubs.getFinish_date();

            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmCustomerModify(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
//        toolBar.add(btnAdd);
        toolBar.add(btnModify);
//        toolBar.add(this.btnDelete);
//        toolBar.add(edtKeyword);
//        toolBar.add(btnSearch);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(1600, 800);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

//        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
//        this.btnDelete.addActionListener(this);
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
//        if(e.getSource()==this.btnAdd){
//            FrmCustomerManager_Add dlg=new FrmCustomerManager_Add(this,"添加顾客",true);
//            dlg.setVisible(true);
//            if(dlg.getPub()!=null){//刷新表格
//                this.reloadTable();
//            }
//        }
        if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择顾客","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanCustomer c=this.pubs;
            FrmCustomerManager_Modify dlg=new FrmCustomerManager_Modify(this,"修改顾客",true,c);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
//        else if(e.getSource()==this.btnDelete){
//            int i=this.dataTable.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null,  "请选择顾客","提示",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            BeanCustomer c=this.pubs.get(i);
//            if(JOptionPane.showConfirmDialog(this,"确定删除"+c.getCustomer_name()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
//                try {
//                    (new CustomerManager()).DeleteCustomer(c.getCustomer_id());
//                    this.reloadTable();
//                } catch (BaseException e1) {
//                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
//                }
//
//            }
//        }
//        else if(e.getSource()==this.btnSearch){
//            this.reloadTable();
//        }
//    }

}}
