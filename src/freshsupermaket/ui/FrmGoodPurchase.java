package freshsupermaket.ui;

import freshsupermaket.control.*;
import freshsupermaket.model.BeanGood;
import freshsupermaket.model.BeanGoodType;
import freshsupermaket.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class FrmGoodPurchase extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加至购物车");
    private JTextField  edtAdd = new JTextField(10);
    private Button btnLook = new Button("查看购物车");
    private Button btnDelete = new Button("删除");
    private Map<String, BeanGoodType> goodTypeMap_name=new HashMap<String,BeanGoodType>();
    private Map<Integer,BeanGoodType> goodTypeMap_id=new HashMap<Integer,BeanGoodType>();
    private JComboBox cmbGoodtype=null;

    private JTextField edtKeyword = new JTextField(10);
    private Button btnSearch = new Button("查询");

    private Object tblTitle[]={"商品编号","商品名称","类别","价格","会员价","数量","规格","描述"};
    private Object tblData[][];
    List<BeanGood> Goods=null;
    public   List<BeanGood> CartGood=new ArrayList<BeanGood>();
    public static int CartGoodQuantiy[][];
    int count=1;
    DefaultTableModel tablmod=new DefaultTableModel();
    private JTable GoodTable=new JTable(tablmod);
    private void reloadTable(){
        try {
            int n=this.cmbGoodtype.getSelectedIndex();
            int gtId=0;
            if(n>=0){
                String rtname=this.cmbGoodtype.getSelectedItem().toString();
                BeanGoodType rt=this.goodTypeMap_name.get(rtname);
                if(rt!=null) gtId=rt.getType_id();
            }
            Goods =(new GoodInformationManager()).searchGood(this.edtKeyword.getText(), gtId);

            tblData =new Object[Goods.size()][8];
            for(int i=0;i<Goods.size();i++){
                tblData[i][0]=Goods.get(i).getGood_id();
                tblData[i][1]=Goods.get(i).getGood_name();
                BeanGoodType t=this.goodTypeMap_id.get(Goods.get(i).getType_id());
                tblData[i][2]=t==null?"":t.getType_name();
                tblData[i][3]=Goods.get(i).getPrice();
                tblData[i][4]=Goods.get(i).getMember_price();
                tblData[i][5]=Goods.get(i).getQuantity();
                tblData[i][6]=Goods.get(i).getSpecification();
                tblData[i][7]=Goods.get(i).getDetail();

            }
            tablmod.setDataVector(tblData,tblTitle);
            this.GoodTable.validate();
            this.GoodTable.repaint();
        } catch (BaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public FrmGoodPurchase(Frame f, String s, boolean b) {
        super(f, s, b);
        //提取商品类别信息
        List<BeanGoodType> types=null;
        try {
            types = (new GoodInformationManager()).LoadAllGoodType();
            String[] strTypes=new String[types.size()+1];
            strTypes[0]="";
            for(int i=0;i<types.size();i++) {
                goodTypeMap_name.put(types.get(i).getType_name(),types.get(i));
                goodTypeMap_id.put(types.get(i).getType_id(), types.get(i));
                strTypes[i+1]=types.get(i).getType_name();
            }
            cmbGoodtype=new JComboBox(strTypes);
        } catch (BaseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(edtAdd);
        toolBar.add(btnLook);
//        toolBar.add(this.btnDelete);
        toolBar.add(cmbGoodtype);
        toolBar.add(edtKeyword);
        toolBar.add(btnSearch);


        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        //提取现有数据
        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.GoodTable), BorderLayout.CENTER);

        // 屏幕居中显示
        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnLook.addActionListener(this);

        this.btnDelete.addActionListener(this);
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
            int i=this.GoodTable.getSelectedRow();
            if(i<0) {
                JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }
            BeanGood good=this.Goods.get(i);
            if (isNumeric.isNumeric(this.edtAdd.getText())){
                if (Integer.valueOf(this.edtAdd.getText())<good.getQuantity()){
                    String name=(new CustomerManager().currentLoginCustomer.getCustomer_id());
                    try {
                        (new OrderManager()).AddGoodToCart(good.getGood_id(),name,count++,Integer.valueOf(this.edtAdd.getText()));
                    }catch (BaseException e1){
                        JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
                    }
                    this.reloadTable();
                }
                else {
                    JOptionPane.showMessageDialog(null,  "库存不足","提示",JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }else {
                JOptionPane.showMessageDialog(null,  "不是正确的购买数字","提示",JOptionPane.ERROR_MESSAGE);
                return;
            }

        }
        else if(e.getSource()==this.btnLook){

            FrmCart dlg=new FrmCart(null,"查看购物车",true);
            dlg.setVisible(true);
            this.reloadTable();

        }

//        else if(e.getSource()==this.btnDelete){
//            int i=this.GoodTable.getSelectedRow();
//            if(i<0) {
//                JOptionPane.showMessageDialog(null,  "请选择商品","提示",JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            BeanGood reader=this.Goods.get(i);
//            if(JOptionPane.showConfirmDialog(this,"确定删除该商品吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
//                try {
//                    (new GoodInformationManager()).DeleteGood(reader.getGood_id());
//                    this.reloadTable();
//                } catch (BaseException e1) {
//                    JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
//                }
//
//            }
//        }
        else if(e.getSource()==this.btnSearch){
            this.reloadTable();
        }
    }
}
