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


public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {


    public JsonArray data;
    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_holder, parent, false);
        return new InfoHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder holder, int position) {
        int index = (data.size() - 1) - position;
        String title=data.get(index).getAsJsonObject().get("title").getAsString();
        String description=data.get(index).getAsJsonObject().get("description").getAsString();
        holder.titleContainer.setText(title);
        holder.description_container.setText(description);
    }

    @Override
    public int getItemCount() {
        if(data==null) {
            return 0;
        }else {
            return data.size();
        }
    }

    static class InfoHolder extends RecyclerView.ViewHolder{
        TextView titleContainer;
        TextView description_container;
        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            titleContainer=itemView.findViewById(R.id.title_container);
            description_container=itemView.findViewById(R.id.description_container);

        }
    }
}
