package com.qst;

import com.qst.Dao.DAO;
import com.qst.model.InfosModel;
import com.qst.model.LogModel;
import com.qst.utils.PwdUtil;


public class mian
{
    public static void main(String[] args)
    {
        System.out.println("hello world");
        System.out.println(PwdUtil.getPbkdf2HashLength());
        
        try
        {
            LogModel model1 = new LogModel();

            model1.setDeletedBy("345");
            model1.setDeletedUsername("123");
            //System.out.println("logdao size: "+LogDao.insertModel(LogDao.MapperID.insertSelective, model1));
           // System.out.println(LogDao.selectModel(LogDao.MapperID.selectAll,null));
            System.out.println(DAO.getModel(DAO.TableName.userinfo,DAO.UserMapperID.selectAll.toString(),model1));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            InfosModel model2 = new InfosModel();
            model2.setType("dfsa");
            System.out.println(DAO.getModel(DAO.TableName.infos,DAO.InfosMapperID.selectByType.toString(),
                    model2));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
