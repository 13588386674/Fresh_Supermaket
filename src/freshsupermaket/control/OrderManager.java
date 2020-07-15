package freshsupermaket.control;

import freshsupermaket.model.*;
import freshsupermaket.util.BaseException;
import freshsupermaket.util.DButil;
import freshsupermaket.util.DbException;

import javax.lang.model.element.NestingKind;
import javax.swing.*;
import java.io.BufferedReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderManager {
    public List<BeanOrder> LoadCustomerOrder() throws BaseException{
        //加载当前顾客已经支付的订单，一个用户同时只能有一个购物车，及未支付状态订单只有一个
        List<BeanOrder> result=new ArrayList<BeanOrder>();
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select order_id,coupon_id,address_id,original_price,settlement_price" +
                    " from orders where customer_id like ? and order_state not like '未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                BeanOrder o=new BeanOrder();
                o.setOrder_id(rs.getInt(1));
                o.setCoupon_id(rs.getInt(2));
                o.setAddress_id(rs.getInt(3));
                o.setOriginal_price(rs.getDouble(4));
                o.setSettlement_price(rs.getDouble(5));
                result.add(o);
            }
            conn.commit();

        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.rollback();
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }
        return  result;
    }
    public BeanStatic StaticReductionMoney() throws BaseException {
        //利用count，sum聚集函数统计总单数，总实付金额，总优惠金额（满折、优惠券）
        BeanStatic o = new BeanStatic();
        Connection conn = null;
        try {
            conn = DButil.getConnection();
            conn.setAutoCommit(false);
            String sql = "select count(order_id),sum(settlement_price),sum(original_price-settlement_price)" +
                    " from orders where customer_id like ? and order_state not like '未支付'";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, CustomerManager.currentLoginCustomer.getCustomer_id());
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                o.setSumOrder(rs.getString(1));
                o.setSumMoney(rs.getString(2));
                o.setSumReduction(rs.getString(3));
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return o;
    }
    public void DropView() throws BaseException{
        //删除buycart视图
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="drop view buycart";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.execute();
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
    public BeanCoupon SearchCoupon(double money) throws BaseException{
        BeanCoupon c=new BeanCoupon();
        //查询最大金额的可用优惠券
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select coupon_id,credit_amount from coupon where coupon_id in (select coupon_id" +
                    " from coupon where suitable_amount in(select max(suitable_amount)FROM coupon where suitable_amount<=?))";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setDouble(1,money);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                c.setCoupon_id(rs.getInt(1));
                c.setCredit_amount(rs.getDouble(2));
                sql="update orders set coupon_id=? where customer_id like ? and order_state like '未支付'";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,rs.getInt(1));
                pst.setString(2,CustomerManager.currentLoginCustomer.getCustomer_id());
                pst.execute();
            }

            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return c;
    }
    public double SearchTotal() throws BaseException{
        //查询当前购物顾客的订单应付金额
        double total=0;
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select original_price from orders where customer_id like ? and order_state like'未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()) {
                total = rs.getDouble(1);
            }
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return total;
    }
    public double SearchFinallyTotal() throws BaseException{
        //查询当前购物顾客购物车订单的实付金额
        double total=0;
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select settlement_price from orders where customer_id like ? and order_state like'未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()) {
                total = rs.getDouble(1);
            }
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return total;
    }
    public void Evaluate(BeanOrder p) throws BaseException{
        //顾客评价功能
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update orders set content=?,evaluate_date=?,star=? where order_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,p.getContent());
            pst.setTimestamp(2,(new Timestamp(System.currentTimeMillis())));
            pst.setInt(3,p.getStar());
            pst.setInt(4,p.getOrder_id());
            pst.execute();
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
    public void Total(List<BuyCart> a) throws BaseException{
        //计算该订单下的实付金额，通过累加循环计算
        double sum=0;
        double reduction=0;
        for(int i=0;i<a.size();i++){
            sum+=a.get(i).getFinallyPrice()*a.get(i).getQuantity();
        }
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update orders set original_price=? where customer_id like ? and order_state like'未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setDouble(1,sum);
            pst.setString(2,CustomerManager.currentLoginCustomer.getCustomer_id());
            pst.execute();
                sql = "create view buycart as  " +
                        "select a.good_id,good_name,type_id,price,member_price,a.finallyprice,quantity,full_reduction_id,b.discount,content  " +
                        "from ((select good.good_id,good_name,type_id,good.price,member_price,orderdetail.price as finallyprice,orderdetail.quantity,orderdetail.discount from good,orderdetail,orders   " +
                        "   where orderdetail.order_id=orders.order_id and orderdetail.good_id=good.good_id and customer_id like ? and order_state like '未支付') as a) LEFT OUTER JOIN   " +
                        "((select good_id,fullandgood.full_reduction_id,discount,content from fullandgood,fullinformation where fullandgood.full_reduction_id  " +
                        "   =fullinformation.full_reduction_id) as b) ON a.good_id= b.good_id";
                pst = conn.prepareStatement(sql);
                pst.setString(1, CustomerManager.currentLoginCustomer.getCustomer_id());
                pst.execute();

            //计算满折优惠金额
            sql="select sum(quantity*finallyprice*(1-discount)) as reduction " +
                    "from buycart " +
                    "where full_reduction_id in( " +
                    "SELECT full_reduction_id  " +
                    "from buycart x  " +
                    "GROUP BY full_reduction_id  " +
                    "HAVING sum(quantity)>=( " +
                    "select suitable_good_quantity  " +
                    "from fullinformation y  " +
                    "where x.full_reduction_id=y.full_reduction_id))" ;
            pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            rs.next();
            reduction=sum-rs.getDouble(1);
            sql="update orders set settlement_price=? where customer_id like ? and order_state like'未支付'";
            pst=conn.prepareStatement(sql);
            pst.setDouble(1,reduction);
            pst.setString(2,CustomerManager.currentLoginCustomer.getCustomer_id());
            pst.execute();
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }


    }
    public void Sure(BeanOrder p ) throws BaseException{
        //确认收货
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);

            String sql="update orders set order_state = '已收货' where order_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,p.getOrder_id());
            pst.execute();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.rollback();
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }
    }
    public List<BuyCart> LoadOrderDetail(int order_id) throws BaseException {
        //加载某个订单的订单详情
        List<BuyCart> result=new ArrayList<BuyCart>();
        double sum=0;
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select a.good_id,good_name,type_id,price,member_price,a.finallyprice,quantity,full_reduction_id,b.discount,content " +
                    " from ((select good.good_id,good_name,type_id,good.price,member_price,orderdetail.price as finallyprice,orderdetail.quantity,orderdetail.discount from good,orderdetail,orders " +
                    " where orderdetail.order_id=orders.order_id and orderdetail.good_id=good.good_id and customer_id like ? and orders.order_id=?) as a) LEFT OUTER JOIN " +
                    "((select good_id,fullandgood.full_reduction_id,discount,content from fullandgood,fullinformation where fullandgood.full_reduction_id " +
                    " =fullinformation.full_reduction_id) as b) ON a.good_id= b.good_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
            pst.setInt(2,order_id);
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                BuyCart r=new BuyCart();
                r.setGood_id(rs.getInt(1));
                r.setGood_name(rs.getString(2));
                r.setGood_type(rs.getInt(3));
                r.setPrice(rs.getDouble(4));
                r.setMemberPrice(rs.getDouble(5));
                r.setFinallyPrice(rs.getDouble(6));
                r.setQuantity(rs.getInt(7));
                r.setFull_reduction_id(rs.getInt(8));
                r.setDiscount(rs.getDouble(9));
                r.setContent(rs.getString(10));
                result.add(r);
            }
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }
    public List<BuyCart> LoadCart() throws BaseException {
        //加载购物车
        List<BuyCart> result=new ArrayList<BuyCart>();
        double sum=0;
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select a.good_id,good_name,type_id,price,member_price,a.finallyprice,quantity,full_reduction_id,b.discount,content " +
                    " from ((select good.good_id,good_name,type_id,good.price,member_price,orderdetail.price as finallyprice,orderdetail.quantity,orderdetail.discount from good,orderdetail,orders " +
                    " where orderdetail.order_id=orders.order_id and orderdetail.good_id=good.good_id and customer_id like ? and order_state like '未支付') as a) LEFT OUTER JOIN " +
                    "((select good_id,fullandgood.full_reduction_id,discount,content from fullandgood,fullinformation where fullandgood.full_reduction_id " +
                    " =fullinformation.full_reduction_id) as b) ON a.good_id= b.good_id";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                BuyCart r=new BuyCart();
                r.setGood_id(rs.getInt(1));
                r.setGood_name(rs.getString(2));
                r.setGood_type(rs.getInt(3));
                r.setPrice(rs.getDouble(4));
                r.setMemberPrice(rs.getDouble(5));
                r.setFinallyPrice(rs.getDouble(6));
                r.setQuantity(rs.getInt(7));
                r.setFull_reduction_id(rs.getInt(8));
                r.setDiscount(rs.getDouble(9));
                r.setContent(rs.getString(10));
                result.add(r);
            }
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return result;
    }
    public void  ModifyOrderSettlement(double finallysum) throws BaseException{
        //将使用优惠券后的实付金额修改到orders表中
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update orders set settlement_price=? where customer_id like ? and order_state like'未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setDouble(1,finallysum);
            pst.setString(2,CustomerManager.currentLoginCustomer.getCustomer_id());
            pst.execute();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.rollback();
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }


    }
    public void  ModifyOrderState(BeanAddress p) throws BaseException{
        //修改订单状态
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
//            String sql="delete from orderdetail where order_id in (select order_id from orders where customer_id like ? and order_state like'未支付' )";
//            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
//            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
//            pst.execute();
            String sql="update orders set address_id=?,order_state=?,receive_time=? where customer_id like ? and order_state like'未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,p.getAddress_id());
            pst.setString(2,"配送中");
            //送到日期默认为一天内
            Date time= new Date();

            Calendar rightNow =Calendar.getInstance();
            rightNow.setTime(time);
            rightNow.add(Calendar.DATE,1);
            pst.setTimestamp(3,(new Timestamp(rightNow.getTime().getTime())));
            pst.setString(4,CustomerManager.currentLoginCustomer.getCustomer_id());
            pst.execute();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.rollback();
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }


    }
    public List<BeanOrder> LoadOrder() throws  BaseException{
        //加载除了未支付的订单
        List<BeanOrder> result=new ArrayList<BeanOrder>();
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select order_id,coupon_id,address_id,original_price,settlement_price,order_state,content,evaluate_date,star " +
                    " from orders where customer_id like ? and order_state not like '未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                BeanOrder o=new BeanOrder();
                o.setOrder_id(rs.getInt(1));
                o.setCoupon_id(rs.getInt(2));
                o.setAddress_id(rs.getInt(3));
                o.setOriginal_price(rs.getDouble(4));
                o.setSettlement_price(rs.getDouble(5));
                o.setOrder_state(rs.getString(6));
                o.setContent(rs.getString(7));
                o.setEvaluate_date(rs.getTimestamp(8));
                o.setStar(rs.getInt(9));
                result.add(o);
            }
            conn.commit();

        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.rollback();
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }
        return  result;
    }
    public  void ReduceFromCart(int good_id,String customer_id,int quantity) throws DbException {
        //从购物车中减少商品
        int order_id=0;
        double TimePromotionPrice=0;
        double price=0;
        double MemberPrice=0;
        int flag=0;
        double NowPrice=0;
        int NowQuantity=0;
        int TimePromotionCount=0;
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            //查看此时的order_id
            String sql="select a.order_id,quantity,price from orders a,orderdetail b where a.order_id=b.order_id" +
                    " and good_id=? and customer_id like ? and  order_state like '未支付'";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.valueOf(good_id));
            pst.setString(2,customer_id);
            java.sql.ResultSet rs= pst.executeQuery();

            if(rs.next()){
                order_id=rs.getInt(1);
                NowQuantity=rs.getInt(2)-quantity;//当前购物车中的该商品数量
                NowPrice=rs.getDouble(3);
                //更新此时购物车中的商品数量
                sql="update orderdetail set quantity=? where order_id=? and good_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,NowQuantity);
                pst.setInt(2,order_id);
                pst.setInt(3,good_id);
                pst.execute();

                sql="select price,end_date,quantity from timepromotion where good_id=? ";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,Integer.valueOf(good_id));
                rs=pst.executeQuery();
                //判断该商品是否参与满折促销
                if(rs.next()) {
                    TimePromotionCount=rs.getInt(3);
                    if (rs.getTimestamp(2).getTime() >= System.currentTimeMillis() && TimePromotionCount >= NowQuantity) {
                        //能够参与限时促销,且数量足够,促销时间未过
                        TimePromotionPrice = rs.getDouble(1);
                        sql = "select price,member_price from good where good_id=?";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, Integer.valueOf(good_id));
                        rs = pst.executeQuery();
                        rs.next();
                        price = rs.getDouble(1);
                        MemberPrice = rs.getDouble(2);
                        //判断此时的顾客是否为会员
                        sql = "select  member,member_finish_date from customer where customer_id like ?";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, customer_id);
                        rs = pst.executeQuery();
                        if (rs.next()) {

                            if (rs.getBoolean(1)) {

                                if (rs.getTimestamp(2).getTime() >= System.currentTimeMillis()) {
                                    //是会员，且会员日期未到期
                                    flag = 1;
                                }
                            }
                            if (flag == 1 && MemberPrice<TimePromotionPrice) {
                                //仅当是会员并且会员价低于促销价时不改变
                            }
                            else {
                                //不是会员或者会员到期，但满足优惠条件也要改变价格，同时减少促销数量
                                sql = "update orderdetail set price=? where good_id=? and order_id=?";
                                pst = conn.prepareStatement(sql);
                                pst.setDouble(1, TimePromotionPrice);
                                pst.setInt(2, good_id);
                                pst.setInt(3, order_id);
                                pst.execute();
                                sql="update timepromotion set quantity=? where good_id=?";
                                pst=conn.prepareStatement(sql);
                                pst.setInt(1,TimePromotionCount-NowQuantity);
                                pst.setInt(2,good_id);
                                pst.execute();
                            }
                        }
                    }
                }
                //如果价格等于促销价格，说明之前就满足促销条件，促销数量也要相应增加
                if(NowPrice==TimePromotionPrice){
                    sql="update timepromotion set quantity=quantity+? where good_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,quantity);
                    pst.setInt(2,good_id);
                    pst.execute();
                }

                //在good表中增加商品数量
                sql="update good  set quantity=quantity+? where good_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,quantity);
                pst.setInt(2,good_id);
                pst.execute();
            }
            //查找此时订单中商品数量，如果为0，删除orderdetail中信息
            sql="select good_id,quantity from orderdetail where order_id=?" ;
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            rs=pst.executeQuery();
            while(rs.next()){
                if(rs.getInt(2)==0){
                    sql="delete from orderdetail where order_id = ? and good_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,order_id);
                    pst.setInt(2,rs.getInt(1));
                    pst.execute();
                }
            }
            //如果该订单下所有商品数量总和为O，删除该订单记录
            sql="select sum(quantity) from orderdetail where order_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,order_id);
            rs=pst.executeQuery();
            if(rs.next()){
                int i=rs.getInt(1);
                if(i==0) {
                    sql = "delete from orderdetail where order_id = ?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, order_id);
                    pst.execute();
                    sql = "delete from orders where order_id = ?";
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1, order_id);
                    pst.execute();
                }
            }
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.rollback();
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }
    }

    public void AddGoodToCart(int good_id,String customer_id,int number,int quantity) throws DbException {
        //添加商品到购物车
        double MemberPrice=0;
        double price=0;
        double TimePromotionPrice=0;
        double FinallyPrice=0;
        int orderId=0;
        int NowQuantity=0;
        int flag1=0;//记录该账号的会员状态，0表示不是会员，1表示是会员
        int flag2=0;//记录该商品是否参与显示促销，如果参与flag2=1

        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            //记录此时商品的原价和会员价
            String sql="select price,member_price from good where good_id=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.valueOf(good_id));
            java.sql.ResultSet  rs=pst.executeQuery();
            rs.next();
            price=rs.getDouble(1);
            MemberPrice=rs.getDouble(2);
            //判断此时的顾客是否为会员
            sql="select  member,member_finish_date from customer where customer_id like ?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,customer_id);
            rs=pst.executeQuery();
            if(rs.next()){
                if(rs.getBoolean(1)){
                    if(rs.getTimestamp(2).getTime()>=System.currentTimeMillis()){
                        //是会员，且会员日期未到期
                        flag1=1;
                    }
                }
            }

            //如果存在购物车并且该购物车中含有该商品，orderdetail表中quantity增加
            sql="select a.order_id,quantity from orders a,orderdetail b where a.order_id=b.order_id" +
                    " and good_id=? and customer_id like ? and  order_state like '未支付'";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,Integer.valueOf(good_id));
            pst.setString(2,customer_id);
            rs= pst.executeQuery();
            if(rs.next()){
                orderId=rs.getInt(1);
                NowQuantity=rs.getInt(2)+quantity;
                sql="update orderdetail set quantity=quantity+? where order_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,quantity);
                pst.setInt(2,rs.getInt(1));
                pst.execute();
                //判断商品是否参与限时促销
                sql="select price,end_date,quantity from timepromotion where good_id=? ";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,Integer.valueOf(good_id));
                rs=pst.executeQuery();
                if(rs.next()){
                    if(rs.getTimestamp(2).getTime()>=System.currentTimeMillis()&&rs.getInt(3)>=NowQuantity){
                        //参与限时促销,且数量足够
                        flag2=1;
                        TimePromotionPrice=rs.getDouble(1);
                    }
                }
                //会员价和促销价同时存在
                if((flag1==1)&&(flag2==1)){
                    if(MemberPrice>TimePromotionPrice){//促销价小于会员价
                        FinallyPrice=TimePromotionPrice;
                        sql="update timepromotion set quantity=quantity-? where good_id=?";
                        pst=conn.prepareStatement(sql);
                        pst.setInt(1,quantity);
                        pst.setInt(2,good_id);
                        pst.execute();
                    }
                    else {
                        FinallyPrice=MemberPrice;
                    }
                }
                else if (flag1==1){
                    FinallyPrice=MemberPrice;
                }
                else if (flag2==1){
                    FinallyPrice=TimePromotionPrice;
                    sql="update timepromotion set quantity=quantity-? where good_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,quantity);
                    pst.setInt(2,good_id);
                    pst.execute();
                }
                else {
                    FinallyPrice=price;
                }
                //更新实付金额
                sql="update orderdetail set price=? where order_id=?";
                pst=conn.prepareStatement(sql);
                pst.setDouble(1,FinallyPrice);
                pst.setInt(2,orderId);
                pst.execute();
            }
            else{
                //判断商品是否参与限时促销
                sql="select price,end_date,quantity from timepromotion where good_id=? ";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,Integer.valueOf(good_id));
                rs=pst.executeQuery();
                if(rs.next()){
                    if(rs.getTimestamp(2).getTime()>=System.currentTimeMillis()&&rs.getInt(3)>=quantity){
                        //参与限时促销,且数量足够
                        flag2=1;
                        TimePromotionPrice=rs.getDouble(1);
                    }
                }
                if((flag1==1)&&(flag2==1)){
                    if(MemberPrice>TimePromotionPrice){//促销价小于会员价
                        FinallyPrice=TimePromotionPrice;
                        sql="update timepromotion set quantity=quantity-? where good_id=?";
                        pst=conn.prepareStatement(sql);
                        pst.setInt(1,quantity);
                        pst.setInt(2,good_id);
                        pst.execute();
                    }
                    else {
                        FinallyPrice=MemberPrice;
                    }
                }
                else if (flag1==1){
                    FinallyPrice=MemberPrice;
                }
                else if (flag2==1){
                    FinallyPrice=TimePromotionPrice;
                    sql="update timepromotion set quantity=quantity-? where good_id=?";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,quantity);
                    pst.setInt(2,good_id);
                    pst.execute();
                }
                else {
                    FinallyPrice=price;
                }

                //查看是否存在购物车
                sql="select order_id from orders where  customer_id like ? and  order_state like '未支付'";
                pst=conn.prepareStatement(sql);
                pst.setString(1,customer_id);
                rs= pst.executeQuery();
                if(rs.next()){//说明购物车订单已经创建
                    sql="insert into orderdetail(order_id,good_id,quantity,price)" +
                            " values(?,?,?,?) ";
                    pst=conn.prepareStatement(sql);
                    pst.setInt(1,rs.getInt(1));
                    pst.setInt(2,Integer.valueOf(good_id));
                    pst.setInt(3,quantity);
                    pst.setDouble(4,FinallyPrice);
                    pst.execute();
                }
                else{//不存在购物车即不存在订单
                    //创建一个订单
                    sql = "insert into orders(customer_id,order_state)" +
                            " values(?,?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, customer_id);
                    pst.setString(2, "未支付");
                    pst.execute();
                    //记录当前订单ID
                    sql="select order_id from orders where  customer_id like ? and  order_state like '未支付'";
                    pst=conn.prepareStatement(sql);
                    pst.setString(1,customer_id);
                    rs= pst.executeQuery();
                    //插入
                    if(rs.next()) {
                        sql = "insert into orderdetail(order_id,good_id,quantity,price)" +
                                " values(?,?,?,?) ";
                        pst = conn.prepareStatement(sql);
                        pst.setInt(1, rs.getInt(1));
                        pst.setInt(2, Integer.valueOf(good_id));
                        pst.setInt(3, quantity);
                        pst.setDouble(4, FinallyPrice);
                        pst.execute();
                    }
                }
            }
            //不管是否参与会员或者促销，原商品中的数量必修减少
            sql="update good  set quantity=quantity-? where good_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,quantity);
            pst.setInt(2,good_id);
            pst.execute();
            conn.commit();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.rollback();
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }
    }
    public String searchOrderState(BeanOrder o) throws BaseException {
        //用于只有先确认收货才能评价
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select order_state from orders where order_id=? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,o.getOrder_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next())
            return rs.getString(1);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        }
        finally{
            if(conn!=null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }
}
