package com.example.project_esiea.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_esiea.R;
import com.example.project_esiea.Singletons;
import com.example.project_esiea.presentation.controller.MainController;
import com.example.project_esiea.presentation.model.Countries;
import com.example.project_esiea.presentation.model.Global;

import java.text.ParseException;
import java.util.List;

import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainController controller;

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

        controller = new MainController(
                this,
                Singletons.getGson(),
                Singletons.getsharedPreferences(getApplicationContext())
        );
        controller.onStart();

    }


    public void showList(List<Countries> Countries) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(Countries, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(com.example.project_esiea.presentation.model.Countries item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @SuppressLint("SetTextI18n")
    public void showGlobal(Global Global) {
        text1 = findViewById(R.id.Line1);
        text2 = findViewById(R.id.Line2);
        text3 = findViewById(R.id.Line3);
        text4 = findViewById(R.id.Line4);
        text5 = findViewById(R.id.Line5);
        text6 = findViewById(R.id.Line6);

        text1.setText("New cases today : "+Global.getNewConfirmed());
        text2.setText("Total cases : "+Global.getTotalConfirmed());
        text3.setText("New deaths today : "+Global.getNewDeaths());
        text4.setText("Total deaths : "+Global.getTotalDeaths());
        text5.setText("New recovered today : "+Global.getNewRecovered());
        text6.setText("Total recovered : "+Global.getTotalRecovered());

    }

    @SuppressLint("SetTextI18n")
    public void showDate(String Date) throws ParseException {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        java.util.Date date = dt.parse(Date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");

        text1 = findViewById(R.id.Dateid);
        if (date != null) {
            text1.setText("Last update : "+dt1.format(date));
        }
    }



    public void showError(){
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

    public void navigateToDetails(Countries countries) {
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
        myIntent.putExtra("countrieKey", Singletons.getGson().toJson(countries));
        MainActivity.this.startActivity(myIntent);

    }
}

