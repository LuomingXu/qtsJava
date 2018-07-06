package com.qst.service;

import com.qst.Dao.DAO;
import com.qst.model.InfosModel;
import com.qst.model.LogModel;
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

//        if(UserDao.setModel(UserDao.MapperID.insertSelective, user)>0){
//            return true;
//        }
        if(DAO.setModel(DAO.TableName.userinfo,DAO.UserMapperID.insertSelective.toString(),user)>0){
            return true;
        }
        return false;
    }


    /**
     * 更新用户信息
     * @return
     */
    public boolean updateUser(UserModel user){
        if (DAO.setModel(DAO.TableName.userinfo, DAO.UserMapperID.updateByPrimaryKeySelective.toString(), user) > 0) {
            return true;
        }
        return false;
    }


    /**
     * 删除用户记录
     * @return
     */
    public boolean deleteUser(UserModel user){
        if (DAO.setModel(DAO.TableName.userinfo, DAO.UserMapperID.deleteByPrimaryKey.toString(), user) > 0) {
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
            return DAO.getModel(DAO.TableName.userinfo,DAO.UserMapperID.selectAll.toString(), user);
        }
        return DAO.getModel(DAO.TableName.userinfo,DAO.UserMapperID.selectByPrimaryKey.toString(), user);
    }


    /**
     * 查找信息
     * @param info
     * @return
     */
    public List<InfosModel> selectInfo(InfosModel info){

//        return InfosDao.selectModel(InfosDao.MapperID.selectByType, info);
//        System.out.println(info.toString());
        if(info.getType()==null){
            return DAO.getModel(DAO.TableName.infos, DAO.InfosMapperID.selectAll.toString(), info);
        }
        return DAO.getModel(DAO.TableName.infos,DAO.InfosMapperID.selectByType.toString(),info);
    }

    /**
     * 添加信息
     * @return
     */
    public boolean createInfo(InfosModel info){

        if (DAO.setModel(DAO.TableName.infos,DAO.InfosMapperID.insertSelective.toString(), info) > 0) {
            return true;
        }
        return false;
    }


    /**
     * 更新信息
     * @return
     */
    public boolean updateInfo(InfosModel info){
        if (DAO.setModel(DAO.TableName.infos,DAO.InfosMapperID.updateByPrimaryKeySelective.toString(), info) > 0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean deleteInfo(InfosModel info){
        if (DAO.setModel(DAO.TableName.infos,DAO.InfosMapperID.deleteByPrimaryKey.toString(), info) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 添加日志记录
     * @return
     */
    public boolean addLog(LogModel log){
        if(DAO.setModel(DAO.TableName.deleteLog,DAO.LogMapperID.insertSelective.toString(),log)>0){
            return true;
        }
        return false;
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
