/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : InfosDao.java
 * CreateTime: 2018/07/05 20:10:58
 * LastModifiedDate : 18-7-5 下午8:10
 */

package com.qst.Dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class InfosDao
{
    private final static String MYBATIS_CONFIG_XML_PATH = "config-infos.xml";

    public enum MapperID
    {
        //还需要添加几个模糊查询
        selectByType,
        deleteByPrimaryKey,
        insertSelective,
        updateByPrimaryKeySelective
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

    public static <T> List<T> selectModel(InfosDao.MapperID mapperID, T paramModel)
    {
        List<T> models;

        try
        {
            SqlSession session = getSession();
            if (session != null)
            {

                switch (mapperID)
                {
                    case selectByType:
                        models = session.selectList(mapperID.toString());
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

    public static <T> int setModel(InfosDao.MapperID mapperID, T paramModel)
    {
        int count = 0;

        try
        {
            SqlSession session = getSession();

            if (session != null)
            {
                switch (mapperID)
                {
                    case updateByPrimaryKeySelective:
                        count = session.update(mapperID.toString(), paramModel);
                        break;
                    case deleteByPrimaryKey:
                        count = session.delete(mapperID.toString(), paramModel);
                        break;
                    case insertSelective:
                        count = session.insert(mapperID.toString(), paramModel);
                        break;
                    default:
                        throw new Exception("No suck choice in this method. ");
                }

                //operation could be done, then commit
                if (count > 0)
                    session.commit();
                else
                    throw new Exception("Operation could't be done. ");
            }

            return count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }
}
