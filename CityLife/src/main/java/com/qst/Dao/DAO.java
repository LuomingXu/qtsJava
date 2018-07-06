/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : DAO.java
 * CreateTime: 2018/07/06 07:52:44
 * LastModifiedDate : 18-7-6 上午7:52
 */

package com.qst.Dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DAO
{
    private final static String USER_MYBATIS_CONFIG_XML_PATH = "config-user.xml";
    private final static String LOG_MYBATIS_CONFIG_XML_PATH = "config-log.xml";
    private final static String INFOS_MYBATIS_CONFIG_XML_PATH = "config-infos.xml";

    public enum TableName
    {
        userinfo,
        deleteLog,
        infos
    }

    public enum UserMapperID
    {
        selectAll,
        selectByPrimaryKey,
        deleteByPrimaryKey,
        insertSelective,
        updateByPrimaryKeySelective
    }

    public enum LogMapperID
    {
        selectAll,
        selectByDeletedUsername,
        insertSelective
    }

    public enum InfosMapperID
    {
        //还需要添加几个模糊查询
        selectByType,
        deleteByPrimaryKey,
        insertSelective,
        updateByPrimaryKeySelective
    }

    private static SqlSession getSession(TableName tableName)
    {
        InputStream inputStream;

        try
        {
            //load mybatis config
            switch (tableName)
            {
                case userinfo:
                    inputStream = Resources.getResourceAsStream(USER_MYBATIS_CONFIG_XML_PATH);
                    break;
                case deleteLog:
                    inputStream = Resources.getResourceAsStream(LOG_MYBATIS_CONFIG_XML_PATH);
                    break;
                case infos:
                    inputStream = Resources.getResourceAsStream(INFOS_MYBATIS_CONFIG_XML_PATH);
                    break;
                default:
                    throw new Exception("No such table");
            }

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


    public static <T> List<T> getModel(TableName tableName, String mapperID, T paramModel)
    {
        List<T> models = null;

        try
        {
            SqlSession session = getSession(tableName);

            if (session != null)
            {
                if (mapperID.equals("selectAll"))
                {
                    models = session.selectList(mapperID);
                    return models;
                }

                switch (tableName)
                {
                    case userinfo:
                        UserMapperID userMapID = UserMapperID.valueOf(mapperID);

                        switch (userMapID)
                        {
                            case selectByPrimaryKey:
                                models = session.selectList(mapperID,paramModel);
                                break;
                            default:
                                throw new Exception("No suck choice in this method. ");
                        }
                        break;
                    case deleteLog:
                        LogMapperID LogMapID = LogMapperID.valueOf(mapperID);

                        switch (LogMapID)
                        {
                            case selectByDeletedUsername:
                                models = session.selectList(mapperID,paramModel);
                                break;
                            default:
                                throw new Exception("No suck choice in this method. ");
                        }
                        break;
                    case infos:
                        InfosMapperID InfosMapID = InfosMapperID.valueOf(mapperID);

                        switch (InfosMapID)
                        {
                            case selectByType:
                                models = session.selectList(mapperID);
                                break;
                            default:
                                throw new Exception("No suck choice in this method. ");
                        }
                        break;
                    default:
                        throw new Exception("No suck choice in this method. ");
                }
            }

        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }

        return models;
    }

    public static <T> int setModel(TableName tableName, String mapperID, T paramModel)
    {
        int count = 0;

        try
        {
            SqlSession session = getSession(tableName);

            if (session != null)
            {
                switch (tableName)
                {
                    case userinfo:
                        UserMapperID userMapID = UserMapperID.valueOf(mapperID);

                        switch (userMapID)
                        {
                            case updateByPrimaryKeySelective:
                                count = session.update(mapperID, paramModel);
                                break;
                            case deleteByPrimaryKey:
                                count = session.delete(mapperID, paramModel);
                                break;
                            case insertSelective:
                                count = session.insert(mapperID, paramModel);
                                break;
                            default:
                                throw new Exception("No suck choice in this method. ");
                        }
                        break;
                    case deleteLog:
                        LogMapperID LogMapID = LogMapperID.valueOf(mapperID);

                        switch (LogMapID)
                        {
                            case insertSelective:
                                count = session.insert(mapperID, paramModel);
                                break;
                            default:
                                throw new Exception("No suck choice in this method. ");
                        }
                        break;
                    case infos:
                        InfosMapperID InfosMapID = InfosMapperID.valueOf(mapperID);

                        switch (InfosMapID)
                        {
                            case updateByPrimaryKeySelective:
                                count = session.update(mapperID, paramModel);
                                break;
                            case deleteByPrimaryKey:
                                count = session.delete(mapperID, paramModel);
                                break;
                            case insertSelective:
                                count = session.insert(mapperID, paramModel);
                                break;
                            default:
                                throw new Exception("No suck choice in this method. ");
                        }
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
