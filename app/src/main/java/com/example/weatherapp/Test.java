package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;

public class Test {
    @SerializedName("main")
    Main main;

//    Wind wind;

    Clouds clouds;

//    Weather weather;





    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }




}
