package com.example.project_esiea.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_esiea.R;
import com.example.project_esiea.data.CovidApi;
import com.example.project_esiea.presentation.model.Countries;
import com.example.project_esiea.presentation.model.Global;
import com.example.project_esiea.presentation.model.RestCovidResponse;
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
import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.covid19api.com";

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;

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

        sharedPreferences = getSharedPreferences("covid_application", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        NetworkInfo network = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if(network==null || !network.isConnected()){
            //device not connected to internet
            Toast.makeText(getApplicationContext(), "Not connected to internet - outdated date", Toast.LENGTH_SHORT).show();

            List<Countries> countriesList = getListFromCache();
            Global Global = getGlobalFromCache();
            String Date  = getDateFromCache();

            showList(countriesList);
            showGlobal(Global);
            try {
                showDate(Date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else {
            makeApiCall();
            Toast.makeText(getApplicationContext(), "Connected to internet - data updated", Toast.LENGTH_SHORT).show();

        }
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

    private void showDate(String Date) throws ParseException {

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        java.util.Date date = dt.parse(Date);
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        text1 = (TextView)findViewById(R.id.Dateid);
        text1.setText("Last update : "+dt1.format(date));
    }

    private void makeApiCall(){

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

                    saveList(Countries);
                    saveGlobal(Global);
                    saveDate(Date);

                    showList(Countries);
                    showGlobal(Global);

                    try {
                        showDate(Date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
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

    private void showError(){
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.countries_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
