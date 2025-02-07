package com.example.eventapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventapp.models.PopularModel;
import com.example.eventapp.R;
import com.example.eventapp.models.UpcomingModel;

import java.util.List;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {

    private Context context;
    private List<UpcomingModel> upcomingItemList;

    public UpcomingAdapter(Context context, List<UpcomingModel> itemList) {
        this.context = context;
        this.upcomingItemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_upcoming, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpcomingModel item = upcomingItemList.get(position);
        holder.upcomingName.setText(item.getTitle());
        holder.upcomingImage.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return upcomingItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView upcomingImage;
        TextView upcomingName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            upcomingImage = itemView.findViewById(R.id.upcoming_image);
            upcomingName = itemView.findViewById(R.id.upcoming_text);
        }
    }
}

