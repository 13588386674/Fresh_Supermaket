package freshsupermaket.ui;

import freshsupermaket.control.SystemUserManager;
import freshsupermaket.model.BeanSystemUser;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class FrmSystemUserManager extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加管理员");
    private Button btnMofifyPwd = new Button("修改当前管理员密码");
    private Button btnDelete = new Button("删除管理员");
    private Object tblTitle[]={"账号","姓名","密码"};
    private Object tblData[][];
    List<BeanSystemUser> pubs;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable userTable=new JTable(tablmod);
    private void reloadUserTable(){
        try {
            pubs=(new SystemUserManager()).LoadAllSystemUser();
            tblData =new Object[pubs.size()][3];
            for(int i=0;i<pubs.size();i++){
                tblData[i][0]=pubs.get(i).getSystem_user_id();
                tblData[i][1]=pubs.get(i).getSystem_user_name();
                tblData[i][2]=pubs.get(i).getPassword();
            }
            tablmod.setDataVector(tblData,tblTitle);
            this.userTable.validate();
            this.userTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmSystemUserManager(Frame f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnMofifyPwd);
        toolBar.add(this.btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadUserTable();
        this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnMofifyPwd.addActionListener(this);
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
            FrmSystemUserManager_Add dlg=new FrmSystemUserManager_Add(this,"添加账号",true);
            dlg.setVisible(true);
            if(dlg.getUser()!=null){//刷新表格
                this.reloadUserTable();
            }
        }
        else if(e.getSource()==this.btnMofifyPwd){
            int i=this.userTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanSystemUser p=this.pubs.get(i);
            FrmSystemUserManager_Modify dlg=new FrmSystemUserManager_Modify(this,"修改密码",true,p);
            dlg.setVisible(true);
            if(dlg.getPub()!=null){//刷新表格
                this.reloadUserTable();
            }


        }
        else if(e.getSource()==this.btnDelete){
            int i=this.userTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(JOptionPane.showConfirmDialog(this,"确定删除账号吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                String userid=this.tblData[i][0].toString();
                try {
                    (new SystemUserManager()).DeleteSystemUser(userid);
                    this.reloadUserTable();
                } catch (BaseException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
    }
}
