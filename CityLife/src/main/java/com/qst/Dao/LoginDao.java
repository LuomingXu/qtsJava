package com.qst.Dao;

import com.qst.model.UserModel;
import com.qst.utils.PwdUtil;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/*
 * @description: 登录验证Dao
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 16:10
 */
public class LoginDao {

    /**
     *
     * @return
     */
    public UserModel userServerLogin(String userName,String pwd){

        UserModel user = new UserModel();
        user.setUserName(userName);
        boolean isSuccess=false;
        user = UserDao.getModel("selectByPrimaryKey",user).get(0);
        try {
            isSuccess= PwdUtil.validatePassword(pwd,user.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        System.out.println(user.toString());

        if(isSuccess){
            return user;
        }
        return null;
    }


    @Test
    public void test(){
        new LoginDao().userServerLogin("123","123");
    }



}
