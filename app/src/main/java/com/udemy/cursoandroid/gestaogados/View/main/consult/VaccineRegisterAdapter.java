package com.udemy.cursoandroid.gestaogados.View.main.consult;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.List;

public class VaccineRegisterAdapter extends RecyclerView.Adapter<VaccineRegisterAdapter.MyViewHolder>
{
    private List<VaccineTask> vaccineTaskList;
    private Integer maxQuantity;

    public VaccineRegisterAdapter(List<VaccineTask> vaccineTaskList)
    {
        this.vaccineTaskList = vaccineTaskList;
        this.maxQuantity = null;
    }

    public VaccineRegisterAdapter(List<VaccineTask> vaccineTaskList, Integer maxQuantity) {
        this.vaccineTaskList = vaccineTaskList;
        this.maxQuantity = maxQuantity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_vaccine_task_adapter, parent, false);

        return new VaccineRegisterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.name.setText(vaccineTaskList.get(position).getName());
        holder.date.setText(vaccineTaskList.get(position).getDate());
        holder.done.setText(Boolean.toString(vaccineTaskList.get(position).getIsDone()));
    }

    @Override
    public int getItemCount()
    {
        if (maxQuantity != null && maxQuantity < vaccineTaskList.size())
        {
            return maxQuantity;
        }

        return vaccineTaskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView date;
        public TextView done;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.nameVaccineList);
            date = itemView.findViewById(R.id.dateVaccineList);
            done = itemView.findViewById(R.id.doneVaccineList);
        }
    }
}
