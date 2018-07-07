/*
 * Copyright (c) 2018
 * Author : Luoming Xu
 * Project Name : CityLife
 * File Name : DAO.java
 * CreateTime: 2018/07/06 07:52:44
 * LastModifiedDate : 18-7-6 上午7:52
 */

package com.qst.Dao;

import com.sun.xml.internal.ws.api.pipe.NextAction;
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

    /**
     * 对应数据库的表名, 完全一致
     */
    public enum TableName
    {
        userinfo,
        deleteLog,
        infos
    }

    /**
     * 对应mapper-user.xml里面的id
     */
    public enum UserMapperID
    {
        selectAll,
        selectByPrimaryKey,
        deleteByPrimaryKey,
        insertSelective,
        updateByPrimaryKeySelective
    }

    /**
     * 对应mapper-log.xml里面的id
     */
    public enum LogMapperID
    {
        selectAll,
        selectByDeletedUsername,
        insertSelective
    }

    /**
     * 对应mapper-infos.xml里面的id
     */
    public enum InfosMapperID
    {
        //还需要添加几个模糊查询
        selectAll,
        selectByType,
        deleteByPrimaryKey,
        insertSelective,
        updateByPrimaryKeySelective
    }

    private enum sqlOperation
    {
        insert,
        delete,
        update
    }

    /**
     * 根据表名获取不同的SqlSession
     *
     * @param tableName
     * @return
     */
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

    /**
     * tablename是否匹配对应的mapperID
     *
     * @param tableName 数据的表名
     * @param mapperID  对应xml的id
     * @param <T>       enum的泛型
     * @return 匹配返回true
     * @throws Exception
     */
    private static <T> boolean isTableMatchItsMapperId(TableName tableName, T mapperID) throws Exception
    {
        switch (tableName)
        {
            case userinfo:
                return mapperID.getClass().getName().equals(UserMapperID.class.getName());
            case deleteLog:
                return mapperID.getClass().getName().equals(LogMapperID.class.getName());
            case infos:
                return mapperID.getClass().getName().equals(InfosMapperID.class.getName());
            default:
                throw new Exception("No such table");
        }
    }

    /**
     * 获取mapperID的操作是update, insert, delete的类型
     *
     * @param mapperID 对应xml的id
     * @param <T>      enum的泛型
     * @return 对应的sql操作
     * @throws Exception
     */
    private static <T> sqlOperation getSqlOperation(T mapperID) throws Exception
    {
        String temp = mapperID.toString().substring(0, 1);
        if (temp.equals("i"))
        {
            return sqlOperation.insert;
        }
        else if (temp.equals("u"))
        {
            return sqlOperation.update;
        }
        else if (temp.equals("d"))
        {
            return sqlOperation.delete;
        }
        else
        {
            throw new Exception("no such sql operation");
        }
    }

    /**
     * sql的select语句都在这边处理
     *
     * @param tableName  需要操作的数据的表的名称
     * @param mapperID   对应xml文件里面id
     * @param paramModel 参数类
     * @param <T>        参数类的class, 全部都在model包里面, 包含一个enum
     * @return 参数类的一个list集合
     */
    public static <T> List<T> getModelRelease(TableName tableName, T mapperID, T paramModel)
    {
        List<T> models = null;

        //判断参数是否合法
        try
        {
            if (paramModel == null)
            {
                throw new Exception("paramModel could not be null. ");
            }

            if (!isTableMatchItsMapperId(tableName, mapperID))
            {
                throw new Exception("Table name is not match its mapper id. ");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        try
        {
            SqlSession session = getSession(tableName);

            if (session != null)
            {
                models = session.selectList(mapperID.toString(), paramModel);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return models;
    }

    /**
     * sql的insert, delete, update方法, 全部都通过这个方法实现
     *
     * @param tableName  需要操作的数据的表的名称
     * @param mapperID   对应xml文件里面id
     * @param paramModel 参数类
     * @param <T>        参数类的class, 全部都在model包里面, 包含一个enum
     * @return affected rows
     */
    public static <T> int setModelRelease(TableName tableName, T mapperID, T paramModel)
    {
        //判断参数是否合法
        try
        {
            if (paramModel == null)
            {
                throw new Exception("paramModel could not be null. ");
            }

            if (!isTableMatchItsMapperId(tableName, mapperID))
            {
                throw new Exception("Table name is not match its mapper id. ");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return 0;
        }

        int count = 0;
        try
        {
            SqlSession session = getSession(tableName);

            if (session != null)
            {
                switch (getSqlOperation(mapperID))
                {
                    case update:
                        count = session.update(mapperID.toString(), paramModel);
                        break;
                    case delete:
                        count = session.delete(mapperID.toString(), paramModel);
                        break;
                    case insert:
                        count = session.insert(mapperID.toString(), paramModel);
                        break;
                    default:
                        throw new Exception("No such choice in mapper.xml");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * sql的select语句都在这边处理
     *
     * @param tableName  需要操作的数据的表的名称
     * @param mapperID   对应xml文件里面id
     * @param paramModel 参数类
     * @param <T>        参数类的class, 全部都在model包里面
     * @return 参数类的一个list集合
     */
    public static <T> List<T> getModel(TableName tableName, String mapperID, T paramModel)
    {
        System.err.println("This method is not recommended");

        List<T> models = null;

        try
        {
            SqlSession session = getSession(tableName);

            if (session != null)
            {
                /**
                 * 如果直接是selectAll就直接返回
                 */
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
                                models = session.selectList(mapperID, paramModel);
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
                                models = session.selectList(mapperID, paramModel);
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
                                models = session.selectList(mapperID, paramModel);
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

    /**
     * sql的insert, delete, update方法, 全部都通过这个方法实现
     *
     * @param tableName  需要操作的数据的表的名称
     * @param mapperID   对应xml文件里面id
     * @param paramModel 参数类
     * @param <T>        参数类的class, 全部都在model包里面
     * @return affected rows
     */
    public static <T> int setModel(TableName tableName, String mapperID, T paramModel)
    {
        System.err.println("This method is not recommended");


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
                {
                    session.commit();
                }
                else
                {
                    throw new Exception("Operation could't be done. ");
                }
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
