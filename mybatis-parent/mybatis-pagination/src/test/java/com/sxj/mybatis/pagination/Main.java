package com.sxj.mybatis.pagination;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sxj.mybatis.pagination.po.PageUser;
import com.sxj.mybatis.pagination.po.User;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        String resource = "com/xwtech/mybatis/test/Configuration.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session1 = factory.openSession(true);
        try
        {
            User parameter = new User();
            List<User> users = session1.selectList("users.selectUsers",
                    parameter);
            System.out.println("返回结果共有:" + users.size());
            for (User user : users)
            {
                System.out.println(user.getName());
            }
            PageUser page = new PageUser();
            
            page.setPagable(true);
            page.setCurrentPage(2);
            page.setShowCount(3);
            users = session1.selectList("selectPageUsers", page);
            System.out.println("分页返回结果共有:" + users.size());
            for (User user : users)
            {
                System.out.println(user.getName());
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            // session1.close();
        }
        
        try
        {
            
            // User user = new User();
            // user.setId(3);
            // user.setName("hello");
            // session1.insert("users.addUser", user);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            session1.close();
        }
        
        // try{
        //
        // User user = (User)session2.selectOne("users.getUserById", 1);
        // System.out.println(user.getName());
        //
        // }catch(Exception ex){
        // ex.printStackTrace();
        // }
        // finally{
        // session2.close();
        // }
        
    }
    
}
