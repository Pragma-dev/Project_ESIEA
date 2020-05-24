package com.example.project_esiea.presentation.model;

import java.util.List;

public class RestCovidResponse {

        com.example.project_esiea.presentation.model.Global Global = new Global();
        private List<com.example.project_esiea.presentation.model.Countries> Countries;
        private String Date;

    public Global getGlobal() {
        return Global;
    }

    public List<com.example.project_esiea.presentation.model.Countries> getCountries() {
        return Countries;
    }

    public String getDate() {
        return Date;
    }
}
