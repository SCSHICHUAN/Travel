package DAO;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class DaoFactoryXML {
    private static DaoFactoryXML factory;
    private static SqlSessionFactory sqlSessionFactory;
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();

    private DaoFactoryXML() {
        try {
            /**
             * 根据xml文件创建工厂，只有一个，
             */
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("/mybatis.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void InitDaoFactoryXML() {
        if (factory == null) {
            factory = new DaoFactoryXML();
        }
    }


    /**
     * 当多个请求发送到同一个Servlet，服务器会为每个请求创建一个新线程来处理
     * threadLocal保证了每次打开和关闭的SqlSession是同一个
     */


    /**
     * 从当前线程中获取SqlSession
     */
    public static SqlSession OpenSqlSession() {
        //从当前线程获取，如果没有就给当前线程创建一个
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
            //将sqlSession与当前线程绑定
            threadLocal.set(sqlSession);
            System.out.println("--------打开SqlSession--------");
        }
        return sqlSession;
    }

    /**
     * 关闭当前线程中的SqlSession
     */
    public static void CloseSqlSession() {
        SqlSession sqlSession = threadLocal.get();
        if (sqlSession != null) {
            sqlSession.close();
            threadLocal.remove();
            threadLocal.set(null);
            System.out.println("--------关闭SqlSession--------");
        }

    }


}
