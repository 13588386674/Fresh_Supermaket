package freshsupermaket.ui;

import freshsupermaket.control.SystemUserManager;
import freshsupermaket.model.BeanGoodPurchase;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmSystemPurchase extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd= new Button("添加");
    private Button btnModify = new Button("修改订单状态");
    private JComboBox cmbState=new JComboBox(new String[]{"入库","下单","在途"});
    private JTextField edtKeyword = new JTextField(10);
    private Button btnSearch = new Button("查询");
    private Object tblTitle[]={"采购单编号","商品编号","数量","状态"};
    private Object tblData[][];
    List<BeanGoodPurchase> pubs=null;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);

    private void reloadTable(){
        try {
            pubs=(new SystemUserManager()).SearchGoodPurchase(this.edtKeyword.getText(), this.cmbState.getSelectedItem().toString());
            tblData =new Object[pubs.size()][4];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getPurchase_id();
                tblData[i][1]=pubs.get(i).getGood_id();
                tblData[i][2]=pubs.get(i).getQuantity();
                tblData[i][3]=pubs.get(i).getState();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmSystemPurchase(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(cmbState);
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
            FrmSystemPurchase_Add dlg=new FrmSystemPurchase_Add(this,"添加采购单",true);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择采购单","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanGoodPurchase p=this.pubs.get(i);
            if(p.getState().equals("入库")){
                JOptionPane.showMessageDialog(null,"已入库的订单无法更改","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            FrmSystemPurchase_Modify dlg=new FrmSystemPurchase_Modify(this,"修改采购单状态",true,p);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnSearch){
            this.reloadTable();
        }

    }
}
