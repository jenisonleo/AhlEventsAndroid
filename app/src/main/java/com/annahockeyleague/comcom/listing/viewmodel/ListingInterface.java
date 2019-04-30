package com.annahockeyleague.comcom.listing.viewmodel;

import com.google.gson.JsonArray;

public interface ListingInterface {

    public void onEventsLoaded(JsonArray data);
    public void onInfoloaded(JsonArray data);

}
