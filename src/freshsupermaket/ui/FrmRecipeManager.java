package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.model.BeanRecipe;
import freshsupermaket.util.BaseException;
import freshsupermaket.util.DButil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmRecipeManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加菜谱");
    private Button btnModify = new Button("修改菜谱");
    private Button btnDelete = new Button("删除菜谱");
    private Object tblTitle[]={"菜谱编号","菜谱名称","菜谱用料","步骤","详情"};
    private Object tblData[][];
    List<BeanRecipe> pubs;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable dataTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            pubs=(new GoodInformationManager()).LoadRecipe();
            tblData =new Object[pubs.size()][5];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getRecipe_id()+"";
                tblData[i][1]=pubs.get(i).getRecipe_name();
                tblData[i][2]=pubs.get(i).getRecipe_material();
                tblData[i][3]=pubs.get(i).getSteps();
                tblData[i][4]=pubs.get(i).getDetail();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public FrmRecipeManager(Frame f, String s, boolean b) {
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
        this.setSize(800, 600);
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
            FrmRecipeManage_Add dlg=new FrmRecipeManage_Add(this,"添加菜谱",true);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnModify){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanRecipe p=this.pubs.get(i);
            FrmRecipeManager_Modify dlg=new FrmRecipeManager_Modify(this,"修改菜谱信息",true,p);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadTable();
            }
        }
        else if(e.getSource()==this.btnDelete){
            int i=this.dataTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择菜谱","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanRecipe p=this.pubs.get(i);
            if(JOptionPane.showConfirmDialog(this,"确定删除"+p.getRecipe_name()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                try {
                    if(!(new CheckForeign()).CheckRecipeForeign(p.getRecipe_id())){
                        JOptionPane.showMessageDialog(null,"该菜谱不能删除","错误",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    (new GoodInformationManager()).DeleteRecipe(p.getRecipe_id());
                    this.reloadTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
}
