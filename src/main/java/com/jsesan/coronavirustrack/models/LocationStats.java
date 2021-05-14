package com.jsesan.coronavirustrack.models;

import java.util.Objects;

public class LocationStats {
    private String country;

    private int latestTotalCases;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

    public int compareTo(LocationStats locationStat){
        return Integer.compare(locationStat.getLatestTotalCases(),getLatestTotalCases());
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "country='" + country + '\'' +
                ", latestTotalCases=" + latestTotalCases +
                '}';
    }
}
