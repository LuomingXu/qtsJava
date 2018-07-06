package com.qst.service;

import com.qst.Dao.DAO;
import com.qst.model.LogModel;

/*
 * @description:
 * @program: CityLife
 * @author: syun
 * @create: 2018-07-06 14:39
 */
public class LogService {


    /**
     * 添加日志
     * @return
     */
    public boolean addLog(LogModel  log){

        if (DAO.setModel(DAO.TableName.deleteLog, DAO.LogMapperID.insertSelective.toString(), log) > 0) {
            return true;
        }
        return false;
    }

//    public boolean selectLogs(){
//        return DAO.getModel(DAO.TableName.infos)
//    }

}
