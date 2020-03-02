package com.cookandroid.mydiary;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconWeather;
    private String title;
    private String date;

    public Drawable getIconWeather() {
        return iconWeather;
    }

    public void setIconWeather(Drawable iconWeather) {
        this.iconWeather = iconWeather;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
