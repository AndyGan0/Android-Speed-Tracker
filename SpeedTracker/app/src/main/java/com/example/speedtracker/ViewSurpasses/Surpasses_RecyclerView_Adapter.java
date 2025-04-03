package com.example.speedtracker.ViewSurpasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speedtracker.R;
import com.example.speedtracker.Classes.Surpasses;

import java.util.ArrayList;

public class Surpasses_RecyclerView_Adapter extends RecyclerView.Adapter<Surpasses_RecyclerView_Adapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Surpasses> surpasses;

    public Surpasses_RecyclerView_Adapter (Context context, ArrayList<Surpasses> surpasses, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.surpasses = surpasses;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Surpasses_RecyclerView_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.surpasses_recycler_view_row , parent , false);
        return new Surpasses_RecyclerView_Adapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Surpasses_RecyclerView_Adapter.MyViewHolder holder, int position) {
        holder.textView3.setText(surpasses.get(position).getDatetime());
        holder.textView4.setText(surpasses.get(position).getSpeed().toString());
    }

    @Override
    public int getItemCount() {
        return surpasses.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView3, textView4;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if ( pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }

                    }

                }
            });

        }
    }





}
