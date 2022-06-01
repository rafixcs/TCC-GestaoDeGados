package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

import java.util.ArrayList;
import java.util.List;

public class InfoCommonCollection
{
    List<IInfoCommon> commonList;

    public List<IInfoCommon> getCommonList()
    {
        return commonList;
    }

    public void setCommonList(List<IInfoCommon> commonList)
    {
        this.commonList = commonList;
    }

    public List<String> getInfoNames()
    {
        List<String> names = new ArrayList<>();

        for (int i=0; i<commonList.size(); i++)
        {
            names.add(commonList.get(i).getNameType());
        }

        return names;
    }

    public void add(IInfoCommon info)
    {
        commonList.add(info);
    }

    public int size()
    {
        return commonList.size();
    }

    public IInfoCommon get(int i)
    {
        return commonList.get(i);
    }
}
