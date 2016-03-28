package com.example.geekyadarsh.stats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Objects;

public class DispNdEdit extends AppCompatActivity {

    Firebase myRef;

    EditText Tv;
    String Key,Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_nd_edit);

        Firebase.setAndroidContext(this);
        myRef = new Firebase("https://status101.firebaseio.com");

        TextView TKey = (TextView) findViewById(R.id.time);
        Tv = (EditText) findViewById(R.id.editText);

        Bundle b = getIntent().getExtras();
        Value = b.getString("val");
        Key = b.getString("valKey");

        assert TKey != null;
        TKey.setText(Key);

        Tv.setText(Value);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            String UpdatedValue = Tv.getText().toString().trim();
            if (!UpdatedValue.equals(Value)) {
                if (!Objects.equals(UpdatedValue, ""))
                    myRef.child(Key).setValue(UpdatedValue);
                else
                    myRef.child(Key).removeValue();
                Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"No updates",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String UpdatedValue = Tv.getText().toString().trim();
        switch (item.getItemId()){
            case R.id.home:
                if (!UpdatedValue.equals(Value)) {
                    if (!Objects.equals(UpdatedValue, ""))
                        myRef.child(Key).setValue(UpdatedValue);
                    else
                        myRef.child(Key).removeValue();
                    Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    Toast.makeText(getApplicationContext(),"No updates",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.update:
                if (!UpdatedValue.equals(Value)) {
                    if (!Objects.equals(UpdatedValue, ""))
                        myRef.child(Key).setValue(UpdatedValue);
                    else
                        myRef.child(Key).removeValue();
                    Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                    Toast.makeText(getApplicationContext(),"No updates",Toast.LENGTH_SHORT).show();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
