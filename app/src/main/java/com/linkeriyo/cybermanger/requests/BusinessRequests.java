package com.linkeriyo.cybermanger.requests;

import android.util.Log;
import android.widget.Toast;

import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.activities.SelectCafeActivity;
import com.linkeriyo.cybermanger.dialogs.QRScannedDialog;
import com.linkeriyo.cybermanger.dialogs.WriteIdDialog;
import com.linkeriyo.cybermanger.fragments.home.ComputersFragment;
import com.linkeriyo.cybermanger.fragments.home.HomeFragment;
import com.linkeriyo.cybermanger.models.Computer;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.models.Post;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessRequests {

    private static final String TAG = "BusinessRequests";

    public static void getBusinessIntoMainActivity(MainActivity activity, final String token, final String businessId) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .checkBusiness(JSONTemplates.createTokenAndBusinessIDJSON(token, businessId));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "checkBusiness response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        CyberCafe cyberCafe = new CyberCafe(jsonResponse.getJSONObject(Tags.BUSINESS));
                        cyberCafe.setBalance(jsonResponse.getInt(Tags.BALANCE));
                        Preferences.setSelectedCafeName(cyberCafe.getName());
                        Preferences.setSelectedCafe(cyberCafe.getBusinessId());
                        Preferences.setBalance(cyberCafe.getBalance());
                        activity.setSelectedCafe(cyberCafe);
                        activity.initLayout();
                    } else {
                        Preferences.removePreference(Tags.SELECTED_CAFE);
                        Preferences.removePreference(Tags.BALANCE);
                        Preferences.removePreference(Tags.SELECTED_CAFE_NAME);
                        activity.startSelectCafeActivity();
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    Preferences.removePreference(Tags.SELECTED_CAFE);
                    Preferences.removePreference(Tags.BALANCE);
                    Preferences.removePreference(Tags.SELECTED_CAFE_NAME);
                    activity.startSelectCafeActivity();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "checkBusiness failure");
            }
        });
    }

    public static void checkBusiness(QRScannedDialog dialog, final String token, final String businessId) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .checkBusiness(JSONTemplates.createTokenAndBusinessIDJSON(token, businessId));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "checkBusiness response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        CyberCafe cyberCafe = new CyberCafe(jsonResponse.getJSONObject(Tags.BUSINESS));
                        cyberCafe.setBalance(jsonResponse.getInt(Tags.BALANCE));
                        dialog.showCyberCafeIfMatched(cyberCafe);
                    } else {
                        dialog.showCyberCafeIfMatched(null);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    dialog.showCyberCafeIfMatched(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "checkBusiness failure");
                Toast.makeText(
                        dialog.getContext(),
                        "An error occurred when handling the server's response.",
                        Toast.LENGTH_SHORT
                ).show();
                dialog.dismiss();
            }
        });
    }

    public static void checkBusinessForWriting(WriteIdDialog dialog, final String token, final String businessId) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .checkBusiness(JSONTemplates.createTokenAndBusinessIDJSON(token, businessId));
        Log.v(TAG, call.request().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "checkBusiness response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        CyberCafe cyberCafe = new CyberCafe(jsonResponse.getJSONObject(Tags.BUSINESS));
                        cyberCafe.setBalance(jsonResponse.getInt(Tags.BALANCE));
                        dialog.showCyberCafeIfMatched(cyberCafe);
                    } else {
                        dialog.showCyberCafeIfMatched(null);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                    dialog.showCyberCafeIfMatched(null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "checkBusiness failure");
                Toast.makeText(
                        dialog.getContext(),
                        "An error occurred when handling the server's response.",
                        Toast.LENGTH_SHORT
                ).show();
                dialog.dismiss();
            }
        });
    }

    public static void getBusinessesByUser(SelectCafeActivity activity, final String token) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .getBusinessesByUser(token);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "getBusinessesByUser response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        ArrayList<CyberCafe> cafes = new ArrayList<>();

                        JSONArray jsonCafes = jsonResponse.getJSONArray(Tags.BUSINESS_LIST);
                        System.out.println(jsonCafes);

                        for (int i = 0; i < jsonCafes.length(); i++) {
                            cafes.add(new CyberCafe(jsonCafes.getJSONObject(i)));
                        }
                        activity.setCafes(cafes);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "getBusinessesByUser failure");
            }
        });
    }

    public static void getPostsByBusinessId(HomeFragment fragment, final String token, final String businessId) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .getPostsByBusinessId(JSONTemplates.createTokenAndBusinessIDJSON(token, businessId));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "getPostsByBusinessId response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        ArrayList<Post> posts = new ArrayList<>();

                        JSONArray jsonPosts = jsonResponse.getJSONArray(Tags.POST_LIST);
                        System.out.println(jsonPosts);

                        for (int i = 0; i < jsonPosts.length(); i++) {
                            posts.add(new Post(jsonPosts.getJSONObject(i)));
                        }
                        fragment.setPosts(posts);
                    }
                } catch (JSONException | ParseException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "getPostsByBusinessId  failure");
            }
        });
    }
    
    public static void getComputersByBusinessId(ComputersFragment fragment, final String token, final String businessId) {
        Call<String> call = RetrofitClient.getClient()
                .create(BusinessService.class)
                .getComputersByBusinessId(JSONTemplates.createTokenAndBusinessIDJSON(token, businessId));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Log.v(TAG, "getComputersByBusinessId response");
                    Log.v(TAG, response.body() + "");

                    JSONObject jsonResponse = new JSONObject(response.body());
                    if (jsonResponse.getString(Tags.RESULT).equals(Tags.OK)) {
                        ArrayList<Computer> computers = new ArrayList<>();

                        JSONArray jsonComputers = jsonResponse.getJSONArray(Tags.COMPUTER_LIST);
                        System.out.println(jsonComputers);

                        for (int i = 0; i < jsonComputers.length(); i++) {
                            computers.add(new Computer(jsonComputers.getJSONObject(i)));
                        }
                        fragment.setComputers(computers);
                    }
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v(TAG, "getComputersByBusinessId  failure");
            }
        });
    }
}
