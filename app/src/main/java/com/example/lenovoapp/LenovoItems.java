package com.example.lenovoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LenovoItems extends AppCompatActivity implements  SearchView.OnQueryTextListener {
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<myModels> contentData, newRecord;
    RecyclerView recyclerView;
    AlertDialog.Builder builder;
    private contentAdapter contentAdapter;
    SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenovo_items);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle("Lenovo Products");
        }

        contentData = new ArrayList<>();
        newRecord = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);


        search = findViewById(R.id.searchData);
        search.setOnQueryTextListener(this);

        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        },1000);
    }

    public void loadData(){
        contentData = dbColumnList.allLenovo;
        newRecord = contentData;
        contentAdapter = new contentAdapter( newRecord, getApplicationContext(), new contentAdapter.OnItemClickListener() {

            @Override
            public void onImageClick(View v, int position) {
                Intent intent = new Intent(getApplication(), ViewBrand.class);
                dbColumnList.itemIdClick = position;
                dbColumnList.allLenovo = newRecord;
                startActivity(intent);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }


        });
        contentAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(contentAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String textData) {
        ArrayList<myModels> newList = new ArrayList<>();
        newRecord = new ArrayList<>();
        String newText = textData.toLowerCase();
        for(myModels cont : contentData){
            String searchVal = cont.getBrand().toLowerCase() + cont.getDescription().toLowerCase();
            if(searchVal.contains(newText)){
                newList.add(cont);
            }
        }
        if(!textData.isEmpty()){
            newRecord = newList;
        }else{
            newRecord = contentData;
        }
        contentAdapter.setFilter(newList);
        contentAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(contentAdapter);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();;
        switch (id){
            case R.id.about:
                displayMessage("Lenovo Products Mobile App. \n Designed By Abdulraheem Sherif Adavuruku - APT223311112");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void displayMessage(String msg) {
        builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setCancelable(false);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        alert.show();
    }
}