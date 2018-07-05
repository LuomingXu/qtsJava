package com.qst;

import com.qst.Dao.LogDao;
import com.qst.Dao.UserDao;
import com.qst.model.LogModel;
import com.qst.model.UserModel;
import com.qst.utils.PwdUtil;

import java.util.List;

public class mian
{
    public static void main(String[] args)
    {
        System.out.println("hello world");
        System.out.println(PwdUtil.getPbkdf2HashLength());

        UserModel model = new UserModel();
        model.setUserName("123");
        List<UserModel> lists;
        lists=UserDao.getModel(UserDao.MapperID.selectByPrimaryKey, model);
        System.out.println("size: "+lists.size());

//        try
//        {
//            model.setUserName("345");
//            model.setPassword(PwdUtil.createHash("345"));
//            model.setNickName("345");
//            System.out.println(UserDao.setModel(UserDao.MapperID.insertSelective, model));
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }

        try
        {
            LogModel model1 = new LogModel();

            model1.setDeletedBy("345");
            model1.setDeletedUsername("123");
            //System.out.println("logdao size: "+LogDao.insertModel(LogDao.MapperID.insertSelective, model1));
            System.out.println(LogDao.selectModel(LogDao.MapperID.selectAll,null));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
