package com.qst;

import com.qst.Dao.DAO;
import com.qst.model.InfosModel;
import com.qst.model.UserModel;
import com.qst.utils.PwdUtil;


public class mian
{
    public static void main(String[] args)
    {
        System.out.println("hello world");
        System.out.println(PwdUtil.getPbkdf2HashLength());

        try
        {
            System.out.println(DAO.getModelRelease(DAO.TableName.deleteLog,DAO.LogMapperID.selectAll,new UserModel()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
