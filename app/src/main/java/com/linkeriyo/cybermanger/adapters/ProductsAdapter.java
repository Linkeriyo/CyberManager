package com.linkeriyo.cybermanger.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.models.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    List<Product> products;

    public ProductsAdapter(MutableLiveData<List<Product>> products) {
        this.products = products.getValue();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_product,
                parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.tvAlias2.setText(products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tvAlias2;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlias2 = itemView.findViewById(R.id.tv_alias_2);
        }
    }
}
