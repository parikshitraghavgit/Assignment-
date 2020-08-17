package com.parikshit.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextAge;
    private Button buttonDone;
    private RecyclerView recyclerView;
    private AdapterUser adapterUser;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        buttonDone = (Button) findViewById(R.id.buttonDone);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextName.getText().toString()) || TextUtils.isEmpty(editTextAge.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Enter Details", Toast.LENGTH_LONG).show();
                    return;
                }
                String name = editTextName.getText().toString().trim();
                int age = Integer.parseInt(editTextAge.getText().toString().trim());
                SaveData(name, age);

                editTextName.setText("");
                editTextAge.setText("");

            }
        });

    }


    public void SaveData(String name, int age) {


        databaseHelper = new DatabaseHelper(MainActivity.this);
        boolean bool = databaseHelper.insertData(name, age, 0);
        if (bool) {
            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        }

        ArrayList<String> strings = new ArrayList<>();


        Cursor res = databaseHelper.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No data to show", Toast.LENGTH_LONG).show();
            return;
        }

        while (res.moveToNext()) {
            StringBuffer buffer = new StringBuffer();
            String Age = Integer.toString(res.getInt(2));
            String Selected = Integer.toString(res.getInt(3));
            String Name = res.getString(1);

            buffer.append("Name is : " + Name + "\n");
            buffer.append("Age is :" + Age);
            String string = buffer.toString();
            strings.add(string);

        }

        adapterUser = new AdapterUser(strings, MainActivity.this);
        recyclerView.setAdapter(adapterUser);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.MenuDeleteButton) {
            deleteSelectedRows();
            return true;
        } else return super.onOptionsItemSelected(item);
    }


    public void deleteSelectedRows() {

        databaseHelper = new DatabaseHelper(MainActivity.this);
        ArrayList<String> strings = new ArrayList<>();

        Cursor res = databaseHelper.getAllData();
        while (res.moveToNext()) {
            int selected = res.getInt(3);
            if (selected == 1) {
                databaseHelper.deleteDATA(res.getInt(0));
            }
        }
        res = databaseHelper.getAllData();
        while (res.moveToNext()) {

            StringBuffer buffer = new StringBuffer();
            String Age = Integer.toString(res.getInt(2));
            String Selected = Integer.toString(res.getInt(3));
            String Name = res.getString(1);

            buffer.append("Name is : " + Name + "\n");
            buffer.append("Age is :" + Age);

            String string = buffer.toString();
            strings.add(string);
        }

        adapterUser = new AdapterUser(strings, MainActivity.this);
        recyclerView.setAdapter(adapterUser);


    }

}





