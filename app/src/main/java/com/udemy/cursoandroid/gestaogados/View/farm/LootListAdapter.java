package com.udemy.cursoandroid.gestaogados.View.farm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.ArrayList;
import java.util.List;

public class LootListAdapter extends RecyclerView.Adapter<LootListAdapter.MyViewHolder>
{
    private List<Loot> lootList;

    public LootListAdapter(List<Loot> lootList) {
        this.lootList = lootList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_adapter_loot, parent, false);

        return new MyViewHolder(lootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Loot lootItem = lootList.get(position);
        holder.id.setText(Integer.toString(position));
        holder.name.setText(lootItem.getName());
    }

    @Override
    public int getItemCount() {
        return lootList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView id;
        TextView name;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idLootList);
            name = itemView.findViewById(R.id.nameLootList);
        }
    }

    public List<Loot> getLootList()
    {
        return lootList;
    }
}
