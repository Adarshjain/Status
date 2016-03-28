package com.example.geekyadarsh.stats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.text.DateFormat;
import java.util.Date;

public class AddStats extends AppCompatActivity {

    private Firebase myRef;
    private EditText StatText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stats);

        Firebase.setAndroidContext(this);
        myRef = new Firebase("https://status101.firebaseio.com");

    }

    @Override
    protected void onStart() {
        super.onStart();
        StatText = (EditText) findViewById(R.id.stats);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.update:
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                String Text = StatText.getText().toString().trim();
                if (!Text.equals("")) {
                    myRef.child(currentDateTimeString).setValue(Text);
                    Intent back = new Intent(AddStats.this,DisplayStats.class);
                    startActivity(back);
                }
                StatText.setText("");
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}