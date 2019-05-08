package com.annahockeyleague.comcom.listing.viewmodel;

import com.google.gson.JsonArray;

public interface ListingInterface {

    public void onEventsLoaded(JsonArray data);
    public void onEventsFailed(String message);
    public void onInfoloaded(JsonArray data);
    public void onInfoFailed(String message);
}
