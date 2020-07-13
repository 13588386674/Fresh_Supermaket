package freshsupermaket.ui;

import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.model.BeanRecipe;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmRecipeManage_Add extends JDialog implements ActionListener {
    private BeanRecipe pub=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    
//    private JLabel labelRecipe_Id = new JLabel("菜谱编号");
    private JLabel labelRecipe_Name = new JLabel("菜谱名称");
    private JLabel labelRecipe_Material = new JLabel("菜谱用料");
    private JLabel labelRecipe_Steps = new JLabel("步骤   ");
    private JLabel labelDetail = new JLabel("详情   ");
    
//    private JTextField edtRecipe_Id =new JTextField(20);
    private JTextField edtRecipe_Name =new JTextField(20);
    private JTextField edtRecipe_Material =new JTextField(20);
    private JTextField edtRecipe_Steps =new JTextField(20);
    private JTextField edtDetail = new JTextField(20);
    
    public FrmRecipeManage_Add(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
//        workPane.add(labelRecipe_Id);
//        workPane.add(edtRecipe_Id);
        workPane.add(labelRecipe_Name);
        workPane.add(edtRecipe_Name);
        workPane.add(labelRecipe_Material);
        workPane.add(edtRecipe_Material);
        workPane.add(labelRecipe_Steps);
        workPane.add(edtRecipe_Steps);
        workPane.add(labelDetail);
        workPane.add(edtDetail);
   
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 300);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            return;
        }
        else if(e.getSource()==this.btnOk){

            pub=new BeanRecipe();


            pub.setRecipe_name(this.edtRecipe_Name.getText());
            pub.setRecipe_material(this.edtRecipe_Material.getText());
            pub.setSteps(this.edtRecipe_Steps.getText());
            pub.setDetail(this.edtDetail.getText());
            try{
                (new GoodInformationManager()).CreateRecipe(pub);
                this.setVisible(false);
            }catch (BaseException e1){
                this.pub=null;
                JOptionPane.showMessageDialog(null,e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }

        }

    }
    public BeanRecipe getPub() {
        return pub;
    }
    
}
