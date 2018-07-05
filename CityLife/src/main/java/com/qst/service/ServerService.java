package com.qst.service;

import com.qst.Dao.UserDao;
import com.qst.model.InfosModel;
import com.qst.model.UserModel;
import com.qst.utils.PwdUtil;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/*
 * @description: 后台处理
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-05 18:59
 */
public class ServerService {


    /**
     * 添加用户信息
     * @param user
     * @return
     */
    public boolean createUser(UserModel user){

        if(UserDao.setModel(UserDao.MapperID.insertSelective, user)>0){
            return true;
        }

        return false;
    }


    /**
     * 更新用户信息
     * @return
     */
    public boolean updateUser(UserModel user){
        if (UserDao.setModel(UserDao.MapperID.updateByPrimaryKeySelective, user) > 0) {
            return true;
        }
        return false;
    }


    /**
     * 删除用户记录
     * @return
     */
    public boolean deleteUser(UserModel user){
        if (UserDao.setModel(UserDao.MapperID.deleteByPrimaryKey, user) > 0) {
            return true;
        }
        return false;
    }


    /**
     * 查找用户信息
     * 传入参为空时,查询所有
     * @return
     */
    public List<UserModel> selectUser(UserModel user){
        if(user.getUserName()==null){
            return UserDao.getModel(UserDao.MapperID.selectAll, user);
        }
        return UserDao.getModel(UserDao.MapperID.selectByPrimaryKey, user);
    }


    public List<InfosModel> selectUser(){
        return null;
    }



    @Test
    public void test01() throws InvalidKeySpecException, NoSuchAlgorithmException {
        UserModel user = new UserModel();
        user.setUserName("syun1");
        user.setPassword(PwdUtil.createHash("123"));
        user.setNickName("syunsyun");
//        createUser(user);
//        updateUser(user);
//        deleteUser(user);
    }


}
