package com.qst.utils;

import java.io.*;
import java.util.List;

/*
 * @description: 日志文件操作
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 20:35
 */
public class LogUtils {

    /**
     * 读取日志文件
     * @return
     */
    public static List<String> logRead(String srcFile){
        FileInputStream fis = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(srcFile));
            if(fis!=null){

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {

            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }


    /**
     * 写入日志文件操作
     */
    public static void logWrite(){

    }

}
