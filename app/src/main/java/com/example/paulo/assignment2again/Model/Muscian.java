package com.example.paulo.assignment2again.Model;

import io.realm.RealmObject;



public class Muscian extends RealmObject {


    private String artistName;
    private String collectionName;
    private String artworkUrl60;
    private String trackPrice;
    private String previewUrl;
    private String type;

    public Muscian(){

    }

    public Muscian(String name, String mCollectionName, String image, String price, String previewUrl,String type){
        this.artistName = name;
        this.collectionName = mCollectionName;
        this.artworkUrl60 = image;
        this.trackPrice = price;
        this.previewUrl = previewUrl;
        this.type = type;

    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {this.trackPrice = trackPrice;}

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
