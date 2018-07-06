package com.qst.service;

import com.qst.model.UserModel;
import com.qst.utils.PwdUtil;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/*
 * @description: 登录验证Dao
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 16:10
 */
public class LoginService {

    /**
     *
     * @return
     */
    public UserModel userServerLogin(String userName,String pwd){

        UserModel user = new UserModel();
        user.setUserName(userName);
        boolean isSuccess=false;
        List<UserModel>  userSelecteds = new ServerService().selectUser(user);
        if(userSelecteds.size()<0){
            return null;
        }
        UserModel  userSelected = new ServerService().selectUser(user).get(0);
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
        if(isSuccess){
            return userSelected;
        }
        return null;
    }


}
