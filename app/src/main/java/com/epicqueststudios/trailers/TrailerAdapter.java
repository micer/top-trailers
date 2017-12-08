package com.epicqueststudios.trailers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Henry on 12/6/2017.
 */

class TrailerAdapter extends RecyclerView.Adapter {
    private final ArrayList<TrailerItem> items;

    public TrailerAdapter(ArrayList<TrailerItem> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TrailerViewHolder) holder).bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.list_item;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.imageView)
        ImageView imageView;


        public TrailerViewHolder(View view) {
            super(view);
        }

        public void bindData(TrailerItem trailerItem) {
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> MainActivity.onTrailerClickListener(trailerItem));
            textView.setText(trailerItem.getTitle());
            if (trailerItem.hasImageUrl())
                Glide.with(imageView.getContext()).load(trailerItem.createImageUrl()).into(imageView);
        }
    }
}
