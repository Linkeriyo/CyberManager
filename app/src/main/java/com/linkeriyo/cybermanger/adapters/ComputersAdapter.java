package com.linkeriyo.cybermanger.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.models.Computer;

import java.util.List;

public class ComputersAdapter extends RecyclerView.Adapter<ComputersAdapter.ComputerViewHolder> {

    List<Computer> computers;

    public ComputersAdapter(MutableLiveData<List<Computer>> computers) {
        this.computers = computers.getValue();
    }

    @NonNull
    @Override
    public ComputerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_computer,
                parent, false);
        return new ComputerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputerViewHolder holder, int position) {
        holder.tvAlias2.setText(computers.get(position).getAlias());
    }

    @Override
    public int getItemCount() {
        return computers.size();
    }

    public static class ComputerViewHolder extends RecyclerView.ViewHolder {

        TextView tvAlias2;

        public ComputerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlias2 = itemView.findViewById(R.id.tv_alias_2);
        }
    }
}
