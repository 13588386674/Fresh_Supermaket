package freshsupermaket.control;

import freshsupermaket.util.DButil;
import freshsupermaket.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;

public class CheckForeign {
    public Boolean CheckRecipeForeign(int recipeId) throws DbException {
        //检查商品类别是否被使用
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from goodandrecipe where recipe_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,recipeId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
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
        return true;
    }

    public Boolean CheckAddressForeign(int AddressId) throws DbException {
        //检查地址是否被使用
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from orders where address_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,AddressId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckCouponForeign(int CouponId) throws DbException {
        //检查优惠券是否被使用
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            String sql="select * from orders where coupon_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,CouponId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckFullInformationForeign(int full_reduction_Id) throws DbException {
        //检查满折编号是否被使用
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from fullandgood where full_reduction_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,full_reduction_Id);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckGoodTypeForeign(int goodtype_id) throws DbException {
        //检查商品类别ID是否被使用
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from good where type_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodtype_id);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckCustomerForeign(String customerId) throws DbException {
        //检查顾客ID是否被使用
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from address where customer_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,customerId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
            sql="select * from orders where customer_id=?";
            pst=conn.prepareStatement(sql);
            pst.setString(1,customerId);
            rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckGoodForeign(int goodId) throws DbException {
        //检查商品ID是否被使用
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from fullandgood where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
            sql="select * from  goodandrecipe where  good_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
            sql="select * from goodpurchase where  good_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
            sql="select * from orderdetail   where  good_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
            sql="select * from timepromotion where  good_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckFullAndGoodForeign(int goodId,int full_reduction_id) throws DbException {
        //检查商品ID和满折ID是否存在用于判断添加商品满折表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from good where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                return false;
            }
            sql="select * from   fullinformation where full_reduction_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,full_reduction_id);
            rs=pst.executeQuery();
            if(rs.next()){

            }
            else{
                return false;
            }
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
        return true;
    }
    public Boolean CheckGoodAndRecipeForeign(int goodId,int recipe_id) throws DbException {

        //检查商品ID和食谱ID是否存在，用于添加商品食谱表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from good where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                return false;
            }
            sql="select * from   recipe where recipe_id=?";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,recipe_id);
            rs=pst.executeQuery();
            if(!rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckGoodAndPurchaseForeign(int goodId) throws DbException {
        //检查商品ID是否存在，用于添加采购表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from good where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckGoodAndTypeForeign(int typeid) throws DbException {
        //检查商品类别ID是否存在，用于添加商品
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from goodtype where type_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,typeid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckTimePromotionForeign(int goodId) throws DbException {
        //检查商品ID是否存在，用于添加限时促销表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from good where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                return false;
            }
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
        return true;
    }
    public Boolean CheckGoodPurchaseForeign(int goodId) throws DbException {
        //检查商品ID是否存在，用于商品购买步骤
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from good where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodId);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()){
                return false;
            }
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
        return true;
    }


}
