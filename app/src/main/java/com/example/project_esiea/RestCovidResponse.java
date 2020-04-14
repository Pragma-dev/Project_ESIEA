package com.example.project_esiea;

import java.text.SimpleDateFormat;
import java.util.List;

public class RestCovidResponse {

        Global Global = new Global();
        private List<Countries> Countries;
        private String Date;

    public Global getGlobal() {
        return Global;
    }

    public List<com.example.project_esiea.Countries> getCountries() {
        return Countries;
    }

    public String getDate() {
        return Date;
    }
}
