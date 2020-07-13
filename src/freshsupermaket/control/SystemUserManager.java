package freshsupermaket.control;

import freshsupermaket.model.BeanGoodPurchase;
import freshsupermaket.model.BeanSystemUser;
import freshsupermaket.util.BaseException;
import freshsupermaket.util.BusinessException;
import freshsupermaket.util.DButil;
import freshsupermaket.util.DbException;
import org.hibernate.loader.custom.Return;

import javax.print.DocFlavor;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SystemUserManager {
    public static BeanSystemUser currentLoginUser = null;

    public void CreateGoodPurchase(BeanGoodPurchase gp) throws BaseException {
        //创建商品采购表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into goodpurchase(purchase_id,good_id,quantity,state)" +
                    " values(?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,gp.getPurchase_id());
            pst.setInt(2,gp.getGood_id());
            pst.setInt(3,gp.getQuantity());
            pst.setString(4,gp.getState());
            pst.execute();
            if(gp.getState().equals("入库")){
                sql="update good set quantity=quantity+? where good_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,gp.getQuantity());
                pst.setInt(2,gp.getGood_id());
                pst.execute();
            }
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void CreateSystemUser(BeanSystemUser su) throws BaseException {
        //创建系统管理员
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into systemuser(system_user_id,system_user_name,password)" +
                    " values(?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,su.getSystem_user_id());
            pst.setString(2,su.getSystem_user_name());
            pst.setString(3,su.getPassword());
            pst.execute();
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void DeleteGoodPurchase(BeanGoodPurchase gp) throws BaseException {

    }

    public void DeleteSystemUser(String system_user_id) throws BaseException {
        //删除系统管理员
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete  from systemuser where system_user_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,system_user_id);
            pst.execute();
            conn.commit();
        }catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void ModifyGoodPurchase(BeanGoodPurchase gp) throws BaseException {
        //修改商品采购表状态，如果执行入库操作，相对应的商品数量增加
        Connection conn = null;
        try {
            conn = DButil.getConnection();
            conn.setAutoCommit(false);
            String sql = "update goodpurchase set state=?" +
                    "where purchase_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,gp.getState());
            pst.setInt(2,gp.getPurchase_id());
            pst.execute();
            if(gp.getState().equals("入库")){
                sql="update good set quantity=quantity+? where good_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,gp.getQuantity());
                pst.setInt(2,gp.getGood_id());
                pst.execute();
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public void ModifySystemUser(BeanSystemUser c) throws BaseException {
        //修改系统管理员
        Connection conn = null;
        try {
            conn = DButil.getConnection();
            conn.setAutoCommit(false);
            String sql = "update systemuser set system_user_name=?,password=?" +
                    "where system_user_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, c.getSystem_user_name());
            pst.setString(2, c.getPassword());
            pst.setString(3, c.getSystem_user_id());
            pst.execute();
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
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

    public List<BeanGoodPurchase> SearchGoodPurchase(String id,String state) throws BaseException {
        //加载商品订单状态为导入参数state
        List<BeanGoodPurchase> result=new ArrayList<BeanGoodPurchase>();
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from goodpurchase where state='"+state+"'";
            if(id!=null&&!"".equals(id)){
                sql+=" and (purchase_id like ? or good_id like ?) ";
            }
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            if(id!=null&&!"".equals(id)){
                pst.setString(1,"%"+id+"%");
                pst.setString(2,"%"+id+"%");
            }
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                BeanGoodPurchase gp= new BeanGoodPurchase();
                gp.setPurchase_id(rs.getInt(1));
                gp.setGood_id(rs.getInt(2));
                gp.setQuantity(rs.getInt(3));
                gp.setState(rs.getString(4));
                result.add(gp);
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

    public List<BeanSystemUser> LoadAllSystemUser() throws BaseException {
        //加载所有系统管理员
        Connection conn = null;
        List<BeanSystemUser> result = new ArrayList<BeanSystemUser>();
        try {
            conn = DButil.getConnection();
            conn.setAutoCommit(false);
            String sql = "select * from systemuser ";
            java.sql.Statement st = conn.createStatement();
            java.sql.ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                BeanSystemUser c = new BeanSystemUser();
                c.setSystem_user_id(rs.getString(1));
                c.setSystem_user_name(rs.getString(2));
                c.setPassword(rs.getString(3));
                result.add(c);
            }
            conn.commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.rollback();
                    conn.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }




    public BeanSystemUser loadUser(String userid)throws BaseException{
        //登录时判断用户是否存在
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from systemuser where system_user_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,userid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()) throw new BusinessException("登录账号不存在");
            BeanSystemUser u=new BeanSystemUser();
            u.setSystem_user_id(rs.getString(1));
            u.setSystem_user_name(rs.getString(2));
            u.setPassword(rs.getString(3));
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
    public BeanSystemUser reg(String userid,String name,String pwd,String pwd2) throws BaseException{
        //注册时判断用户是否存在，密码是否相同
        BeanSystemUser bu= new BeanSystemUser();
        if(userid==null||"".equals(userid)||userid.length()>50){
            throw new BusinessException("注册账号必须1-50个字");
        }
        if(pwd.equals(pwd2)==false){
            throw new BusinessException("两次密码必须相同");
        }
        if(pwd==null||"".equals(pwd)||pwd.length()>20){
            throw new BusinessException("密码必须1-20个字");
        }
        Connection conn=null;
        try{
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from systemuser where system_user_id=?" ;
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, userid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()) throw new BusinessException("注册账号已经存在");
            sql="insert into systemuser(system_user_id,system_user_name,password) values(?,?,?)" ;
            pst=conn.prepareStatement(sql);
            pst.setString(1, userid);
            pst.setString(2,name);
            pst.setString(3, pwd);
            bu.setSystem_user_id(userid);
            bu.setSystem_user_name(name);
            bu.setPassword(pwd);
            pst.execute();
            pst.close();
            conn.commit();
            return bu;
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

}