package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.model.BeanGoodType;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmGoodTypeManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加商品类别");
    private Button btnModify = new Button("修改商品类别信息");
    private Button btnDelete = new Button("删除商品类别");
    private Object tblTitle[]={"类别编号","类别名称","类别描述"};
    private Object tblData[][];
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable goodTypeTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            List<BeanGoodType> types=(new GoodInformationManager()).LoadAllGoodType();
            tblData =new Object[types.size()][3];
            for(int i=0;i<types.size();i++){
                tblData[i][0]=types.get(i).getType_id()+"";
                tblData[i][1]=types.get(i).getType_name();
                tblData[i][2]=types.get(i).getDetail()+"";
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.goodTypeTable.validate();
            this.goodTypeTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmGoodTypeManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnModify);
        toolBar.add(this.btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.goodTypeTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(360, 600);
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
            FrmGoodTypeManager_Add dlg=new FrmGoodTypeManager_Add(this,"添加商品类别",true);
            dlg.setVisible(true);
            if(dlg.getGoodtype()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.goodTypeTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品类别","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int n=Integer.parseInt(this.tblData[i][0].toString());
            BeanGoodType goodtype=new BeanGoodType();
            goodtype.setType_id(n);
            goodtype.setType_name(this.tblData[i][1].toString());
            goodtype.setDetail(this.tblData[i][2].toString());
            FrmGoodTypeManager_Modify dlg=new FrmGoodTypeManager_Modify(this,"添加商品类别",true,goodtype);
            dlg.setVisible(true);
            if(dlg.getGoodtype()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete){
            int i=this.goodTypeTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品类别","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this,"确定删除该类别吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                int n=Integer.parseInt(this.tblData[i][0].toString());
                try {
                    if(!new CheckForeign().CheckGoodTypeForeign(n)){
                        JOptionPane.showMessageDialog(null,"该类别不能删除","错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    (new GoodInformationManager()).DeleteGoodType(n);
                    this.reloadTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
}
