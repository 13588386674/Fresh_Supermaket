package freshsupermaket.ui;

import freshsupermaket.control.CheckForeign;
import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanGood;
import freshsupermaket.model.BeanGoodType;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import freshsupermaket.control.GoodInformationManager;

public class FrmGoodManager_Add extends JDialog implements ActionListener {
    private BeanGood good=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
//    private JLabel labelGoodid = new JLabel("商品编号：");
    private JLabel labelGoodName = new JLabel("商品名称：");
    private JLabel labelGoodPirce = new JLabel("商品价格");
    private JLabel labelGoodMemberPrice = new JLabel("会员价 ");
    private JLabel labelQuantity = new JLabel("数量   ");
    private JLabel labelSpecification =new JLabel("规格   ");
    private JLabel labelDetail = new JLabel("描述   ");
    private JLabel labelGoodType = new JLabel("商品类别：");


//    private JTextField edtId = new JTextField(20);
    private JTextField edtName = new JTextField(20);
    private JTextField edtGoodPirce = new JTextField(20);
    private JTextField edtGoodMemberPrice = new JTextField(20);
    private JTextField edtQuantity = new JTextField(20);
    private JTextField edtSpecification =new JTextField(20);
    private JTextField edtlDetail = new JTextField(20);
    private Map<String, BeanGoodType> goodTypeMap_name=null;
    private JComboBox cmbGoodtype=null;
    public FrmGoodManager_Add(JDialog f, String s, boolean b, Map<String,BeanGoodType> gtMap) {
        super(f, s, b);
        this.goodTypeMap_name=gtMap;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelGoodName);
        workPane.add(edtName);
        workPane.add(labelGoodPirce);
        workPane.add(edtGoodPirce);
        workPane.add(labelGoodMemberPrice);
        workPane.add(edtGoodMemberPrice);
        workPane.add(labelQuantity);
        workPane.add(edtQuantity);
        workPane.add(labelSpecification);
        workPane.add(edtSpecification);
        workPane.add(labelDetail);
        workPane.add(edtlDetail);

        workPane.add(labelGoodType);
        //提取商品类别信息
        String[] strTypes=new String[this.goodTypeMap_name.size()+1];
        strTypes[0]="";
        java.util.Iterator<BeanGoodType> itRt=this.goodTypeMap_name.values().iterator();
        int i=1;
        while(itRt.hasNext()){
            strTypes[i]=itRt.next().getType_name();
            i++;
        }
        cmbGoodtype=new JComboBox(strTypes);
        workPane.add(cmbGoodtype);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(320, 360);
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
            if(this.cmbGoodtype.getSelectedIndex()<0){
                JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanGood r=new BeanGood();
//            if (isNumeric.isNumeric(this.edtId.getText())){
//                r.setGood_id(Integer.valueOf(this.edtId.getText()));
//            }
//            else {
//                JOptionPane.showMessageDialog(null, "商品编号为数字","错误",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            r.setGood_name(this.edtName.getText());
            if(isNumeric.isNumeric1(this.edtGoodPirce.getText())){
                r.setPrice(Double.valueOf(this.edtGoodPirce.getText()));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "单价为正数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric1(this.edtGoodMemberPrice.getText())){
                r.setMember_price(Double.valueOf(this.edtGoodMemberPrice.getText()));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "会员价为正数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric(this.edtQuantity.getText())){
                r.setQuantity(Integer.valueOf(this.edtQuantity.getText()));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "数量为正整数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            r.setSpecification(this.edtSpecification.getText());
            r.setDetail(this.edtlDetail.getText());
            String gtName=this.cmbGoodtype.getSelectedItem().toString();
            BeanGoodType gt=this.goodTypeMap_name.get(gtName);
            if(gt==null){
                JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            r.setType_id(gt.getType_id());
            try {
                (new GoodInformationManager()).CreateGood(r);
                this.good=r;
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    public BeanGood getGood() {
        return good;
    }
}
