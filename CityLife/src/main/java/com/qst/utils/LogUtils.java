package com.qst.utils;

import com.qst.model.LogModel;
import com.qst.Dao.DAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WouledYou
 */
public class LogUtils {

    /**
     * 获取桌面目录
     * @return 桌面目录名
     */
    private static String getFilePath()
    {
        return String.format("C:\\Users\\%s\\Desktop\\qstlog.txt",System.getenv().get("USERNAME"));
    }

    /**
     * 读取文件中的日志
     * @return 日志
     */
    public static List<String> getLogLists()
    {
       List<String> logLists;
       logLists=logRead(getFilePath());
        if(logLists!=null){

            return logLists;
        }else{
            logWrite();
            logLists=logRead(getFilePath());
        }
        return logLists;
    }

    /**
     * 读取本地日志文件
     * @param srcFile 文件目录
     * @return 文件所含内容
     */
    private static List<String> logRead(String srcFile){
             FileInputStream fis=null;
             BufferedReader br=null;
             List<String> logs=new ArrayList<String>();
             try {

                 br = new BufferedReader(new FileReader(srcFile));

             try {
                 String temp=null;
                while ((temp=br.readLine() )!=null) {


                    logs.add(temp);
                }

            }catch (IOException e) {
                e.printStackTrace();
            }
           }catch (FileNotFoundException e) {
          e.printStackTrace();
       }
           finally {
            try {
               br.close();

            } catch (IOException e) {
               e.printStackTrace();
           }
        }
        return logs;
    }


    /**
     *将数据库的操作日志读取写入到本地文件中
     */
    private static void logWrite(){

         List<LogModel> logModels;
         logModels=DAO.getModel(DAO.TableName.deleteLog,DAO.LogMapperID.selectAll.toString(),new LogModel());
         File file=new File(getFilePath());

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw=null;

        try {
            bw=new BufferedWriter(new FileWriter(getFilePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (LogModel item : logModels){

            try {
                bw.write(item.logFormat());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
