package freshsupermaket.ui;

import freshsupermaket.control.DiscountManager;
import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.model.BeanTimePromotion;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmTimePromotionManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加限时促销信息");
    private Button btnModify = new Button("修改限时促销信息");
    private Button btnDelete = new Button("删除限时促销信息");
    private Button btnSearch = new Button("查询");
    private JTextField edtKeyword = new JTextField(10);
    private Object tblTitle[] ={"促销编号","商品编号","促销单价","促销数量","促销开始时间","促销结束时间"};
    private Object tblData[][];
    List<BeanTimePromotion> pubs;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            pubs=(new DiscountManager()).SearchTimePromotion(this.edtKeyword.getText());
            tblData =new Object[pubs.size()][6];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getPromotion_id()+"";
                tblData[i][1]=pubs.get(i).getGood_id();
                tblData[i][2]=pubs.get(i).getPrice();
                tblData[i][3]=pubs.get(i).getQuantity();
                tblData[i][4]=pubs.get(i).getStart_date();
                tblData[i][5]=pubs.get(i).getEnd_date();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmTimePromotionManager(Frame f, String s, boolean b) {
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
            FrmTimePromotionManager_Add dlg=new FrmTimePromotionManager_Add(this,"添加限时促销信息",true);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择限时促销信息","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanTimePromotion p=this.pubs.get(i);
            FrmTimePromotionManager_Modify dlg=new FrmTimePromotionManager_Modify(this,"修改限时促销信息",true,p);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择限时促销信息","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanTimePromotion p=this.pubs.get(i);
            if(JOptionPane.showConfirmDialog(this,"确定删除该限时促销信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    (new DiscountManager()).DeleteTimePromotion(p.getPromotion_id());
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
