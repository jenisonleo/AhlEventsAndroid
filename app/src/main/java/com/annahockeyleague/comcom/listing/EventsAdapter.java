package com.annahockeyleague.comcom.listing;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.annahockeyleague.comcom.R;
import com.google.gson.JsonArray;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder> {
    public JsonArray data;

    @NonNull
    @Override
    public EventsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_holder, parent, false);
        return new EventsHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsHolder holder, int position) {
        Log.e("onbind,", " ");
        int index = (data.size() - 1) - position;
        String title=data.get(index).getAsJsonObject().get("title").getAsString();
        String description=data.get(index).getAsJsonObject().get("description").getAsString();
        String venue=data.get(index).getAsJsonObject().get("place").getAsString();
        String from=data.get(index).getAsJsonObject().get("fromDate").getAsString();
        String to=data.get(index).getAsJsonObject().get("toDate").getAsString();

        holder.titleContainer.setText(title);
        holder.description_container.setText(description);
        holder.venue_container.setText(venue);
        holder.from_container.setText(from+ " to " +to);
    }

    @Override
    public int getItemCount() {
        if(data==null) {
            return 0;
        }else {
            return data.size();
        }
    }

    static class EventsHolder extends RecyclerView.ViewHolder{
        TextView titleContainer;
        TextView description_container;
        TextView venue_container;
        TextView from_container;

        public EventsHolder(@NonNull View itemView) {
            super(itemView);
            titleContainer=itemView.findViewById(R.id.title_container);
            description_container=itemView.findViewById(R.id.description_container);
            venue_container=itemView.findViewById(R.id.venue_container);
            from_container=itemView.findViewById(R.id.from_container);
        }
    }
}
