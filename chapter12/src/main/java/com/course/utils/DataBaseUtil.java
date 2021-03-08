package com.course.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class DataBaseUtil {
    public static SqlSession getSqlSession() throws IOException {
        //获取配置文件
        Reader reader = Resources.getResourceAsReader("databaseConfig.xml");
        //得到SqlSessionFactory，使用类加载器加载xml文件
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //执行sql语句
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }
}
