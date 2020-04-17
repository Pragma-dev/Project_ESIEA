package com.example.project_esiea;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.covid19api.com";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView text1 = null;
    TextView text2 = null;
    TextView text3 = null;
    TextView text4 = null;
    TextView text5 = null;
    TextView text6 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeApiCall();
    }

    private void showList(List<Countries> Countries) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(Countries);
        recyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void showGlobal(Global Global) {
        text1 = (TextView)findViewById(R.id.Line1);
        text2 = (TextView)findViewById(R.id.Line2);
        text3 = (TextView)findViewById(R.id.Line3);
        text4 = (TextView)findViewById(R.id.Line4);
        text5 = (TextView)findViewById(R.id.Line5);
        text6 = (TextView)findViewById(R.id.Line6);

        text1.setText("New cases today : "+Global.getNewConfirmed());
        text2.setText("Total cases : "+Global.getTotalConfirmed());
        text3.setText("New deaths today : "+Global.getNewDeaths());
        text4.setText("Total deaths : "+Global.getTotalDeaths());
        text5.setText("New recovered today : "+Global.getNewRecovered());
        text6.setText("Total recovered : "+Global.getTotalRecovered());

    }

    private void showDate(String Date){
        text1 = (TextView)findViewById(R.id.Dateid);

        text1.setText("COVID-19 Data - "+Date);

    }

    private void makeApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
                    showList(Countries);
                    showGlobal(Global);
                    showDate(Date);
                    Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestCovidResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError(){
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
