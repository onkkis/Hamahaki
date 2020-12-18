package com.example.hamahaki;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<ParseItem> parseItems;
    private Context context;

    public ListAdapter(ArrayList<ParseItem> parseItems, Context context) {
        this.parseItems = parseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_loading,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        ParseItem parseItem = parseItems.get(position);
        holder.textView.setText(parseItem.getTitle());
        holder.priceText.setText(parseItem.getPrice());
        if(parseItem.getImgUrl().isEmpty() != true) {
            Picasso.get().load(parseItem.getImgUrl()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView priceText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.listText);
            priceText = itemView.findViewById(R.id.priceText);
        }
    }

    public void resetValues(ArrayList<ParseItem> parseItems){
        this.parseItems = parseItems;
        notifyDataSetChanged();
    }
}
