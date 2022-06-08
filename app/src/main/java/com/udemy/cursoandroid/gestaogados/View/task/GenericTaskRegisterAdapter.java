package com.udemy.cursoandroid.gestaogados.View.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.List;

public class GenericTaskRegisterAdapter extends RecyclerView.Adapter<GenericTaskRegisterAdapter.MyViewHolder>
{
    private List<GenericTask> genericTaskList;

    public GenericTaskRegisterAdapter(List<GenericTask> genericTaskList)
    {
        this.genericTaskList = genericTaskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_generic_task_adapter, parent, false);

        return new GenericTaskRegisterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        String isDone = genericTaskList.get(position).isDone() ? "done" : "todo";

        holder.name.setText(genericTaskList.get(position).getName());
        holder.done.setText(isDone);
    }

    @Override
    public int getItemCount() {
        return genericTaskList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView done;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.nameGenericTaskList);
            done = itemView.findViewById(R.id.doneGenericTaskList);
        }
    }
}
