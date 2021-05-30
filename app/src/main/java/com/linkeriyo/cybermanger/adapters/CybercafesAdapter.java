package com.linkeriyo.cybermanger.adapters;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.SelectCafeActivity;
import com.linkeriyo.cybermanger.databinding.ElementComputerBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class CybercafesAdapter extends RecyclerView.Adapter<CybercafesAdapter.CybercafeViewHolder> {

    SelectCafeActivity activity;
    ArrayList<CyberCafe> cafes;

    public CybercafesAdapter(SelectCafeActivity activity, ArrayList<CyberCafe> cafes) {
        this.activity = activity;
        this.cafes = cafes;
    }

    @NonNull
    @Override
    public CybercafeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CybercafeViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.element_cybercafe,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull CybercafeViewHolder holder, int position) {
        int nightModeFlags = activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            Glide.with(activity)
                    .load(cafes.get(position).getImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_name)
                    .into(holder.civImage);
        } else {
            Glide.with(activity)
                    .load(cafes.get(position).getImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_action_name_night)
                    .into(holder.civImage);
        }

        holder.tvName.setText(cafes.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return cafes.size();
    }

    public static class CybercafeViewHolder extends RecyclerView.ViewHolder {

        CircularImageView civImage;
        TextView tvName;

        public CybercafeViewHolder(@NonNull View itemView) {
            super(itemView);
            civImage = itemView.findViewById(R.id.civ_image);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
