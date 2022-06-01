package com.udemy.cursoandroid.gestaogados.Controller.animals.info;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.IInfoCommon;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.IInfoDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoCommonCollection;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoTypeEnum;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.View.main.consult.IConsultAnimalRegisterView;
import com.udemy.cursoandroid.gestaogados.View.main.register.IRegisterBovineView;

import java.util.List;

public class AnimalInfoController implements IAnimalInfoController
{
    private IRegisterBovineView registerBovineView;
    private IConsultAnimalRegisterView consultAnimalRegisterView;
    private Context context;


    public AnimalInfoController(IRegisterBovineView registerBovineView, Context context)
    {
        this.context = context;
        this.registerBovineView = registerBovineView;
    }

    public AnimalInfoController(IConsultAnimalRegisterView consultAnimalRegisterView, Context context)
    {
        this.context = context;
        this.consultAnimalRegisterView = consultAnimalRegisterView;
    }

    @Override
    public void save(IInfoCommon info)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        IInfoDAO infoDAO = new InfoDAO(databaseAccess.getDb(), this);

        infoDAO.save(info);
    }

    @Override
    public InfoCommonCollection getInfoList(InfoTypeEnum infoTypeEnum)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        IInfoDAO infoDAO = new InfoDAO(databaseAccess.getDb(), this);

        InfoCommonCollection infoCollection = new InfoCommonCollection();
        List<IInfoCommon> infoList = infoDAO.getInfoTable(infoTypeEnum);
        infoCollection.setCommonList(infoList);

        return infoCollection;
    }

    @Override
    public void delete(IInfoCommon info)
    {

    }

    @Override
    public void result(boolean result)
    {
        if (registerBovineView != null)
        {
            registerBovineView.setSaveResult(result);
        }
        else if (consultAnimalRegisterView != null)
        {
            //consultAnimalRegisterView.
        }
    }
}
