package DAO;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class DaoFactoryClass {
    private static DaoFactoryClass factory;
    private static SqlSessionFactory sqlSessionFactory;

    private DaoFactoryClass() {
        try {
            /**
             * 根据xml文件创建工厂，只有一个，
             */
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("/mybatis.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static DaoFactoryClass getInstance() {
        if (factory == null) {
            factory = new DaoFactoryClass();
        }
        return factory;

    }
    public <T> T org(Class<T> tClass) {
        /**
         * 根据java反射得出org，并且打开一个会话
         */
        return sqlSessionFactory.openSession(true).getMapper(tClass);
    }
}
