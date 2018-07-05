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
        UserModel  userSelected = new ServereDao().selectUser(user).get(0);
        try {
            if(userSelected==null){
                return null;
            }
            isSuccess= PwdUtil.validatePassword(pwd,userSelected.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        System.out.println(user.toString());

        if(isSuccess){
            return userSelected;
        }
        return null;
    }


    @Test
    public void test(){
        UserModel user= new LoginDao().userServerLogin("syun1","123");
        System.out.println("s");
    }



}
