package com.example.eventapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.EventViewActivity;
import com.example.eventapp.R;
import com.example.eventapp.models.MusicModel;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private Context context;
    private List<MusicModel> itemList;

    // Constructor
    public MusicAdapter(Context context, List<MusicModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MusicModel item = itemList.get(position);

        holder.itemName.setText(item.getTitle());
        holder.itemImage.setImageResource(item.getImageResId());


        holder.eventcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent eventIntent = new Intent(context, EventViewActivity.class);
                eventIntent.putExtra("eventTitle", item.getTitle());
                context.startActivity(eventIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // ViewHolder class to hold references to each item's views
    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout eventcard;

        TextView itemName;
        ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_text);
            itemImage = itemView.findViewById(R.id.item_image);
            eventcard = itemView.findViewById(R.id.eventcard);
        }
    }
}
