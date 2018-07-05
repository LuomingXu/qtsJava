package com.qst;

import com.qst.Dao.UserDao;
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
        lists=UserDao.getModel("selectByPrimaryKey", model);
        System.out.println("size: "+lists.size());
    }
}
