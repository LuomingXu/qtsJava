package com.qst.controller;

import com.qst.Dao.ServereDao;
import com.qst.model.UserModel;
import com.qst.utils.PwdUtil;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Scanner;

/*
 * @description: 后台主页面逻辑
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 14:54
 */
public class Server {


    private ServereDao servereDao = new ServereDao();



    /**
     * 后台能够操作的菜单(实现的功能)
     */
    public void startBack() {
        int chooseNum = 0;

        while(chooseNum!=-1){
            System.out.println("请选择后台如下操作：");
            System.out.println("1.注册用户		 6.添加发布信息");
            System.out.println("2.查询用户		 7.查询后台所有信息");
            System.out.println("3.修改用户		 8.条件查询后台信息");
            System.out.println("4.删除用户		 9.设置信息");
            System.out.println("5.显示删除日志	10.删除信息");
            System.out.println("---按0返回上一级---");
            System.out.println();
            Scanner in = new Scanner(System.in);
            chooseNum = in.nextInt();
            switch (chooseNum) {
                case 1:
                    createUser();
                    break;
                case 2:
                    searchUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    break;

            }
        }


    }

    @Test
    public void test01(){
        new Server().searchUsers();
    }

    public static void main(String[] args) {
        new Server().startBack();
    }






    /**
     * 创建用户
     */
    public void createUser() {
        System.out.println("请输入用户信息:");
        UserModel newUser = new UserModel();
        Scanner sc = new Scanner(System.in);

        System.out.print("账号:");
        String name = sc.nextLine();
        newUser.setUserName(name);

        System.out.print("密码");
        String pwd = sc.nextLine();
        try {
            newUser.setPassword(PwdUtil.createHash(pwd));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.print("昵称:");
        String nickName = sc.nextLine();
        newUser.setNickName(nickName);
        if(servereDao.createUser(newUser)){
            System.out.println("注册成功");
        }
    }

    /**
     * 查找用户信息
     */
    public void searchUser(){
        System.out.print("输入需要查找的name:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        UserModel user = new UserModel();
        user.setUserName(name);
        List<UserModel> userList = servereDao.selectUser(user);
        for(UserModel temp:userList){
            System.out.println(temp.toString());
        }
    }

    /**
     * 查找所有用户信息
     */
    public void searchUsers(){
        UserModel user = new UserModel();
        List<UserModel> userList = servereDao.selectUser(user);
        for(UserModel temp:userList){
            System.out.println(temp.toString());
        }
    }

    /**
     * 更新用户信息
     */
    public void updateUser() {
        UserModel user = new UserModel();
        System.out.print("输入需要给修改的用户的姓名");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.print("新密码");
        String pwd = sc.nextLine();
        System.out.print("新昵称");
        String nickName = sc.nextLine();

        user.setUserName(name);
        user.setNickName(nickName);
        user.setPassword(pwd);

        if(servereDao.updateUser(user)){
            System.out.println("更新成功");
        }else {
            System.out.println("更新失败,请检查操作");
        }

    }
    /**
     * 删除
     */
    public void deleteUser() {
        UserModel user = new UserModel();
        System.out.println("输入需要删除的用户名");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        user.setUserName(name);
        if(servereDao.deleteUser(user)){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败,请检查操作!");
        }
    }



    /**
     * 查询所有信息
     * 包含条件查询
     */
    public void searchInfo() {


    }






    /**
     * 显示日志
     */
    public void showLog() {

    }


    /**
     * 添加发布信息
     */
    public void addReleaseInfo() {

    }



    /**
     * 设置信息
     */
    public void setInfo() {

    }

    /**
     * 删除信息
     */
    public void deleteInfo() {

    }


}
