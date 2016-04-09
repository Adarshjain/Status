package com.example.geekyadarsh.stats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DisplayStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stats);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent next = new Intent(getApplicationContext(),AddStats.class);
                    startActivity(next);
                }
            });
        }
        Firebase.setAndroidContext(this);
        final Firebase myReference = new Firebase("https://status101.firebaseio.com");


        myReference.addValueEventListener(new ValueEventListener() {

            LinearLayout layout1 = (LinearLayout) findViewById(R.id.left);
            LinearLayout layout2 = (LinearLayout) findViewById(R.id.right);
            ArrayList<String> times = new ArrayList<>();
            ArrayList<String> contents = new ArrayList<>();
            ArrayList<String> titles = new ArrayList<>();
            Boolean pos = true;

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                times.clear();
                contents.clear();
                titles.clear();
                layout1.removeAllViews();
                layout2.removeAllViews();
                int j = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    times.add(postSnapshot.getKey());
                    Boolean turn = true;
                    for (DataSnapshot ps1 : postSnapshot.getChildren()) {
                        if (turn) {
                            contents.add(ps1.getValue().toString());
                        }
                        else {
                            titles.add(ps1.getValue().toString());
                        }
                        turn=!turn;
                    }
                    j++;
                }
                for (int i = 0;i < times.size();i++) {
                    TextView TitleTV = new TextView(DisplayStats.this);
                    TextView ContentTV = new TextView(DisplayStats.this);
                    final String content = contents.get(i);
                    final String title = titles.get(i);
                    final String time = times.get(i);
                    TitleTV.setHeight(0);
                    ContentTV.setHeight(80);
                    if (!Objects.equals(title, "")){
                        TitleTV.setText(title);
                        TitleTV.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        TitleTV.setTextSize(18);
                        TitleTV.setHeight(80);
                        TitleTV.setTypeface(null, Typeface.BOLD);
                    }
                    if (!Objects.equals(content, "")) {
                        ContentTV.setText(content);
                        ContentTV.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        ContentTV.setTextSize(14);
                        ContentTV.setMaxHeight(700);
                        ContentTV.setMinHeight(80);
                    }
                    CardView card = new CardView(new ContextThemeWrapper(DisplayStats.this, R.style.CardViewStyle), null, 0);
                    LinearLayout cardInner = new LinearLayout(new ContextThemeWrapper(DisplayStats.this, R.style.Widget_CardContent));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 8, 12, 8);
                    card.setLayoutParams(params);
                    cardInner.addView(TitleTV);
                    cardInner.addView(ContentTV);
                    card.addView(cardInner);
                    card.setElevation(5);
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle Data = new Bundle();
                            Data.putString("title", title);
                            Data.putString("content", content);
                            Data.putString("time", time);
                            Intent DNE = new Intent(getApplicationContext(), DispNdEdit.class);
                            DNE.putExtras(Data);
                            startActivity(DNE);
                        }
                    });
                    card.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            myReference.child(time).removeValue();
                                            break;
                                    }
                                }
                            };

                            AlertDialog.Builder builder = new AlertDialog.Builder(DisplayStats.this);
                            builder.setMessage("Confirm delete?").setPositiveButton("ok", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();
                            return true;
                        }
                    });
                    if (pos)
                        layout1.addView(card);
                    else
                        layout2.addView(card);
                    pos = !pos;
                }
                pos = true;
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(DisplayStats.this, "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                finish();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayStats.this);
                builder.setMessage("Do you want to exit?").setPositiveButton("ok", dialogClickListener).setNegativeButton("Cancel", dialogClickListener).show();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }

}


