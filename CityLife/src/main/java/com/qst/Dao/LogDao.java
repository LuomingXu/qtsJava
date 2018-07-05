/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : LogDao.java
 * CreateTime: 2018/07/05 19:04:49
 * LastModifiedDate : 18-7-5 下午7:04
 */

package com.qst.Dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LogDao
{
    private final static String MYBATIS_CONFIG_XML_PATH = "config-log.xml";

    public enum MapperID
    {
        selectAll,
        selectByDeletedUsername,
        insertSelective
    }

    private static SqlSession getSession()
    {
        try
        {
            //load mybatis config
            InputStream inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG_XML_PATH);

            SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);

            return ssf.openSession();
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

    /**
     * connect DB get model
     *
     * @param mapperID   id in mapper.xml
     * @param paramModel generics class which you could get data for you select statement
     * @param <T>        generics class name
     * @return model from DB
     */
    public static <T> List<T> selectModel(LogDao.MapperID mapperID, T paramModel)
    {
        List<T> models;

        try
        {
            SqlSession session = getSession();
            if (session != null)
            {

                switch (mapperID)
                {
                    case selectAll:
                        models = session.selectList(mapperID.toString());
                        break;
                    case selectByDeletedUsername:
                        models = session.selectList(mapperID.toString(),paramModel);
                        break;
                    default:
                        throw new Exception("No suck choice in this method. ");
                }

                return models;
            }
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }

        return null;
    }

    /**
     * operate db
     *
     * @param mapperID      id in mapper.xml
     * @param paramModel    generics class which you could get data for you select statement
     * @param <T>           generics class name
     * @return              affected rows
     */
    public static <T> int insertModel(LogDao.MapperID mapperID, T paramModel)
    {
        int count = 0;

        try
        {
            SqlSession session = getSession();

            if (session != null && mapperID == MapperID.insertSelective)
            {
                count = session.insert(mapperID.toString(), paramModel);
            }

            //operation could be done, then commit
            if (count > 0)
                session.commit();
            else
                throw new Exception("Operation could't be done. ");

            return count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}
