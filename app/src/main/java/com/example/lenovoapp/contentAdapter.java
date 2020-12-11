package com.example.lenovoapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class contentAdapter extends RecyclerView.Adapter<contentAdapter.RecyclerHolder> implements Filterable {
    private LayoutInflater inflater;
    private List<myModels> contacts;
    private List<myModels> contactListFiltered;
    private String stat;
    private Context activity;
    private  OnItemClickListener mlistener;
    public contentAdapter(List<myModels> contacts, Context context, OnItemClickListener listener){
        this.activity = context;
        this.inflater = LayoutInflater.from(context);
        this.mlistener = listener;
        this.contacts = contacts;
    }
    public interface OnItemClickListener{
        void onImageClick(View v, int position);
    }
    public void setOnitemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.customlenovo,parent,false);
        RecyclerHolder holder= new RecyclerHolder(view,mlistener);
        return holder;
    }
    int prevpos=0;
    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        try {
            int g = holder.getAdapterPosition();
            myModels contact = contacts.get(g);
            holder.brand.setText(contact.getBrand());
            Bitmap bitmap = BitmapFactory.decodeByteArray(contact.getImages(), 0, contact.getImages().length);
            holder.imageView.setImageBitmap(bitmap);
            if (position > prevpos) {
                AnimationUtils.animate(holder, true);
            } else {
                AnimationUtils.animate(holder, false);
            }
            prevpos = position;
        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    //create the holder class
    class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView brand;
        ImageView imageView;
        CardView card;
        public RecyclerHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView =  itemView.findViewById(R.id.image);
            brand = itemView.findViewById(R.id.brand);
            card = itemView.findViewById(R.id.card);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onImageClick(view, position);
                        }
                    }
                }
            });
        }
    }

    public void setFilter(ArrayList<myModels> newList){
        contacts = new ArrayList<>();
        contacts.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    contactListFiltered = contacts;
                } else {
                    List<myModels> filteredList = new ArrayList<>();
                    for (myModels row : contacts) {
                        String name_ = row.getBrand().toLowerCase();
                        String depart_ = row.getDescription().toLowerCase();
                        if(name_.contains(charString) || depart_.contains(charString)){
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<myModels>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
