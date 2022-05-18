package com.udemy.cursoandroid.gestaogados.View.main.consult;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.View.ICommonView;

public interface IConsultAnimalRegisterView extends ICommonView {
    public void setAnimalRegister(AnimalRegister animal);
    public void onFailedConsultRegister();
}
