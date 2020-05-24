package com.example.project_esiea.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.project_esiea.Constants;
import com.example.project_esiea.data.CovidApi;
import com.example.project_esiea.presentation.model.Countries;
import com.example.project_esiea.presentation.model.Global;
import com.example.project_esiea.presentation.model.RestCovidResponse;
import com.example.project_esiea.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences) {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){

        NetworkInfo network = ((ConnectivityManager)view.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if(network==null || !network.isConnected()){
            //device not connected to internet
            Toast.makeText(view.getApplicationContext(), "Not connected to internet - outdated date", Toast.LENGTH_SHORT).show();

            List<Countries> countriesList = getListFromCache();
            Global Global = getGlobalFromCache();
            String Date  = getDateFromCache();

            view.showList(countriesList);
            view.showGlobal(Global);
            try {
                view.showDate(Date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else {
            makeApiCall();
            Toast.makeText(view.getApplicationContext(), "Connected to internet - data updated", Toast.LENGTH_SHORT).show();

        }
    }


    private void makeApiCall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CovidApi covidApi = retrofit.create(CovidApi.class);

        Call<RestCovidResponse> call = covidApi.getCovidResponse();
        call.enqueue(new Callback<RestCovidResponse>() {
            @Override
            public void onResponse(Call<RestCovidResponse> call, Response<RestCovidResponse> response) {
                if(response.isSuccessful() && response.body() != null){

                    List<Countries> Countries = response.body().getCountries();
                    Global Global = response.body().getGlobal();
                    String Date  = response.body().getDate();

                    saveList(Countries);
                    saveGlobal(Global);
                    saveDate(Date);

                    view.showList(Countries);
                    view.showGlobal(Global);

                    try {
                        view.showDate(Date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
                }else{
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestCovidResponse> call, Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Countries> countries) {
        String jsonString = gson.toJson(countries);
        sharedPreferences
                .edit()
                .putString("jsonCountriesList", jsonString)
                .apply();

        //Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
    }

    private void saveGlobal(Global Global) {
        String jsonString = gson.toJson(Global);
        sharedPreferences
                .edit()
                .putString("jsonGlobal", jsonString)
                .apply();

        //Toast.makeText(getApplicationContext(), "Global Saved", Toast.LENGTH_SHORT).show();
    }

    private void saveDate(String Date) {
        String jsonString = gson.toJson(Date);
        sharedPreferences
                .edit()
                .putString("jsonDate", jsonString)
                .apply();

        //Toast.makeText(getApplicationContext(), "Date Saved", Toast.LENGTH_SHORT).show();
    }

    private List<Countries> getListFromCache(){
        String countries = sharedPreferences.getString("jsonCountriesList", null);

        if(countries == null){
            return null;
        }else {
            Type listType = new TypeToken<List<Countries>>() {}.getType();
            return gson.fromJson(countries, listType);
        }
    }
    private Global getGlobalFromCache(){
        String jsonGlobal = sharedPreferences.getString("jsonGlobal", null);

        if(jsonGlobal == null){
            return null;
        }else {
            return gson.fromJson(jsonGlobal, Global.class);
        }
    }
    private String getDateFromCache(){
        String jsonDate = sharedPreferences.getString("jsonDate", null);

        if(jsonDate == null){
            return null;
        }else {
            return gson.fromJson(jsonDate, String.class);
        }
    }
}
