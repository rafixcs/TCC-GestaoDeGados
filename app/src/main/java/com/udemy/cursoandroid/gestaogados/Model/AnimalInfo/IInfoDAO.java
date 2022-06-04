package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

import java.util.List;

public interface IInfoDAO
{
    public void save(IInfoCommon info);
    public List<IInfoCommon> getInfoTable(InfoTypeEnum infoType);
    public void delete(IInfoCommon info);
    public IInfoCommon getById(InfoTypeEnum infoTypeEnum, int id);
}
