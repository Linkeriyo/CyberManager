package com.linkeriyo.cybermanger.requests;

import android.util.Log;

import com.linkeriyo.cybermanger.dialogs.PaymentDialog;
import com.linkeriyo.cybermanger.fragments.payment.NewCardFragment;
import com.linkeriyo.cybermanger.fragments.payment.PaymentMethodsFragment;
import com.linkeriyo.cybermanger.models.CreditCard;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentsRequests {

    final static String TAG = "PaymentsRequests";

    public static void getMethodsByUser(PaymentMethodsFragment fragment, final String token) {
        Call<String> call = RetrofitClient.getClient()
                .create(PaymentsService.class)
                .getMethodsByUser(token);
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "getMethodsByUser response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        ArrayList<CreditCard> methods = new ArrayList<>();
                        
                        JSONArray jsonMethods = jsonResponse.getJSONArray(Tags.CARD_LIST);
                        System.out.println(jsonMethods);

                        for (int i = 0; i < jsonMethods.length(); i++) {
                            methods.add(new CreditCard(jsonMethods.getJSONObject(i)));
                        }
                        fragment.setMethods(methods);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "getMethodsByUser failure");
            }
        });
    }

    public static void registerNewCard(NewCardFragment fragment, final String token, final CreditCard creditCard) {
        Call<String> call = RetrofitClient.getClient()
                .create(PaymentsService.class)
                .registerNewCard(JSONTemplates.createNewCardJSON(token, creditCard));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "registerNewCard response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        fragment.getParentFragmentManager().popBackStack();
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "registerNewCard failure");
            }
        });
    }

    public static void removeCard(PaymentMethodsFragment fragment, String token, CreditCard creditCard) {
        Call<String> call = RetrofitClient.getClient()
                .create(PaymentsService.class)
                .removeCard(JSONTemplates.createRemoveCardJSON(token, creditCard));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "removeCard response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        getMethodsByUser(fragment, token);
                    }

                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "removeCard failure");
            }
        });
    }

    public static void getCybergold(PaymentDialog dialog, String token, String businessId, int quantity) {
        Call<String> call = RetrofitClient.getClient()
                .create(PaymentsService.class)
                .getCybergold(JSONTemplates.createGetCybergoldJSON(token, businessId, quantity));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "getCybergold response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        Preferences.setBalance(jsonResponse.getInt(Tags.BALANCE));
                        dialog.balanceAdded();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "getCybergold failure");
            }
        });
    }
}
