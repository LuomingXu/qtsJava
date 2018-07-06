package com.qst.controller;

import com.qst.model.InfosModel;
import com.qst.model.LogModel;
import com.qst.service.LogService;
import com.qst.service.ServerService;
import com.qst.model.UserModel;
import com.qst.utils.LogUtils;
import com.qst.utils.PwdUtil;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/*
 * @description: 后台主页面逻辑
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 14:54
 */
public class Server {


    private ServerService serverService = new ServerService();

    private LogService logService = new LogService();

    private UserModel nowUser ;

    /**
     * 后台能够操作的菜单(实现的功能)
     */
    public void startBack(UserModel user) {
        int chooseNum = 0;
        nowUser=user;
        while(chooseNum!=-1){
            System.out.println("-------------------------------------------------");
            System.out.println("请选择后台如下操作：");
            System.out.println("1.注册用户		 6.添加发布信息");
            System.out.println("2.查询用户		 7.查询后台所有信息");
            System.out.println("3.修改用户		 8.条件查询后台信息");
            System.out.println("4.删除用户		 9.设置信息");
            System.out.println("5.显示删除日志	10.删除信息");
            System.out.println("---按0返回上一级---");
            System.out.println("-------------------------------------------------");

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
                    showLog();
                    break;
                case 6:
                    addReleaseInfo();
                    break;
                case 7:
                    searchInfoAll();
                    break;
                case 8:
                    searchInfo();
                    break;
                case 9:
                    setInfo();
                    break;
                case 10:
                    deleteInfo();
                    break;
                case 0:
                    new Login().programLogin(user);
                    //退出此方法
                    return;


                    default:break;

            }
        }


    }

    @Test
    public void test01(){
        new Server().searchUsers();
    }

    public static void main(String[] args) {
        new Server().startBack(null);
    }






    /**
     * 创建用户
     */
    public void createUser() {
        System.out.println("请输入用户信息:");
        UserModel newUser = new UserModel();
        Scanner sc = new Scanner(System.in);

        System.out.print("账号: ");
        String name = sc.nextLine();
        newUser.setUserName(name);

        System.out.print("密码: ");
        String pwd = sc.nextLine();
        try {
            newUser.setPassword(PwdUtil.createHash(pwd));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        System.out.print("昵称: ");
        String nickName = sc.nextLine();
        newUser.setNickName(nickName);
        if(serverService.createUser(newUser)){
            System.out.println("注册成功");
        }
    }

    /**
     * 查找用户信息
     */
    public void searchUser(){
        System.out.print("输入需要查找的用户的账号:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        UserModel user = new UserModel();
        user.setUserName(name);
        List<UserModel> userList = serverService.selectUser(user);

        if(userList.size()==0){
            System.out.println("无此信息!");
            return;
        }
        for(UserModel temp:userList){

            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp.getCreatetime());
            System.out.println("账号: " + temp.getUserName() + "\t昵称: " + temp.getNickName() + "\t 创建日期: " + date);
//            System.out.println(temp.toString());
        }
    }

    /**
     * 查找所有用户信息
     */
    public void searchUsers(){
        UserModel user = new UserModel();
        List<UserModel> userList = serverService.selectUser(user);
        if(userList.size()==0){
            System.out.println("无此信息!");
            return;
        }
        for(UserModel temp:userList){
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp.getCreatetime());
            System.out.println("账号: "+temp.getUserName()+"\t昵称: "+temp.getNickName()+"\t 创建日期: "+date);
//            System.out.println(temp.toString());
        }
    }

    /**
     * 更新用户信息
     */
    public void updateUser() {
        UserModel user = new UserModel();
        System.out.print("输入需要给修改的用户的姓名:");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.print("新密码: ");
        String pwd = sc.nextLine();
        System.out.print("新昵称: ");
        String nickName = sc.nextLine();

        user.setUserName(name);
        user.setNickName(nickName);
        user.setPassword(pwd);
        if(!UserIsExist(user)){
            return;
        }
        if(serverService.updateUser(user)){
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
        if(!UserIsExist(user)){
           return;
        }
        if(serverService.deleteUser(user)){
            System.out.println("删除成功");
            LogModel log = new LogModel();
            log.setDeletedUsername(user.getUserName());
            log.setDeletedBy(nowUser.getUserName());
            if(logService.addLog(log)){
                System.out.println("添加日志成功!");
            }else {
                System.out.println("添加日志失败!");
            }
        }else {
            System.out.println("删除失败,请检查操作!");
        }
    }

    public boolean UserIsExist(UserModel user){
        if(serverService.selectUser(user).size()==0){
            System.out.println("查无此人!");
            return false;
        }
        return true;
    }


    /**
     * 查询所有信息
     * 包含条件查询
     */
    public void searchInfo() {
        InfosModel info = new InfosModel();
        System.out.println("输入需要查找的类型");
        Scanner sc = new Scanner(System.in);
        String type = sc.nextLine();
        info.setType(type);
        List<InfosModel> Infos = serverService.selectInfo(info);
        if(Infos.size()==0){
            System.out.println("无此信息!");
            return;
        }
        for (InfosModel temp : Infos) {
            System.out.println("信息ID:" + temp.getId() + "\t信息类别：" + temp.getType() + "\t标题："
                    + temp.getTitle() + "\t内容：" + temp.getContent() + "\t联系人:" + temp.getLinkman()
                    + "\t联系电话：" + temp.getPhone() + "\t邮箱：" + temp.getEmail() + "\t付费状态："
                    + temp.getPayfor() + "\t审核状态：" + temp.getState());
//            System.out.println(temp.toString());
        }
    }

    /**
     * 查询所有信息
     */
    public void searchInfoAll(){
        InfosModel info = new InfosModel();
        List<InfosModel> Infos = serverService.selectInfo(info);
        for (InfosModel temp : Infos) {
            System.out.println("信息ID:" + temp.getId() + "\t信息类别：" + temp.getType() + "\t标题："
                    + temp.getTitle() + "\t内容：" + temp.getContent() + "\t联系人:" + temp.getLinkman()
                    + "\t联系电话：" + temp.getPhone() + "\t邮箱：" + temp.getEmail() + "\t付费状态："
                    + temp.getPayfor() + "\t审核状态：" + temp.getState());
//            System.out.println(temp.toString());
        }
    }

    /**
     * 输入信息
     * @return
     */
    public InfosModel initInfo(){
        Scanner sc = new Scanner(System.in);
        InfosModel info = new InfosModel();

        System.out.print("类型:");
        String type = sc.nextLine();

        System.out.print("标题:");
        String title = sc.nextLine();

        System.out.print("内容:");
        String content = sc.nextLine();

        System.out.print("联系人:");
        String linkMan = sc.nextLine();

        System.out.print("手机:");
        String phone = sc.nextLine();

        System.out.print("邮箱:");
        String email = sc.nextLine();

        System.out.print("状态:");
        String state = sc.nextLine();

        System.out.print("支付人:");
        String payfor = sc.nextLine();

        info.setType(type);
        info.setContent(content);
        info.setEmail(email);
        info.setLinkman(linkMan);
        info.setPayfor(payfor);
        info.setTitle(title);
        info.setPhone(phone);
        info.setState(state);
        return info;
    }

    /**
     * 添加发布信息
     */
    public void addReleaseInfo() {


        System.out.println("输入需要创建的信息");
        InfosModel info = initInfo();

        if (serverService.createInfo(info)) {
            System.out.println("添加成功!");
        }else {
            System.out.println("添加失败!");
        }

    }


    /**
     * 设置信息
     */
    public void setInfo() {
        System.out.println("输入需要修改的信息(根据id修改)");

        InfosModel info = initInfo();

        System.out.print("输入需要更改的id:");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();

        info.setId(id);

//        判断时候需要修改的记录是否在数据库中
        if(!InfoIsExit(info)){
            return;
        }
        if(serverService.updateInfo(info)){
            System.out.println("更新成功");
        }else {
            System.out.println("更新失败");
        }
    }


    /**
     * 删除信息
     */
    public void deleteInfo() {
        InfosModel info = new InfosModel();
        System.out.print("输入需要删除信息的id:");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();
        info.setId(id);
//        判断时候需要修改的记录是否在数据库中
        if(!InfoIsExit(info)){
            return;
        }

        if (serverService.deleteInfo(info)) {
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }

    }



    public boolean InfoIsExit(InfosModel info){
        if (serverService.selectInfo(info).size() == 0) {
            System.out.println("无此信息!");
            return false;
        }
        return true;
    }



    /**
     * 显示日志
     */
    public void showLog() {
        List<String> logs = LogUtils.logRead();
        for (String log : logs) {
            System.out.println(log);
        }
    }








}
