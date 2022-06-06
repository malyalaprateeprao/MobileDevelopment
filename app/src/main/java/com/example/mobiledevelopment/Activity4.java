package com.example.mobiledevelopment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Activity4 extends AppCompatActivity implements dialog_activity.LinkDialogListener{

    ListView l;
    public ArrayList<String> listname = new ArrayList<>();
    public ArrayList<String> listlink = new ArrayList<>();

    Snackbar bar;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);


        l = findViewById(R.id.list);
        view = findViewById(R.id.list);
        FloatingActionButton fab_button = findViewById(R.id.fab);

        ArrayAdapter<String> arr;
        arr = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listname);
        l.setAdapter(arr);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent openLink = new Intent(Intent.ACTION_VIEW, Uri.parse(listlink.get(position)));
                startActivity(openLink);
            }
        });

        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        registerForContextMenu(l);


        /*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openDialog();

                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        getMenuInflater().inflate(R.menu.link_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.option1:
                Log.d("creation", "chill");
                Toast.makeText(Activity4.this, "Opens " + listlink.get(info.position).toString(), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void openDialog() {
        dialog_activity linkDialog = new dialog_activity();
        linkDialog.show(getSupportFragmentManager(), "link dialog");
    }

    @Override
    public void applyTexts(String name, String link) {

        //link = getString(R.string.messageWithLink);
        listname.add(name);
        listlink.add(link);
        bar = Snackbar.make(view, "Link added Successfully", 3000);
        bar.show();

        //callme();
    }

    public void callme(){
        l = findViewById(R.id.list);
        ArrayAdapter<String> arr;
        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listname);
        l.setAdapter(arr);
    }
}