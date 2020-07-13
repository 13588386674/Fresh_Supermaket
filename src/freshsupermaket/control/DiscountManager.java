package freshsupermaket.control;

import freshsupermaket.model.*;
import freshsupermaket.util.BaseException;
import freshsupermaket.util.DButil;
import freshsupermaket.util.DbException;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountManager {
    public void CreateCoupon(BeanCoupon c) throws BaseException{
        //创建优惠券信息
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);

            String sql="insert into coupon(content,suitable_amount,credit_amount,start_date," +
                    "end_date) values(?,?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,c.getContent());
            pst.setDouble(2,c.getSuitable_amount());
            pst.setDouble(3,c.getCredit_amount());
            pst.setTimestamp(4,new java.sql.Timestamp(c.getStart_date().getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(c.getEnd_date().getTime()));
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

    public void CreateFullInformation(BeanFullInformation c)throws BaseException{
        //创建满减信息
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);

            String sql="insert into fullinformation(content,suitable_good_quantity,discount,start_date" +
                    ",end_date)  values (?,?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,c.getContent());
            pst.setInt(2,c.getSuitable_good_quantity());
            pst.setDouble(3,c.getDiscount());
            pst.setTimestamp(4,new java.sql.Timestamp(c.getStart_date().getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(c.getEnd_date().getTime()));
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

    public void CreateFullAndGood(BeanFullAndGood c)throws BaseException{
        //创建商品满减信息
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);

            String sql="insert into fullandgood(good_id,full_reduction_id,start_date,end_date) " +
                    "values(?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,c.getGood_id());
            pst.setInt(2,c.getFull_reduction_id());
            pst.setTimestamp(3,new java.sql.Timestamp(c.getStart_date().getTime()));
            pst.setTimestamp(4,new java.sql.Timestamp(c.getEnd_date().getTime()));
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

    public void CreateTimePromotion(BeanTimePromotion c)throws BaseException{
        //创建显示促销信息
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into timepromotion(good_id,price,quantity,start_date,end_date) " +
                    "values(?,?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,c.getGood_id());
            pst.setDouble(2,c.getPrice());
            pst.setInt(3,c.getQuantity());
            pst.setTimestamp(4,new java.sql.Timestamp(c.getStart_date().getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(c.getEnd_date().getTime()));
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

    public void DeleteCoupon(int id)throws BaseException{
        //删除优惠劵信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from coupon where coupon_id =? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
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

    public void DeleteFullAndInformation(int id)throws BaseException{
        //删除满减信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from fullinformation where full_reduction_id =? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
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

    public void DeleteFullAndGood(int good_id,int full_reduction_id)throws BaseException{
        //删除商品满减信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from fullandgood where good_id=? and  full_reduction_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,good_id);
            pst.setInt(2,full_reduction_id);
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

    public void DeleteTimePromotion(int id) throws BaseException{
        //删除限时促销信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from timepromotion where promotion_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
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
    public void ModifyCoupon(BeanCoupon c)throws BaseException{
        //修改优惠券信息
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);

            String sql="update coupon set content=?,suitable_amount=?,credit_amount=?," +
                    "start_date=?,end_date=? " +
                    " where coupon_id like ?" ;
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,c.getContent());
            pst.setDouble(2,c.getSuitable_amount());
            pst.setDouble(3,c.getCredit_amount());
            pst.setTimestamp(4,new java.sql.Timestamp(c.getStart_date().getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(c.getEnd_date().getTime()));
            pst.setInt(6,c.getCoupon_id());
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
    public void ModifyFullInformation(BeanFullInformation c)throws BaseException{
        //修改满减信息
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);

            String sql="update fullinformation set content=?,suitable_good_quantity=?,discount=?" +
                    ",start_date=?,end_date=? " +
                    "where full_reduction_id=? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,c.getContent());
            pst.setInt(2,c.getSuitable_good_quantity());
            pst.setDouble(3,c.getDiscount());
            pst.setTimestamp(4,new java.sql.Timestamp(c.getStart_date().getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(c.getEnd_date().getTime()));
            pst.setInt(6,c.getFull_reduction_id());
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

    public void ModifyFullAndGood(BeanFullAndGood fag,BeanFullAndGood fag1) throws BaseException{
        //修改商品满折表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update fullandgood set  start_date=?," +
                    "end_date=?" +
                    " where good_id=? and  full_reduction_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setTimestamp(1,new java.sql.Timestamp(fag1.getStart_date().getTime()));
            pst.setTimestamp(2,new java.sql.Timestamp(fag1.getEnd_date().getTime()));
            pst.setInt(3,fag.getGood_id());
            pst.setInt(4,fag.getFull_reduction_id());
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
    public void ModifyTimePromotion(BeanTimePromotion c) throws BaseException{
        //修改限时促销信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update timepromotion set good_id=?,price=?,quantity=?,start_date=?,end_date=? where promotion_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,c.getGood_id());
            pst.setDouble(2,c.getPrice());
            pst.setInt(3,c.getQuantity());
            pst.setTimestamp(4,new java.sql.Timestamp(c.getStart_date().getTime()));
            pst.setTimestamp(5,new java.sql.Timestamp(c.getEnd_date().getTime()));
            pst.setInt(6,c.getPromotion_id());
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

    public List<BeanCoupon> LoadCoupon()throws BaseException{
        //加载所有优惠券信息
        List<BeanCoupon> result=new ArrayList<BeanCoupon>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from coupon order by coupon_id";
            java.sql.Statement st=conn.createStatement();
            java.sql.ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                BeanCoupon rt=new BeanCoupon();
                rt.setCoupon_id(rs.getInt(1));
                rt.setContent(rs.getString(2));
                rt.setSuitable_amount(rs.getDouble(3));
                rt.setCredit_amount(rs.getDouble(4));
                rt.setStart_date(rs.getTimestamp(5));
                rt.setEnd_date(rs.getTimestamp(6));
                result.add(rt);
            }
            conn.commit();
        } catch (SQLException e) {
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

    public List<BeanFullInformation> LoadFullInformation()throws BaseException{
        //加载所有满减信息
        List<BeanFullInformation> result=new ArrayList<BeanFullInformation>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from fullinformation order by full_reduction_id";
            java.sql.Statement st=conn.createStatement();
            java.sql.ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                BeanFullInformation rt=new BeanFullInformation();
                rt.setFull_reduction_id(rs.getInt(1));
                rt.setContent(rs.getString(2));
                rt.setSuitable_good_quantity(rs.getInt(3));
                rt.setDiscount(rs.getDouble(4));
                rt.setStart_date(rs.getTimestamp(5));
                rt.setEnd_date(rs.getTimestamp(6));
                result.add(rt);
            }
            conn.commit();
        } catch (SQLException e) {
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
    public List<BeanFullAndGood> SearchFullAndGood(String id)throws BaseException{
        //模糊查询商品ID或者满减ID和关键字匹配的商品满折信息
        List<BeanFullAndGood> result=new ArrayList<BeanFullAndGood>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from fullandgood  ";
            if (id!=null&&!"".equals(id))
                sql+="where good_id like ? or full_reduction_id like ?";
            sql+="order by full_reduction_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            if(id!=null&&!"".equals(id)){
                pst.setString(1,"%"+id+"%");
                pst.setString(2,"%"+id+"%");
            }
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                BeanFullAndGood c=new BeanFullAndGood();
                c.setGood_id(rs.getInt(1));
                c.setFull_reduction_id(rs.getInt(2));
                c.setStart_date(rs.getTimestamp(3));
                c.setEnd_date(rs.getTimestamp(4));
                result.add(c);

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
    public List<BeanTimePromotion> SearchTimePromotion(String id) throws BaseException{
        //模糊查询商品ID或者促销ID和关键字匹配的促销信息
        List<BeanTimePromotion> result=new ArrayList<BeanTimePromotion>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from timepromotion  ";
            if (id!=null&&!"".equals(id))
                sql+="where good_id like ? or promotion_id like ?";
            sql+="order by promotion_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            if(id!=null&&!"".equals(id)){
                pst.setString(1,"%"+id+"%");
                pst.setString(2,"%"+id+"%");
            }
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                BeanTimePromotion c=new BeanTimePromotion();
                c.setPromotion_id(rs.getInt(1));
                c.setGood_id(rs.getInt(2));
                c.setPrice(rs.getDouble(3));
                c.setQuantity(rs.getInt(4));
                c.setStart_date(rs.getTimestamp(5));
                c.setEnd_date(rs.getTimestamp(6));
                result.add(c);

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


}
