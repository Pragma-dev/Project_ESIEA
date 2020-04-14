package com.example.project_esiea;

import java.text.SimpleDateFormat;
import java.util.List;

public class RestCovidResponse {

        private String Global;
        private List<Countries> Countries;
        private String Date;

    public String getGlobal() {
        return Global;
    }

    public List<Countries> getCountries() {
        return Countries;
    }

    public String getDate() {
        return Date;
    }
}
