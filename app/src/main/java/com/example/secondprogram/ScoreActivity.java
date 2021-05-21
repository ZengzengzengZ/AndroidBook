package com.example.secondprogram;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button pointA3=(Button) findViewById(R.id.buttonA3);
        pointA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pointA = (TextView) findViewById(R.id.pointA);
                int pointa = Integer.parseInt(pointA.getText().toString());
                pointa +=3;
                pointA.setText(String.valueOf(pointa));
            }
        });

        Button pointA2=(Button) findViewById(R.id.buttonA2);
        pointA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pointA = (TextView) findViewById(R.id.pointA);
                int pointa = Integer.parseInt(pointA.getText().toString());
                pointa +=2;
                pointA.setText(String.valueOf(pointa));
            }
        });

        Button pointA1=(Button) findViewById(R.id.buttonA1);
        pointA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pointA = (TextView) findViewById(R.id.pointA);
                int pointa = Integer.parseInt(pointA.getText().toString());
                pointa +=1;
                pointA.setText(String.valueOf(pointa));
            }
        });

        Button pointB3=(Button) findViewById(R.id.buttonB3);
        pointB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pointB = (TextView) findViewById(R.id.pointB);
                int pointb = Integer.parseInt(pointB.getText().toString());
                pointb +=3;
                pointB.setText(String.valueOf(pointb));
            }
        });

        Button pointB2=(Button) findViewById(R.id.buttonB2);
        pointB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pointB = (TextView) findViewById(R.id.pointB);
                int pointb = Integer.parseInt(pointB.getText().toString());
                pointb +=2;
                pointB.setText(String.valueOf(pointb));
            }
        });

        Button pointB1=(Button) findViewById(R.id.buttonB1);
        pointB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pointB = (TextView) findViewById(R.id.pointB);
                int pointb = Integer.parseInt(pointB.getText().toString());
                pointb +=1;
                pointB.setText(String.valueOf(pointb));
            }
        });

        Button reset=(Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pointA = (TextView) findViewById(R.id.pointA);
                TextView pointB = (TextView) findViewById(R.id.pointB);
                pointA.setText("0");
                pointB.setText("0");
            }
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = ((TextView)findViewById(R.id.pointA)).getText().toString();
        String scoreb = ((TextView)findViewById(R.id.pointB)).getText().toString();
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score");
        String scoreb = savedInstanceState.getString("teamb_score");
        ((TextView)findViewById(R.id.pointA)).setText(scorea);
        ((TextView)findViewById(R.id.pointB)).setText(scoreb);
    }
}