package com.qst.controller;

import com.qst.service.LoginService;
import com.qst.model.UserModel;

import java.util.Scanner;

/*
 * @description: 初始登录页面
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 14:53
 */
public class Login{

    private UserModel user=null;

    public void programLogin(UserModel user){

        this.user=user;


        int chooseNum=-1;



        while (chooseNum!=3){

            System.out.println("\t--------------------------");
            System.out.println("\t-        欢迎登陆本系统                    -");
            System.out.println("\t--------------------------");
            System.out.println("请您先登陆模式");
            System.out.println("请选择登录模式：1.前台查看\t2.登陆后台\t\t3.退出");
            Scanner in = new Scanner(System.in);

            chooseNum = in.nextInt();

            switch (chooseNum) {
                case 1:// 登陆前台
                    System.out.println("---欢迎进入本系统前台！---");
                    new Index().start();
                    break;
                case 2:// 登陆后台
                    mainLogin();
                    break;
                case 3:// 退出程序
                    System.out.println("-----   欢迎您再次使用本系统      -----");
                    System.exit(0);
                    break;
                default:
                    System.out.println("请您输入有效的选项！");
                    break;
            }
        }

    }


    /**
     * 后台登录,登录成功直接进入下个页面,如不成功返回false
     * @return 登录成功或者失败
     */
    public boolean mainLogin(){

        //若是已有用户,则不进入下面验证
        if(user!=null){
            new Server().startBack(user);
            return true;
        }

        String name;
        String pwd ;

        System.out.print("用户名:");
        Scanner sc = new Scanner(System.in);
        name = sc.nextLine();
        System.out.print("密码:");
        pwd = sc.nextLine();
        UserModel user = new LoginService().userServerLogin(name, pwd);

        if(user==null){
            System.out.println("登录失败,账号或密码错误!");
        }else {
            System.out.println("用户: " + user.getNickName() + "登录成功");
            new Server().startBack(user);
        }

        return false;
    }


    /**
     * 程序启动入口
     * @param args
     */
    public static void main(String[] args) {
        new Login().programLogin(null);
    }



}
