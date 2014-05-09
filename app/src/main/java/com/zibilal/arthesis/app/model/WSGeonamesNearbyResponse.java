package com.zibilal.arthesis.app.model;

import java.util.List;

/**
 * Created by bmuhamm on 4/14/14.
 */
public class WSGeonamesNearbyResponse implements Response{

    private List<Geoname> geonames;

    public List<Geoname> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<Geoname> geonames) {
        this.geonames = geonames;
    }
}
