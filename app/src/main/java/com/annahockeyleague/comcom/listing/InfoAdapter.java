package com.annahockeyleague.comcom.listing;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.annahockeyleague.comcom.R;
import com.google.gson.JsonArray;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;


public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {


    public JsonArray data;
    private Random random=new Random();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd");
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");


    @NonNull
    @Override
    public InfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_holder, parent, false);
        return new InfoHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoHolder holder, int position) {
        holder.colorbar.setBackgroundColor(generateRandomColor(random));
        int index = (data.size() - 1) - position;
        String title=data.get(index).getAsJsonObject().get("title").getAsString();
        String description=data.get(index).getAsJsonObject().get("description").getAsString();
        long date=data.get(index).getAsJsonObject().get("createdAt").getAsLong();
        Timestamp t=new Timestamp(System.currentTimeMillis());
        String c1=simpleDateFormat.format(t);
        Timestamp createdAt = new Timestamp(date);
        String c2=simpleDateFormat.format(createdAt);
        if(c1.equals(c2)) {
            holder.dateContainer.setText(simpleDateFormat1.format(createdAt));
        }else {
            holder.dateContainer.setText(c2);
        }
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
        TextView dateContainer;
        View colorbar;
        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            titleContainer=itemView.findViewById(R.id.title_container);
            description_container=itemView.findViewById(R.id.description_container);
            dateContainer=itemView.findViewById(R.id.infodate_container);
            colorbar=itemView.findViewById(R.id.color_bar);
        }
    }
    public static int generateRandomColor(Random random) {
        // This is the base color which will be mixed with the generated one
        final int baseColor = Color.WHITE;

        final int baseRed = Color.red(baseColor);
        final int baseGreen = Color.green(baseColor);
        final int baseBlue = Color.blue(baseColor);

        final int red = (baseRed + random.nextInt(256)) / 2;
        final int green = (baseGreen + random.nextInt(256)) / 2;
        final int blue = (baseBlue + random.nextInt(256)) / 2;

        return Color.rgb(red, green, blue);
    }}
