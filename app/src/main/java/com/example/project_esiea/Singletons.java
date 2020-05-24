package com.example.project_esiea;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.project_esiea.data.CovidApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static CovidApi covidApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static CovidApi getCovidApi(){
        if(covidApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            covidApiInstance = retrofit.create(CovidApi.class);
        }

        return covidApiInstance;
    }

    public static SharedPreferences getsharedPreferences(Context context){
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("covid_application", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
