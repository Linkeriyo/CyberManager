package com.linkeriyo.cybermanger.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.dialogs.PaymentDialog;
import com.linkeriyo.cybermanger.fragments.payment.PaymentMethodsFragment;
import com.linkeriyo.cybermanger.models.CreditCard;
import com.linkeriyo.cybermanger.requests.PaymentsRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

import java.util.ArrayList;

public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodViewHolder> {

    PaymentMethodsFragment fragment;
    ArrayList<CreditCard> methods;

    public PaymentMethodsAdapter(PaymentMethodsFragment fragment, ArrayList<CreditCard> methods) {
        this.fragment = fragment;
        this.methods = methods;
    }

    @NonNull
    @Override
    public PaymentMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_payment_method,
                parent, false);
        return new PaymentMethodsAdapter.PaymentMethodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodViewHolder holder, int position) {
        String cardNumber = methods.get(position).getCardNumber();
        String last4Digits = cardNumber.substring(11, 15);
        holder.tvDescription.setText(fragment.getString(R.string.credit_card, getCardTypeByNumber(cardNumber), last4Digits));

        holder.ibRemove.setOnClickListener(v -> {
            PaymentsRequests.removeCard(fragment, Preferences.getToken(), methods.get(position));
        });

        holder.clMethod.setOnClickListener(v -> {
            new PaymentDialog(fragment.getContext()).show();
        });
    }

    @Override
    public int getItemCount() {
        return methods.size();
    }

    public static class PaymentMethodViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescription;
        ConstraintLayout clMethod;
        ImageButton ibRemove;

        public PaymentMethodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tv_description);
            clMethod = itemView.findViewById(R.id.cl_method);
            ibRemove = itemView.findViewById(R.id.ib_remove);
        }
    }

    public String getCardTypeByNumber(String number) {
        if (number.matches("^4[0-9]{12}(?:[0-9]{3})?$"))
            return "VISA";
        if (number.matches("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$"))
            return "MasterCard";
        if (number.matches("^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$"))
            return "Visa Master Card";
        if (number.matches("^(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}$"))
            return "Maestro Card";
        if (number.matches("^3[47][0-9]{13}$"))
            return "American Express";
        return fragment.getString(R.string.unknown_card);
    }
}
