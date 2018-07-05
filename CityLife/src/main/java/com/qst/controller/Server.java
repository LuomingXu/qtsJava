package com.qst.controller;

import com.qst.model.UserModel;

import java.util.Scanner;

/*
 * @description: 后台主页面逻辑
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 14:54
 */
public class Server {


    /**
     * 后台能够操作的菜单(实现的功能)
     */
    public void startBack(){
        System.out.println("请选择后台如下操作：");
        System.out.println("1.注册用户		 6.添加发布信息");
        System.out.println("2.查询用户		 7.查询后台所有信息");
        System.out.println("3.修改用户		 8.条件查询后台信息");
        System.out.println("4.删除用户		 9.设置信息");
        System.out.println("5.显示删除日志	10.删除信息");
        System.out.println("\n---按0返回上一级---");
        System.out.println();
        Scanner in = new Scanner(System.in);
        int chooseNum = in.nextInt();
    }



    /**
     * 查询用户
     */
    public void searchUser(){

    }

//    System.out.println("1.注册用户		 6.添加发布信息");
//        System.out.println("2.查询用户		 7.查询后台所有信息");
//        System.out.println("3.修改用户		 8.条件查询后台信息");
//        System.out.println("4.删除用户		 9.设置信息");
//        System.out.println("5.显示删除日志	10.删除信息");


    /**
     * 创建用户
     */
    public void createUser(){
        System.out.println("创建用户");
        UserModel newUser=new UserModel();
        newUser.setUserName("181364");
        newUser.setNickName("syun");
        newUser.setPassword("181364");


    }


    /**
     * 删除
     */
    public void deleteUser(){

    }


    /**
     * 更新
     */
    public void updateUser(){

    }


    /**
     * 显示日志
     */
    public void showLog(){

    }


    /**
     * 添加发布信息
     */
    public void addReleaseInfo(){

    }

    /**
     * 查询所有信息
     * 包含条件查询
     */
    public void searchInfo(){

    }

    /**
     * 设置信息
     */
    public void setInfo(){

    }

    /**
     * 删除信息
     */
    public void deleteInfo(){

    }


}
