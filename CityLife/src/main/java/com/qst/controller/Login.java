package com.qst.controller;

import java.util.Scanner;

/*
 * @description: 初始登录页面
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 14:53
 */
public class Login {


    public void programLogin(){
        System.out.println("\t--------------------------");
        System.out.println("\t-        欢迎登陆本系统                    -");
        System.out.println("\t--------------------------");

        System.out.println("请您先登陆模式");
        System.out.println("请选择登录模式：1.前台查看\t2.登陆后台\t\t3.退出");
        Scanner in = new Scanner(System.in);
        int chooseNum = in.nextInt();


        /*
         * 选择登陆前台，后台
         */
//        Index index = new Index();
//        Server main = new Server();
        switch (chooseNum) {
            case 1:// 登陆前台
                System.out.println("---欢迎进入本系统前台！---");
                new Index().start();
                break;
            case 2:// 登陆后台
                mainLogin();



//                User user = new User();
//                do {
//                    // 进去登陆后台方法
//                    user = main.login();
//                    if (user != null) {
//                        //后台选项循环操作
//                        while (true) {
//                            System.out.println();
//                            System.out.println("-----欢迎 " + user.getUser_name() + " 登陆本系统！！！-----");
//                            globalUser = user.getUser_name();
//                            main.startBack();
//                        }
//                    } else {
//                        System.out.println("您登陆有误，请检查用户名和密码！");
//                    }
//                } while (user == null);
//                break;
            case 3:// 退出程序
//                System.out.println("-----   欢迎您再次使用本系统      -----");
//                System.exit(0);
                break;
            default:
                System.out.println("请您输入有效的选项！");
                break;
        }
    }


    /**
     * 后台登录,登录成功直接进入下个页面,如不成功返回false
     * @return 登录成功或者失败
     */
    public boolean mainLogin(){


        return true;
    }


    /**
     * 程序启动入口
     * @param args
     */
    public static void main(String[] args) {
        new Login().programLogin();
    }



}
