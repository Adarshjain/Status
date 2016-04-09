package com.example.geekyadarsh.stats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Objects;

public class DispNdEdit extends AppCompatActivity {

    Firebase myReference;

    EditText TitleET,ContentET;
    TextView TimeTV;
    String Title,Content,Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_nd_edit);

        Firebase.setAndroidContext(this);
        myReference = new Firebase("https://status101.firebaseio.com");

        TitleET = (EditText) findViewById(R.id.ETTitle);
        ContentET = (EditText) findViewById(R.id.ETContent);
        TimeTV = (TextView) findViewById(R.id.TVTime);

        Bundle b = getIntent().getExtras();
        Title = b.getString("title");
        Content = b.getString("content");
        Time = b.getString("time");

        TitleET.setText(Title);
        TitleET.setTypeface(null, Typeface.BOLD);
        ContentET.setText(Content);
        TimeTV.setText(Time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String NewTitle = TitleET.getText().toString().trim();
        String NewContent = ContentET.getText().toString().trim();
        switch (item.getItemId()){
            case R.id.update:
                if ((!Objects.equals(NewContent, "")) || (!Objects.equals(NewTitle, ""))) {
                    myReference.child(Time).child("content").setValue(NewContent);
                    myReference.child(Time).child("title").setValue(NewTitle);
                    Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                }
                else {
                    myReference.child(Time).removeValue();
                    Toast.makeText(getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.delete:
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                myReference.child(Time).removeValue();
                                Intent back = new Intent(DispNdEdit.this,DisplayStats.class);
                                startActivity(back);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Confirm delete?").setPositiveButton("ok", dialogClickListener).setNegativeButton("cancel", dialogClickListener).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
