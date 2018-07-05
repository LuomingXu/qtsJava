/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : UserDao.java
 * CreateTime: 2018/07/05 15:00:48
 * LastModifiedDate : 18-6-29 下午5:00
 */

package com.qst.Dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDao
{
    private final static String MYBATIS_CONFIG_XML_PATH = "user-config.xml";

    /**
     * connect DB get model
     *
     * @param mapperID   id in mapper.xml
     * @param paramModel generics class which you could get data for you select statement
     * @param <T>        generics class name
     * @return model from DB
     */
    public static <T> List<T> getModel(String mapperID, T paramModel) throws NullPointerException
    {
        List<T> models;

        try
        {
            //load mybatis config
            InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG_XML_PATH);

            SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
            SqlSession session = ssf.openSession();

            models = session.selectList(mapperID, paramModel);

            return models;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }

        return null;
    }
}