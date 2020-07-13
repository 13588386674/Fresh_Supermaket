package freshsupermaket.ui;

import freshsupermaket.control.DiscountManager;
import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.model.BeanFullAndGood;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmFullAndGoodManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加满折商品关联信息");
    private Button btnModify = new Button("修改满折商品关联信息");
    private Button btnDelete = new Button("删除满折商品关联信息");
    private Button btnSearch = new Button("查询");
    private JTextField edtKeyword = new JTextField(10);
    private Object tblTitle[] ={"商品编号","满折编号","开始日期","结束日期"};
    private Object tblData[][];
    List<BeanFullAndGood> pubs;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            pubs=(new DiscountManager()).SearchFullAndGood(this.edtKeyword.getText());
            tblData =new Object[pubs.size()][4];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getGood_id()+"";
                tblData[i][1]=pubs.get(i).getFull_reduction_id();
                tblData[i][2] = pubs.get(i).getStart_date();
                tblData[i][3] = pubs.get(i).getEnd_date();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmFullAndGoodManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(this.btnDelete);
        toolBar.add(edtKeyword);
        toolBar.add(btnSearch);
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

        this.btnAdd.addActionListener(this);
        this.btnModify.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnSearch.addActionListener(this);
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
            FrmFullAndGoodManager_Add dlg=new FrmFullAndGoodManager_Add(this,"添加满折商品关联信息",true);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择满折商品关联信息","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanFullAndGood p=this.pubs.get(i);
            FrmFullAndGoodManager_Modify dlg=new FrmFullAndGoodManager_Modify(this,"修改满折商品关联信息",true,p);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择顾客","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanFullAndGood p=this.pubs.get(i);
            if(JOptionPane.showConfirmDialog(this,"确定删除该商品食谱信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    (new DiscountManager()).DeleteFullAndGood(p.getGood_id(),p.getFull_reduction_id());
                    this.reloadTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        else if(e.getSource()==this.btnSearch){
            this.reloadTable();
        }

    }
}
