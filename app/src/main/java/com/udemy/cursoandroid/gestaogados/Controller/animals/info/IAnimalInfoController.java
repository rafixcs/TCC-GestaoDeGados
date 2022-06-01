package com.udemy.cursoandroid.gestaogados.Controller.animals.info;

import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.IInfoCommon;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoCommonCollection;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoTypeEnum;

import java.util.List;

public interface IAnimalInfoController
{
    public void save(IInfoCommon info);
    public InfoCommonCollection getInfoList(InfoTypeEnum infoTypeEnum);
    public void delete(IInfoCommon info);
    public void result(boolean result);
}
