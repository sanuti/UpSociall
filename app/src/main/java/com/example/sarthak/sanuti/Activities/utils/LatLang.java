package com.example.sarthak.sanuti.Activities.utils;

/**
 * Created by SARTHAK on 3/20/2018.
 */

public class LatLang {
    String id,lati,longi;
    public LatLang()
    {

    }

    public LatLang(String id, String lati, String longi) {
        this.id = id;
        this.lati = lati;
        this.longi = longi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }
}
