package freshsupermaket.control;

import freshsupermaket.model.*;
import freshsupermaket.util.BaseException;
import freshsupermaket.util.BusinessException;
import freshsupermaket.util.DButil;
import freshsupermaket.util.DbException;

import sun.reflect.generics.tree.VoidDescriptor;

import javax.smartcardio.CommandAPDU;
import javax.tools.JavaCompiler;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerManager {
    public static BeanCustomer currentLoginCustomer=null;//记录登录顾客信息
    public BeanCustomer LoadNowCustomer() throws BaseException{
        //显示当前登录的顾客信息
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from customer where customer_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,CustomerManager.currentLoginCustomer.getCustomer_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()) throw new BusinessException("登录账号不存在");
            BeanCustomer u=new BeanCustomer();
            u.setCustomer_id(rs.getString(1));
            u.setCustomer_name(rs.getString(2));
            u.setCustomer_sex(rs.getString(3));
            u.setPassword(rs.getString(4));
            u.setCustomer_number(rs.getString(5));
            u.setEmail(rs.getString(6));
            u.setCity(rs.getString(7));
            u.setCreate_date(rs.getTimestamp(8));
            u.setMember(rs.getBoolean(10));
            u.setFinish_date(rs.getTimestamp(9));
            rs.close();
            pst.close();
            conn.commit();
            return u;

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
    public BeanCustomer LoadCustomer(String userid) throws BaseException{
        //查找账号是否存在
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from customer where customer_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,userid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()) throw new BusinessException("登录账号不存在");
            BeanCustomer u=new BeanCustomer();
            u.setCustomer_id(rs.getString(1));
            u.setCustomer_name(rs.getString(2));
            u.setPassword(rs.getString(4));
            u.setMember(rs.getBoolean(10));
            u.setFinish_date(rs.getTimestamp(9));
            rs.close();
            pst.close();
            conn.commit();
            return u;

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
    public void CreateCustomer(BeanCustomer c) throws BaseException{
        //创建顾客
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from customer where customer_id like ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,c.getCustomer_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()) throw new BaseException("注册顾客已存在");
            sql="insert into customer(customer_id,customer_name,customer_sex,password," +
                    "customer_number,email,city,create_date,member_finish_date,member)" +
                    "values(?,?,?,?,?,?,?,?,?,?)";

            pst=conn.prepareStatement(sql);
            pst.setString(1,c.getCustomer_id());
            pst.setString(2,c.getCustomer_name());
            pst.setString(3,c.getCustomer_sex());
            pst.setString(4,c.getPassword());
            pst.setString(5,c.getCustomer_number());
            pst.setString(6,c.getEmail());
            pst.setString(7,c.getCity());
            pst.setTimestamp(8,new java.sql.Timestamp(c.getCreate_date().getTime()));
            if(c.isMember()) {
                pst.setTimestamp(9, new java.sql.Timestamp(c.getFinish_date().getTime()));
            }
            else{
                pst.setTimestamp(9,null);
            }
            pst.setBoolean(10,c.isMember());

            pst.execute();
        }catch(SQLException e){
            e.printStackTrace();
            throw new DbException(e);
        }
        finally {
            if(conn!=null)
                try{
                    conn.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
        }


    }

    public void CreateAddress(BeanAddress a) throws BaseException{
        //创建顾客地址
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            //address_id在mysql中设置为自动递增不可能重复
            conn.setAutoCommit(false);
            String sql="insert into address(customer_id,province,city,area,address" +
                    ",connect_person,connect_number)" +
                    "values(?,?,?,?,?,?,?)";
            java.sql.PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1,a.getCustomer_id());
            pst.setString(2,a.getProvince());
            pst.setString(3,a.getCity());
            pst.setString(4,a.getArea());
            pst.setString(5,a.getAddress());
            pst.setString(6,a.getConnect_person());
            pst.setString(7,a.getConnect_number());
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

    public void CreateGoodOrderAndOrderDetail(int customer_id,int coupon_id,int address_id,int good_id,int full_reduction_id){
        /*该方法创建两个表，一个是商品订单表，一个是订单详情表*/
    }
    public void DeleteCustomer(String customer_id) throws BaseException{
        //删除顾客
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from customer where customer_id like ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,customer_id);
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

    public void DeleteAddress(int address_id) throws BaseException{
        //删除地址
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from address where address_id =? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,address_id);
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



    public void DeleteGoodOrderAndOrderDetail(BeanOrder o) throws BaseException{

    }

    public void ModifyCustomer(BeanCustomer c)throws BaseException{
        //修改顾客
        Connection conn=null;
        try {

            conn=DButil.getConnection();
            conn.setAutoCommit(false);

            String sql="update customer set customer_name=?,customer_sex=?,password=?," +
                    "customer_number=?,email=?,city=?,member=?,member_finish_date=?" +
                    " where customer_id like ?" ;
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,c.getCustomer_name());
            pst.setString(2,c.getCustomer_sex());
            pst.setString(3,c.getPassword());
            pst.setString(4,c.getCustomer_number());
            pst.setString(5,c.getEmail());
            pst.setString(6,c.getCity());
            pst.setBoolean(7,c.isMember());
            if(c.isMember()) {
                pst.setTimestamp(8, new java.sql.Timestamp(c.getFinish_date().getTime()));
            }
            else{
                pst.setTimestamp(8,null);
            }
//            pst.setTimestamp(8,new java.sql.Timestamp(c.getFinish_date().getTime()));
//            pst.setTimestamp(7,new java.sql.Timestamp(c.getFinish_date().getTime()));
            pst.setString(9,c.getCustomer_id());
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

    public void ModifyAddress(BeanAddress a) throws BaseException{
        //修改地址
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update address set customer_id=?,province=?,city=?,area=?,address=?" +
                    " ,connect_person=?,connect_number=?" +
                    " where address_id = ?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,a.getCustomer_id());
            pst.setString(2,a.getProvince());
            pst.setString(3,a.getCity());
            pst.setString(4,a.getArea());
            pst.setString(5,a.getAddress());
            pst.setString(6,a.getConnect_person());
            pst.setString(7,a.getConnect_number());
            pst.setInt(8,a.getAddress_id());
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

    public void ModifyOrder(BeanOrder o)throws BaseException{

    }

    public void ModifyOrderDetail(BeanOrderDetail od) throws BaseException{

    }
    public List<BeanCustomer> LoadAllCustomer(boolean withDeleteUser)throws BaseException{
        return null;
    }
    public List<BeanCustomer> SearchCustomer(String CustomerIdOrCustomerName)throws BaseException{
        //模糊查找顾客ID或者顾客姓名中包含关键字的顾客信息
        List<BeanCustomer> result=new ArrayList<>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from customer  ";
            if (CustomerIdOrCustomerName!=null&&!"".equals(CustomerIdOrCustomerName))
                sql+="where customer_id like ? or customer_name like ?";
            sql+="order by customer_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            if(CustomerIdOrCustomerName!=null&&!"".equals(CustomerIdOrCustomerName)){
                pst.setString(1,"%"+CustomerIdOrCustomerName+"%");
                pst.setString(2,"%"+CustomerIdOrCustomerName+"%");
            }
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                BeanCustomer c=new BeanCustomer();
                c.setCustomer_id(rs.getString(1));
                c.setCustomer_name(rs.getString(2));
                c.setCustomer_sex(rs.getString(3));
                c.setPassword(rs.getString(4));
                c.setCustomer_number(rs.getString(5));
                c.setEmail(rs.getString(5));
                c.setCity(rs.getString(7));
                c.setCreate_date(rs.getTimestamp(8));
                c.setFinish_date(rs.getTimestamp(9));
                c.setMember(rs.getBoolean(10));
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

    public List<BeanAddress> SearchAddress(String CustomerIdOrCustomerName)throws BaseException{
        //模糊查找Address表中顾客名与关键字相近的地址信息
        List<BeanAddress> result=new ArrayList<>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from address  ";
            if (CustomerIdOrCustomerName!=null&&!"".equals(CustomerIdOrCustomerName))
                sql+="where customer_id like ? ";
            sql+="order by customer_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            if(CustomerIdOrCustomerName!=null&&!"".equals(CustomerIdOrCustomerName)){
                pst.setString(1,"%"+CustomerIdOrCustomerName+"%");
            }

            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                BeanAddress a= new BeanAddress();
                a.setAddress_id(rs.getInt(1));
                a.setCustomer_id(rs.getString(2));
                a.setProvince(rs.getString(3));
                a.setCity(rs.getString(4));
                a.setArea(rs.getString(5));
                a.setAddress(rs.getString(6));
                a.setConnect_person(rs.getString(7));
                a.setConnect_number(rs.getString(8));
                result.add(a);
                conn.commit();

            }
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
    public List<BeanAddress> SearchAddressC(String customer_id)throws BaseException{
        //精确查找顾客ID与关键字相匹配的地址
        List<BeanAddress> result=new ArrayList<>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from address where customer_id like? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,customer_id);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                BeanAddress a= new BeanAddress();
                a.setAddress_id(rs.getInt(1));
                a.setCustomer_id(rs.getString(2));
                a.setProvince(rs.getString(3));
                a.setCity(rs.getString(4));
                a.setArea(rs.getString(5));
                a.setAddress(rs.getString(6));
                a.setConnect_person(rs.getString(7));
                a.setConnect_number(rs.getString(8));
                result.add(a);
                conn.commit();

            }
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
    public List<BeanOrder> LoadOrder()throws BaseException{
        return null;
    }

    public List<BeanOrderDetail> LoadOrderDetail(BeanOrder o) throws BaseException{
        return null;
    }

}
