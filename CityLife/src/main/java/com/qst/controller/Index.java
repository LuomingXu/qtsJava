package com.qst.controller;

import com.qst.model.InfosModel;
import com.qst.service.ServerService;

import java.util.List;
import java.util.Scanner;

/*
 * @description: 前段页面.......
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 14:55
 */
public class Index {

    private ServerService service=new ServerService();

    /**
     * 显示前端的菜单
     */
    public int IndexMeun(){
        System.out.println("------");
        return -1;
    }

    /**
     * 前端的主页逻辑执行启动
     */
    public void start(){

        while(true){
            System.out.println("-------------------------------------------------");
            System.out.println("请选择你需要查看的分类：");
            System.out.println("1.招聘信息\t\t  5.招商引资\t   9.车辆信息");
            System.out.println("2.培训信息\t\t  6.公寓信息\t  10.出售信息");
            System.out.println("3.房屋信息\t\t  7.求职信息\t  11.寻找启示");
            System.out.println("4.求购信息\t\t  8.家教信息\t");
            System.out.println("0.返回上一级");
            System.out.println("-------------------------------------------------");

            Scanner in = new Scanner(System.in);
            int chooseNum = in.nextInt();

            if (chooseNum == 0) {  //相当于返回到登录主页面
                new Login().programLogin(null);
            }else if(chooseNum>0&&chooseNum<12) {
                infoPrint(chooseNum);
            }
            else {
                System.out.println("输入错误!");
            }
        }

    }


    public void infoPrint(int type){

        InfosModel info = new InfosModel();
        info.setType(String.valueOf(type));
        List<InfosModel> infos = service.selectInfo(info);
        if (infos.size() == 0) {
            return;
        }
        for(InfosModel temp:infos){
//            System.out.println(temp.toString());
            System.out.println("信息ID:" + temp.getId() + "\t信息类别：" + temp.getType() + "\t标题："
                    + temp.getTitle() + "\t内容：" + temp.getContent() + "\t联系人:" + temp.getLinkman()
                    + "\t联系电话：" + temp.getPhone() + "\t邮箱：" + temp.getEmail() + "\t付费状态："
                    + temp.getPayfor() + "\t审核状态：" + temp.getState());
        }
    }



}
