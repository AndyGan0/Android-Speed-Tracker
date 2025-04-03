package com.example.speedtracker.ViewSurpasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.speedtracker.R;
import com.example.speedtracker.Classes.Surpasses;
import com.example.speedtracker.ViewSurpassActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewSurpassesActivity extends AppCompatActivity implements RecyclerViewInterface {

    Surpasses_RecyclerView_Adapter surpassesAdapter;
    ArrayList<Surpasses> SurpassesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_surpasses);

        SQLiteDatabase database = openOrCreateDatabase("Database.db", MODE_PRIVATE, null);
        Cursor cursor = database.rawQuery( "Select * from Surpasses" , null);
        while (cursor.moveToNext() ){

            SurpassesList.add( new Surpasses(cursor.getFloat(0),
                                             cursor.getFloat(1),
                                             cursor.getFloat(2),
                                             cursor.getString(3)) );

        }

        Collections.sort(SurpassesList , Comparator.comparing(Surpasses::getDatetime));
        Collections.reverse(SurpassesList);


        RecyclerView recyclerView = findViewById(R.id.recyclerViewSurpasses);
        surpassesAdapter = new Surpasses_RecyclerView_Adapter(this, SurpassesList , this);
        recyclerView.setAdapter(surpassesAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    public void sortNewest(View view){
        Collections.sort(SurpassesList , Comparator.comparing(Surpasses::getDatetime));
        Collections.reverse(SurpassesList);
        surpassesAdapter.notifyDataSetChanged();
    }

    public void sortHighest(View view){
        Collections.sort(SurpassesList , Comparator.comparing(Surpasses::getSpeed));
        Collections.reverse(SurpassesList);
        surpassesAdapter.notifyDataSetChanged();
    }

    public void sortOldest(View view){
        Collections.sort(SurpassesList , Comparator.comparing(Surpasses::getDatetime));
        surpassesAdapter.notifyDataSetChanged();
    }

    public void onItemClick(int position) {
        Surpasses item = SurpassesList.get(position);

        Intent intent = new Intent(this, ViewSurpassActivity.class);

        intent.putExtra("longtitude", item.getLongtitude());
        intent.putExtra("latitude", item.getLatitude());
        intent.putExtra("speed", item.getSpeed());
        intent.putExtra("datetime", item.getDatetime());

        startActivity(intent);

    }


}