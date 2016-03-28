package com.example.geekyadarsh.stats;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class DisplayStats extends AppCompatActivity {

    DatabaseHelper db;

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

        db = new DatabaseHelper(this);

        addThroughDB();

        Firebase.setAndroidContext(this);
        Firebase myRef = new Firebase("https://status101.firebaseio.com");


        myRef.addValueEventListener(new ValueEventListener() {

            LinearLayout layout1 = (LinearLayout) findViewById(R.id.left);
            LinearLayout layout2 = (LinearLayout) findViewById(R.id.right);
            Boolean pos = true;

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<String> val1 = new ArrayList<>();
                ArrayList<String> val2 = new ArrayList<>();
                layout1.removeAllViews();
                layout2.removeAllViews();
                int j = 0;
                db.dropDB();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    val1.add(j, postSnapshot.getValue().toString());
                    val2.add(j, postSnapshot.getKey());
                    j++;
                }
                for (int i = val1.size() - 1; i >= 0; i--) {
                    TextView valueTV = new TextView(DisplayStats.this);
                    final String valx = val1.get(i);
                    final String valKey = val2.get(i);
                    db.insert(valKey,valx);
                    valueTV.setText(valx);
                    valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    valueTV.setTextSize(18);
                    valueTV.setMaxHeight(700);
                    valueTV.setMinHeight(80);

                    CardView card = new CardView(new ContextThemeWrapper(DisplayStats.this, R.style.CardViewStyle), null, 0);
                    LinearLayout cardInner = new LinearLayout(new ContextThemeWrapper(DisplayStats.this, R.style.Widget_CardContent));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 8, 8, 8);
                    card.setLayoutParams(params);
                    cardInner.addView(valueTV);
                    card.addView(cardInner);
                    card.setElevation(10);
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle Data = new Bundle();
                            Data.putString("val",valx);
                            Data.putString("valKey",valKey);
                            Intent DNE = new Intent(getApplicationContext(),DispNdEdit.class);
                            DNE.putExtras(Data);
                            startActivity(DNE);
                        }
                    });
                    if (pos) {
                        layout1.addView(card);
                        pos = !pos;
                    } else {
                        layout2.addView(card);
                        pos = !pos;
                    }
                }
                pos = true;
                val1.clear();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(DisplayStats.this, "The read failed: " + firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addThroughDB() {
        Cursor res = db.getData();
        res.moveToFirst();

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.left);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.right);
        Boolean pos = true;

        layout1.removeAllViews();
        layout2.removeAllViews();

        while (!res.isAfterLast()){

            final String valKey = res.getString(res.getColumnIndex("time"));
            final String valx = res.getString(res.getColumnIndex("status"));
            TextView valueTV = new TextView(DisplayStats.this);
            valueTV.setText(valx);
            valueTV.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            valueTV.setTextSize(18);
            valueTV.setMaxHeight(700);
            valueTV.setMinHeight(80);

            CardView card = new CardView(new ContextThemeWrapper(DisplayStats.this, R.style.CardViewStyle), null, 0);
            LinearLayout cardInner = new LinearLayout(new ContextThemeWrapper(DisplayStats.this, R.style.Widget_CardContent));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 8, 8, 8);
            card.setLayoutParams(params);
            cardInner.addView(valueTV);
            card.addView(cardInner);
            card.setElevation(10);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle Data = new Bundle();
                    Data.putString("val", valx);
                    Data.putString("valKey", valKey);
                    Intent DNE = new Intent(getApplicationContext(), DispNdEdit.class);
                    DNE.putExtras(Data);
                    startActivity(DNE);
                }
            });
            if (pos) {
                layout1.addView(card);
                pos = false;
            } else {
                layout2.addView(card);
                pos = true;
            }
            res.moveToNext();
        }
    }
}


