package com.qst;

import com.qst.Dao.DAO;
import com.qst.model.InfosModel;
import com.qst.utils.PwdUtil;


public class mian
{
    public static void main(String[] args)
    {
        System.out.println("hello world");
        System.out.println(PwdUtil.getPbkdf2HashLength());

        System.out.println(DAO.getModel(DAO.TableName.infos,DAO.InfosMapperID.selectAll.toString(),
                new InfosModel()).size());
    }
}
