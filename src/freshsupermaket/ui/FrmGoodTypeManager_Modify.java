package freshsupermaket.ui;

import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.model.BeanGoodType;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmGoodTypeManager_Modify extends JDialog implements ActionListener {
    private BeanGoodType goodtype=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("类别名称：");
    private JLabel labelDetail = new JLabel("类别描述：");

    private JTextField edtName = new JTextField(20);
    private JTextField edtDetail = new JTextField(20);
    public FrmGoodTypeManager_Modify(JDialog f, String s, boolean b, BeanGoodType gt) {
        super(f, s, b);
        this.goodtype=gt;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelName);
        this.edtName.setText(gt.getType_name());
        workPane.add(edtName);
        workPane.add(labelDetail);
        this.edtDetail.setText(gt.getDetail()+"");
        workPane.add(edtDetail);
        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(360, 140);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();
        this.btnOk.addActionListener(this);
        this.btnCancel.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                FrmGoodTypeManager_Modify.this.goodtype=null;
            }
        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            this.goodtype=null;
            return;
        }
        else if(e.getSource()==this.btnOk){
            String name=this.edtName.getText();
            String detail=this.edtDetail.getText();
            this.goodtype.setDetail(detail);
            this.goodtype.setType_name(name);
            try {
                (new GoodInformationManager()).ModifyGoodType(this.goodtype);
                this.setVisible(false);
            } catch (BaseException e1) {
                this.goodtype=null;
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public BeanGoodType getGoodtype() {
        return goodtype;
    }
}
