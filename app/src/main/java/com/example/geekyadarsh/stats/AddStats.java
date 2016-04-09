package com.example.geekyadarsh.stats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddStats extends AppCompatActivity {

    private Firebase myReference;
    private EditText AddTitle,AddContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stats);

        Firebase.setAndroidContext(this);
        myReference = new Firebase("https://status101.firebaseio.com");

    }

    @Override
    protected void onStart() {
        super.onStart();
        AddTitle = (EditText) findViewById(R.id.AddTitle);
        AddContent = (EditText) findViewById(R.id.AddContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String NewTitle = AddTitle.getText().toString().trim();
        String NewContent = AddContent.getText().toString().trim();
        switch (item.getItemId()){
            case R.id.update:
                long Time= System.currentTimeMillis();
                String Timex = Long.toString(Time);
                if ((!Objects.equals(NewContent, "")) || (!Objects.equals(NewTitle, ""))) {
                    Map<String, Object> nickname = new HashMap<>();
                    nickname.put(Timex + "/content", NewContent);
                    nickname.put(Timex + "/title", NewTitle);
                    myReference.updateChildren(nickname);
                    Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();
                    Intent back = new Intent(AddStats.this,DisplayStats.class);
                    startActivity(back);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}