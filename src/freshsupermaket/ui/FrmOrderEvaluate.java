package freshsupermaket.ui;

import com.sun.org.apache.xpath.internal.operations.Or;
import freshsupermaket.control.GoodInformationManager;
import freshsupermaket.control.OrderManager;
import freshsupermaket.model.BeanGood;
import freshsupermaket.model.BeanGoodType;
import freshsupermaket.model.BeanOrder;
import freshsupermaket.util.BaseException;
import org.hibernate.criterion.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

public class FrmOrderEvaluate extends JDialog implements ActionListener {
    private BeanOrder good = null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelContent = new JLabel("内容：");
    private JLabel labelStar = new JLabel("星级");


    private JTextField edtContent = new JTextField(20);
    private JTextField edtStar = new JTextField(20);

    public FrmOrderEvaluate(JDialog f, String s, boolean b, BeanOrder r) {
        super(f, s, b);
        this.good=r;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelContent);
        workPane.add(edtContent);
        workPane.add(labelStar);
        workPane.add(edtStar);


        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 160);
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
                FrmOrderEvaluate.this.good=null;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
            return;
        } else if (e.getSource() == this.btnOk) {
            BeanOrder o=new BeanOrder();
            o.setOrder_id(this.good.getOrder_id());
            o.setContent(this.edtContent.getText());
            o.setStar(this.edtStar.getText());
            try {
                (new OrderManager()).Evaluate(o);
            }catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null,"评价成功","提示",JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
        }

    }
    public BeanOrder getGood() {
        return good;
    }
}
