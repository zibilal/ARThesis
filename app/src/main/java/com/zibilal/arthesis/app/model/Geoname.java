package com.zibilal.arthesis.app.model;

import com.zibilal.arthesis.app.location.Point;

/**
 * Created by bmuhamm on 4/14/14.
 */
public class Geoname {

    private String summary;
    private String distance;
    private int rank;
    private String title;
    private String wikipediaUrl;
    private int elavation;
    private String countryCode;
    private double lng;
    private double lat;
    private String lang;
    private Long geoNameId;

    private Point projection;

    public Point getProjection() {
        return projection;
    }

    public void setProjection(Point projection) {
        this.projection = projection;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public int getElavation() {
        return elavation;
    }

    public void setElavation(int elavation) {
        this.elavation = elavation;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Long getGeoNameId() {
        return geoNameId;
    }

    public void setGeoNameId(Long geoNameId) {
        this.geoNameId = geoNameId;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
