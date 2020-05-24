package com.example.project_esiea.presentation.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_esiea.R;
import com.example.project_esiea.Singletons;
import com.example.project_esiea.presentation.controller.MainController;
import com.example.project_esiea.presentation.model.Countries;




public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private MainController controller;

    private TextView txtHeader = null;
    private TextView text1 = null;
    private TextView text2 = null;
    private TextView text3 = null;
    private TextView text4 = null;
    private TextView text5 = null;
    private TextView text6 = null;
    private TextView text7 = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtHeader = findViewById(R.id.Title);
        text1 = findViewById(R.id.Line1);
        text2 = findViewById(R.id.Line2);
        text3 = findViewById(R.id.Line3);
        text4 = findViewById(R.id.Line4);
        text5 = findViewById(R.id.Line5);
        text6 = findViewById(R.id.Line6);
        text7 = findViewById(R.id.Line7);


        Intent intent = getIntent();
        String countrieJson = intent.getStringExtra("countrieKey");
        Countries countries = Singletons.getGson().fromJson(countrieJson, Countries.class);
        showDetail(countries);
    }

    @SuppressLint("SetTextI18n")
    private void showDetail(Countries countries) {
        txtHeader.setText(countries.getCountry());
        text1.setText("New cases today : "+countries.getNewConfirmed());
        text2.setText("Total cases : "+countries.getTotalConfirmed());
        text3.setText("New deaths today : "+countries.getNewDeaths());
        text4.setText("Total deaths : "+countries.getTotalDeaths());
        text5.setText("New recovered today : "+countries.getNewRecovered());
        text6.setText("Total recovered : "+countries.getTotalRecovered());
        text7.setText(countries.getCountry()+" statistics");
    }


    public void showError(){
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}

