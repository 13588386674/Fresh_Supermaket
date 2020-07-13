package freshsupermaket.ui;

import freshsupermaket.control.CustomerManager;
import freshsupermaket.control.SystemUserManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrmMain extends JFrame implements ActionListener {
    private JMenuBar menubar=new JMenuBar();
    private JMenu menu_Manage=new JMenu("系统管理");
    private JMenu menu_buy=new JMenu("购买管理");
    private JMenu menu_search=new JMenu("查询统计");
    private JMenuItem  menuItem_CustomerManager=new JMenuItem("顾客管理");
    private JMenuItem  menuItem_CustomerModify = new JMenuItem("顾客信息修改");
    private JMenuItem  menuItem_CustomerAddressManager=new JMenuItem("顾客地址管理");
    private JMenuItem  menuItem_GoodManager=new JMenuItem("商品管理");
    private JMenuItem  menuItem_GoodTypeManager=new JMenuItem("商品类别管理");
    private JMenuItem  menuItem_RecipeManager = new JMenuItem("菜谱管理");
    private JMenuItem  menuItem_GoodAndRecipe = new JMenuItem("商品菜谱管理");
    private JMenuItem  menuItem_CouponManager=new JMenuItem("优惠劵管理");
    private JMenuItem  menuItem_FullInformationManager=new JMenuItem("满折信息管理");
    private JMenuItem  menuItem_FullAndGood = new JMenuItem("满折商品信息关联");
    private JMenuItem  menuItem_TimePromotionManager = new JMenuItem("限时促销管理");
    private JMenuItem  menuItem_SystemUserManager=new JMenuItem("管理员管理");

    private JMenuItem  menuItem_CustomerBuy=new JMenuItem("顾客购买");
    private JMenuItem  menuItem_CustomerEvaluate=new JMenuItem("顾客收货评价");
    private JMenuItem  menuItem_SystemPurchase=new JMenuItem("管理员采购");

    private JMenuItem  menuItem_CustomerBuySearch=new JMenuItem("用户消费情况查询");
//    private JMenuItem  menuItem_PromotionSearch=new JMenuItem("优惠情况查询");
//    private JMenuItem  menuItem_TypeSearch=new JMenuItem("类别情况统计");
//    private JMenuItem  menuItem_GoodSearch=new JMenuItem("商品情况统计");
//    private JMenuItem  menuItem_OrderSearch=new JMenuItem("订单情况查询");

    private FrmLogin dlgLogin=null;
    private JPanel statusBar = new JPanel();
    public FrmMain(){
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setTitle("生鲜超市管理系统");
        dlgLogin=new FrmLogin(this,"登陆",true);
        dlgLogin.setVisible(true);
        //菜单
        if (dlgLogin.gettype().equals("管理员")) {
            menu_Manage.add(menuItem_CustomerManager);
            menuItem_CustomerManager.addActionListener(this);
//
//            menu_Manage.add(menuItem_CustomerAddressManager);
//            menuItem_CustomerAddressManager.addActionListener(this);

            menu_Manage.add(menuItem_GoodManager);
            menuItem_GoodManager.addActionListener(this);

            menu_Manage.add(menuItem_GoodTypeManager);
            menuItem_GoodTypeManager.addActionListener(this);

            menu_Manage.add(menuItem_RecipeManager);
            menuItem_RecipeManager.addActionListener(this);

            menu_Manage.add(menuItem_GoodAndRecipe);
            menuItem_GoodAndRecipe.addActionListener(this);

            menu_Manage.add(menuItem_CouponManager);
            menuItem_CouponManager.addActionListener(this);

            menu_Manage.add(menuItem_FullInformationManager);
            menuItem_FullInformationManager.addActionListener(this);

            menu_Manage.add(menuItem_FullAndGood);
            menuItem_FullAndGood.addActionListener(this);

            menu_Manage.add(menuItem_TimePromotionManager);
            menuItem_TimePromotionManager.addActionListener(this);

            menu_Manage.add(menuItem_SystemUserManager);
            menuItem_SystemUserManager.addActionListener(this);

            menubar.add(menu_Manage);

//            menu_buy.add(this.menuItem_CustomerBuy);
//            menuItem_CustomerBuy.addActionListener(this);
//            menu_buy.add(this.menuItem_CustomerEvaluate);
//            menuItem_CustomerEvaluate.addActionListener(this);
            menu_buy.add(this.menuItem_SystemPurchase);
            menuItem_SystemPurchase.addActionListener(this);
            menubar.add(menu_buy);

//            menu_search.add(this.menuItem_CustomerBuySearch);
//            menuItem_CustomerBuySearch.addActionListener(this);
//            menu_search.add(this.menuItem_PromotionSearch);
//            menuItem_PromotionSearch.addActionListener(this);
//            menu_search.add(this.menuItem_TypeSearch);
//            menuItem_TypeSearch.addActionListener(this);
//            menu_search.add(this.menuItem_GoodSearch);
//            menuItem_GoodSearch.addActionListener(this);
//            menu_search.add(this.menuItem_OrderSearch);
//            menuItem_OrderSearch.addActionListener(this);

//            menubar.add(this.menu_search);
            this.setJMenuBar(menubar);
            //状态栏
            statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel();
            label.setText("您好!" + SystemUserManager.currentLoginUser.getSystem_user_name());
            statusBar.add(label);
            this.getContentPane().add(statusBar, BorderLayout.SOUTH);
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            this.setVisible(true);
        }
        else if(dlgLogin.gettype().equals("顾客")){
            menu_Manage.add(menuItem_CustomerModify);
            menuItem_CustomerModify.addActionListener(this);
            menu_Manage.add(menuItem_CustomerAddressManager);
            menuItem_CustomerAddressManager.addActionListener(this);
            menu_buy.add(this.menuItem_CustomerBuy);
            menubar.add(menu_Manage);
            menuItem_CustomerBuy.addActionListener(this);
            menubar.add(menu_buy);
            menu_buy.add(this.menuItem_CustomerEvaluate);
            menuItem_CustomerEvaluate.addActionListener(this);
            menu_search.add(this.menuItem_CustomerBuySearch);
            menubar.add(menu_search);
            menuItem_CustomerBuySearch.addActionListener(this);
            this.setJMenuBar(menubar);
            //状态栏
            statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel();
            label.setText("您好!" + CustomerManager.currentLoginCustomer.getCustomer_name());
            statusBar.add(label);
            this.getContentPane().add(statusBar, BorderLayout.SOUTH);
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
            this.setVisible(true);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        if(e.getSource()==this.menuItem_CustomerManager){
            FrmCustomerManager dlg=new FrmCustomerManager(this,"顾客管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_CustomerAddressManager){
            FrmCustomerAddressManager dlg=new FrmCustomerAddressManager(this,"顾客地址管理",true,0);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_GoodManager){
            FrmGoodManager dlg=new FrmGoodManager(this,"商品管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_GoodTypeManager){
            FrmGoodTypeManager dlg=new FrmGoodTypeManager(this,"商品类别管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_RecipeManager){
            FrmRecipeManager dlg=new FrmRecipeManager(this,"菜谱管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_GoodAndRecipe){
            FrmGoodAndRecipeManager dlg=new FrmGoodAndRecipeManager(this,"商品菜谱管理",true);
            dlg.setVisible(true);
        }
        else if (e.getSource()==this.menuItem_CouponManager){
            FrmCouponManager dlg=new FrmCouponManager(this,"优惠券管理",true);
            dlg.setVisible(true);
        }
        else if (e.getSource()==this.menuItem_FullInformationManager){
            FrmFullInformationManager dlg=new FrmFullInformationManager(this,"满折信息管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_FullAndGood){
            FrmFullAndGoodManager dlg=new FrmFullAndGoodManager(this,"满折商品信息关联",true);
            dlg.setVisible(true);
        }
        else if (e.getSource()==this.menuItem_TimePromotionManager){
            FrmTimePromotionManager dlg=new FrmTimePromotionManager(this,"限时促销信息管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_SystemPurchase){
            FrmSystemPurchase dlg=new FrmSystemPurchase(this,"管理员采购",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_SystemUserManager){
            FrmSystemUserManager dlg=new FrmSystemUserManager(this,"管理员管理",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_CustomerBuy){
            FrmGoodPurchase dlg=new FrmGoodPurchase(this,"顾客购买",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_CustomerEvaluate){
            FrmCustomerEvaluate dlg=new FrmCustomerEvaluate(this,"顾客评价",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_CustomerModify){
            FrmCustomerModify dlg =new FrmCustomerModify(this,"顾客修改",true);
            dlg.setVisible(true);
        }
        else if(e.getSource()==this.menuItem_CustomerBuySearch){
            FrmConsumerStatic dlg= new FrmConsumerStatic(this,"顾客消费情况统计",true);
            dlg.setVisible(true);
        }
    }

}
