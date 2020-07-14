package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanGoodAndRecipe;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrmGoodAndRecipeManager_Add extends JDialog implements ActionListener {
    private BeanGoodAndRecipe pub=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelGood_Id = new JLabel("商品编号：");
    private JLabel labelRecipe_Id = new JLabel("菜谱编号：");
    private JLabel labelContent = new JLabel("描述：");



    private JTextField edtGood_Id = new JTextField(20);
    private JTextField edtRecipe_Id = new JTextField(20);
    private JTextField edtContent = new JTextField(20);


    public FrmGoodAndRecipeManager_Add(JDialog f, String s, boolean b) {
        super(f, s, b);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelGood_Id);
        workPane.add(edtGood_Id);
        workPane.add(labelRecipe_Id);
        workPane.add(edtRecipe_Id);
        workPane.add(labelContent);
        workPane.add(edtContent);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(340, 300);
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

            pub=new BeanGoodAndRecipe();
            if(isNumeric.isNumeric(this.edtGood_Id.getText())) {
                pub.setGood_id(Integer.valueOf(this.edtGood_Id.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null, "商品编号为数字", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            pub.setContent(this.edtContent.getText());
            if(isNumeric.isNumeric(this.edtRecipe_Id.getText())) {
                pub.setRecipe_id(Integer.valueOf(this.edtRecipe_Id.getText()));
            }
            else {
                JOptionPane.showMessageDialog(null, "菜谱编号为数字", "提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if(!(new CheckForeign()).CheckGoodAndRecipeForeign(pub.getGood_id(),pub.getRecipe_id())){
                    JOptionPane.showMessageDialog(null,"不存在此商品或菜谱","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(new GoodInformationManager().CreateGoodAndRecipe(pub)==false){
                    JOptionPane.showMessageDialog(null,"不能创建相同的商品菜谱关联","错误",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                this.setVisible(false);
            } catch (BaseException e1) {
                this.pub=null;
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public BeanGoodAndRecipe getPub() {
        return pub;
    }
}
