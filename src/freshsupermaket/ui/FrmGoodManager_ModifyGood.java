package freshsupermaket.ui;

import freshsupermaket.control.isNumeric;
import freshsupermaket.model.BeanGood;
import freshsupermaket.model.BeanGoodType;
import freshsupermaket.util.BaseException;
import freshsupermaket.control.GoodInformationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;


public class FrmGoodManager_ModifyGood extends JDialog implements ActionListener {
    private BeanGood good=null;

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelGoodid = new JLabel("商品编号：");
    private JLabel labelGoodName = new JLabel("商品名称：");
    private JLabel labelGoodPrice = new JLabel("商品价格");
    private JLabel labelGoodMemberPrice = new JLabel("商品会员价");
    private JLabel labelGoodQuantity = new JLabel("商品数量");
    private JLabel labelGoodSpecification = new JLabel("商品规格");
    private JLabel labelGoodDetail = new JLabel("商品描述");
    private JLabel labelGoodType = new JLabel("商品类别：");

    private JTextField edtId = new JTextField(20);
    private JTextField edtName = new JTextField(20);
    private JTextField edtPrice =new JTextField(20);
    private JTextField edtMemberPrice=new JTextField(20);
    private JTextField edtQuantity=new JTextField(20);
    private JTextField edtSpecification=new JTextField(20);
    private JTextField edtDetail=new JTextField(20);
    private Map<String, BeanGoodType> goodTypeMap_name=null;
    private JComboBox cmbGoodtype=null;

    public FrmGoodManager_ModifyGood(JDialog f, String s, boolean b, Map<String,BeanGoodType> gtMap, BeanGood r) {
        super(f, s, b);
        this.good=r;
        this.goodTypeMap_name=gtMap;
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnOk);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);
        workPane.add(labelGoodid);
        this.edtId.setEnabled(false);
        this.edtId.setText(String.valueOf(this.good.getGood_id()));
        workPane.add(edtId);
        workPane.add(labelGoodName);
        this.edtName.setText(r.getGood_name());
        workPane.add(edtName);



        workPane.add(labelGoodPrice);
        this.edtPrice.setText(String.valueOf(r.getPrice()));
        workPane.add(edtPrice);

        workPane.add(labelGoodMemberPrice);
        this.edtMemberPrice.setText(String.valueOf(r.getMember_price()));
        workPane.add(edtMemberPrice);

        workPane.add(labelGoodQuantity);
        this.edtQuantity.setText(String.valueOf(r.getQuantity()));
        workPane.add(edtQuantity);
        workPane.add(labelGoodSpecification);
        this.edtSpecification.setText(r.getSpecification());
        workPane.add(edtSpecification);
        workPane.add(labelGoodDetail);
        this.edtDetail.setText(r.getDetail());
        workPane.add(edtDetail);
        workPane.add(labelGoodType);

        //提取商品类别信息
        String[] strTypes=new String[this.goodTypeMap_name.size()+1];
        strTypes[0]="";
        java.util.Iterator<BeanGoodType> itRt=this.goodTypeMap_name.values().iterator();
        int i=1;
        int oldIndex=0;
        while(itRt.hasNext()){
            BeanGoodType gt=itRt.next();
            strTypes[i]=gt.getType_name();
            if(this.good.getType_id()==gt.getType_id()){
                oldIndex=i;
            }
            i++;
        }
        cmbGoodtype=new JComboBox(strTypes);
        this.cmbGoodtype.setSelectedIndex(oldIndex);
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
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                FrmGoodManager_ModifyGood.this.good=null;
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel) {
            this.setVisible(false);
            this.good=null;
            return;
        }
        else if(e.getSource()==this.btnOk){
            if(this.cmbGoodtype.getSelectedIndex()<0){
                JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            int id=Integer.valueOf(this.edtId.getText());
            String name=this.edtName.getText();
            if(isNumeric.isNumeric1(this.edtPrice.getText()));
            String specification=this.edtSpecification.getText();
            String detail=this.edtDetail.getText();
            BeanGood g=new BeanGood();
            g.setGood_id(id);
            g.setGood_name(name);
            if(isNumeric.isNumeric1(this.edtPrice.getText())){
                g.setPrice(Double.valueOf(this.edtPrice.getText()));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "单价为正数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric1(this.edtMemberPrice.getText())){
                g.setMember_price(Double.valueOf(this.edtMemberPrice.getText()));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "会员价为正数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(isNumeric.isNumeric(this.edtQuantity.getText())){
                g.setQuantity(Integer.valueOf(this.edtQuantity.getText()));
            }
            else
            {
                JOptionPane.showMessageDialog(null, "数量为正整数","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            g.setSpecification(specification);
            g.setDetail(detail);
            String gtName=this.cmbGoodtype.getSelectedItem().toString();
            BeanGoodType gt=this.goodTypeMap_name.get(gtName);
            int Type=gt.getType_id();
            g.setType_id(Type);
            if(gt==null){
                JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                (new GoodInformationManager()).ModifyGood(g);
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
