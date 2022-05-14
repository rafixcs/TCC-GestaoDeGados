package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class FarmsMockRecord {
    List<Farm> listFarms;

    public FarmsMockRecord() {
        listFarms = new ArrayList<>();
        List<String> listLoot = new ArrayList<>();
        listLoot.add("1");
        listLoot.add("2");
        listLoot.add("3");
        listLoot.add("4");
        listFarms.add(new Farm(0, "leaozinho", listLoot));

        List<String> listLoot1 = new ArrayList<>();
        listLoot1.add("5");
        listLoot1.add("6");
        listLoot1.add("7");
        listFarms.add(new Farm(1, "marea turbo", listLoot1));

        List<String> listLoot2 = new ArrayList<>();
        listLoot2.add("8");
        listLoot2.add("9");
        listFarms.add(new Farm(2, "civicao bolado fazendinha", listLoot2));
    }

    public List<String> getFarmsNames() {

        List<String> names = new ArrayList<>();
        for (int i=0; i<listFarms.size(); i++)
        {
            names.add(listFarms.get(i).getName());
        }

        return names;
    }

    public List<String> getFarmLoots(int index) {
        return listFarms.get(index).getFarmLoots();
    }
}
