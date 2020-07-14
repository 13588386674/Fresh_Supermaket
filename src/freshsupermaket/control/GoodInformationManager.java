package freshsupermaket.control;

import freshsupermaket.model.*;
import freshsupermaket.util.BaseException;
import freshsupermaket.util.DButil;
import freshsupermaket.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GoodInformationManager {
    public void  CreateGoodType(BeanGoodType gt) throws BaseException{
        //创建商品类别
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into goodtype(type_name,detail)" +
                    "values(?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,gt.getType_name());
            pst.setString(2,gt.getDetail());
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

    public void CreateGood(BeanGood g) throws  BaseException{
        //创建商品
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into good(type_id,good_name,price,member_price,quantity," +
                    "specification,detail)" +
                    "values(?,?,?,?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,g.getType_id());
            pst.setString(2,g.getGood_name());
            pst.setDouble(3,g.getPrice());
            pst.setDouble(4,g.getMember_price());
            pst.setInt(5,g.getQuantity());
            pst.setString(6,g.getSpecification());
            pst.setString(7,g.getDetail());
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
    public boolean SearchGood(int id) throws BaseException{
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            String sql="select * from timepromotion where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
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
    public void CreateRecipe(BeanRecipe r) throws  BaseException{
        //创建菜谱
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="insert into recipe(recipe_name,recipe_material,steps,detail)" +
                    "values(?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,r.getRecipe_name());
            pst.setString(2,r.getRecipe_material());
            pst.setString(3,r.getSteps());
            pst.setString(4,r.getDetail());
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

    public boolean CreateGoodAndRecipe(BeanGoodAndRecipe gap)throws BaseException{
        //创建商品菜谱表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from goodandrecipe where good_id=? and recipe_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,gap.getGood_id());
            pst.setInt(2,gap.getRecipe_id());
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return false;
            }
            sql="insert into goodandrecipe(good_id,recipe_id,content)" +
                    "values(?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,gap.getGood_id());
            pst.setInt(2,gap.getRecipe_id());
            pst.setString(3,gap.getContent());
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
        return  true;
    }

    public void DeleteGoodType(int goodtype_id) throws BaseException{
        //删除商品类别信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from goodtype where type_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,goodtype_id);
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

    public void DeleteGood(int good_id) throws  BaseException{
        //删除商品信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from good where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,good_id);
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
    public void DeleteRecipe(int recipe_id) throws  BaseException{
        //删除菜谱信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from recipe where recipe_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,recipe_id);
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

    public void DeleteGoodAndRecipe(int good_id,int recipe_id)throws BaseException{
        //删除商品菜谱信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="delete from goodandrecipe where good_id=? and  recipe_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,good_id);
            pst.setInt(2,recipe_id);
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


    public void ModifyGoodType(BeanGoodType gt) throws BaseException{
        //修改商品类别
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update goodtype set type_name=?,detail=? where type_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,gt.getType_name());
            pst.setString(2,gt.getDetail());
            pst.setInt(3,gt.getType_id());
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

    public void ModifyGood(BeanGood g) throws BaseException{
        //修改商品信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update good set type_id=?,good_name=?,price=?,member_price=?" +
                    ",quantity=?,specification=?,detail=?" +
                    " where good_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,g.getType_id());
            pst.setString(2,g.getGood_name());
            pst.setDouble(3,g.getPrice());
            pst.setDouble(4,g.getMember_price());
            pst.setInt(5,g.getQuantity());
            pst.setString(6,g.getSpecification());
            pst.setString(7,g.getDetail());
            pst.setInt(8,g.getGood_id());
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

    public void ModifyRecipe(BeanRecipe r) throws BaseException{
        //修改食谱信息
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update recipe set recipe_name=?,recipe_material=?," +
                    "steps=?,detail=? where recipe_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,r.getRecipe_name());
            pst.setString(2,r.getRecipe_material());
            pst.setString(3,r.getSteps());
            pst.setString(4,r.getDetail());
            pst.setInt(5,r.getRecipe_id());
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

    public void ModifyGoodAndRecipe(BeanGoodAndRecipe gad,BeanGoodAndRecipe gad1)throws BaseException{
        //修改商品食谱信息表
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="update goodandrecipe set  content=?" +
                    " where good_id=? and  recipe_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,gad1.getContent());
            pst.setInt(2,gad.getGood_id());
            pst.setInt(3,gad.getRecipe_id());
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

    public List<BeanGoodType> LoadAllGoodType() throws BaseException{
        //加载所有的商品类型
        List<BeanGoodType> result=new ArrayList<BeanGoodType>();
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select type_id,type_name,detail from goodtype order by type_id";
            java.sql.Statement st=conn.createStatement();
            java.sql.ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String detail=rs.getString(3);
                BeanGoodType rt=new BeanGoodType();
                rt.setType_id(id);
                rt.setType_name(name);
                rt.setDetail(detail);
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

    public List<BeanGood> LoadGood() throws BaseException{

        return  null;
    }

    public List<BeanRecipe> LoadRecipe()throws BaseException{
        //加载所有的菜谱信息
        List<BeanRecipe> result=new ArrayList<BeanRecipe>();
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from recipe";
            java.sql.Statement st=conn.createStatement();
            java.sql.ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                BeanRecipe rt=new BeanRecipe();
                rt.setRecipe_id(rs.getInt(1));
                rt.setRecipe_name(rs.getString(2));
                rt.setRecipe_material(rs.getString(3));
                rt.setSteps(rs.getString(4));
                rt.setDetail(rs.getString(5));
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

    public List<BeanGoodAndRecipe> SearchGoodAndRecipe(String id)throws BaseException{
        //加载所有的商品食谱信息
        List<BeanGoodAndRecipe> result=new ArrayList<BeanGoodAndRecipe>();
        Connection conn=null;
        try {
            conn= DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from goodandrecipe  ";
            if (id!=null&&!"".equals(id))
                sql+="where good_id like ? or recipe_id like ?";
            sql+="order by recipe_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            if(id!=null&&!"".equals(id)){
                pst.setString(1,"%"+id+"%");
                pst.setString(2,"%"+id+"%");
            }
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                BeanGoodAndRecipe c=new BeanGoodAndRecipe();
                c.setGood_id(rs.getInt(1));
                c.setRecipe_id(rs.getInt(2));
                c.setContent(rs.getString(3));
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

    public List<BeanGood> searchGood(String keyword,int GoodType)throws BaseException{
        //模糊查询商品ID或者商品名称和keyword相匹配的商品信息，如果两者都为空刚开始，则加载所有商品
        List<BeanGood>  result=new ArrayList<>();
        Connection conn=null;
        try {
            conn=DButil.getConnection();
            conn.setAutoCommit(false);
            String sql="select * from good ";
            if((keyword!=null&&!"".equals(keyword))||GoodType>0){
                sql+="where ";
            }
            if(keyword!=null&&!"".equals(keyword))
                sql+=" good_id like ? or good_name like ?";
            if(GoodType>0&&keyword!=null&&!"".equals(keyword)){
                sql+=" and type_id="+GoodType;
            }
            else if(GoodType>0){
                sql+="  type_id="+GoodType;
            }
            sql+="  order by good_id";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            if(keyword!=null&&!"".equals(keyword)){
                pst.setString(1,"%"+keyword+"%");
                pst.setString(2,"%"+keyword+"%");
            }

            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                BeanGood g= new BeanGood();
                g.setGood_id(rs.getInt(1));
                g.setType_id(rs.getInt(2));
                g.setGood_name(rs.getString(3));
                g.setPrice(rs.getDouble(4));
                g.setMember_price(rs.getDouble(5));
                g.setQuantity(rs.getInt(6));
                g.setSpecification(rs.getString(7));
                g.setDetail(rs.getString(8));
                result.add(g);
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

    public void Evaluate(int customer_id,int good_id) throws BaseException{

    }


}
