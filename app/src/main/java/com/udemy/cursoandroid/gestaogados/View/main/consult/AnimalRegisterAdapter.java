package com.udemy.cursoandroid.gestaogados.View.main.consult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.IConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.info.IAnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.IInfoCommon;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoTypeEnum;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.List;

public class AnimalRegisterAdapter extends RecyclerView.Adapter<AnimalRegisterAdapter.MyViewHolder>
{
    private List<AnimalRegister> animalsList;
    private IAnimalInfoController animalInfoController;
    private Context context;

    public AnimalRegisterAdapter(
            List<AnimalRegister> animalsList,
            IAnimalInfoController animalInfoController,
            Context context)
    {
        this.animalsList = animalsList;
        this.animalInfoController = animalInfoController;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_animal_register_adapter, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        AnimalRegister animalRegister = animalsList.get(position);

        IInfoCommon info = animalInfoController.getInfoById(InfoTypeEnum.RACE_TYPE, animalRegister.getRace());

        holder.sequenceNumberView.setText(Integer.toString(animalRegister.getSequenceNumber()));
        holder.nameView.setText(animalRegister.getName());
        holder.raceView.setText(info.getNameType());

        if(animalRegister.getImgSource() == null)
        {
            holder.imageView.setImageResource(R.drawable.common_animal_image_icon);
            return;
        }

        int resourceId = holder.itemView.getResources()
                .getIdentifier(
                        animalRegister.getImgSource(),
                        "drawable",
                        context.getPackageName()
                );

        resourceId = resourceId == 0 ? R.drawable.common_animal_image_icon : resourceId;

        holder.imageView.setImageResource(resourceId);
    }

    @Override
    public int getItemCount() {
        return animalsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sequenceNumberView;
        TextView nameView;
        TextView raceView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            sequenceNumberView = itemView.findViewById(R.id.sequenceNumberAnimalRegisterAdapter);
            nameView = itemView.findViewById(R.id.nameAnimalRegisterAdapter);
            raceView = itemView.findViewById(R.id.raceAnimalRegisterAdapter);
            imageView = itemView.findViewById(R.id.imgAnimalRegisterAdapter);
        }
    }
}
