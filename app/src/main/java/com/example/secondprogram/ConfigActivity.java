package com.example.secondprogram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigActivity extends AppCompatActivity {

    private static final String TAG="ConfigActivity";
    EditText dollar;
    EditText euro;
    EditText won;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent=getIntent();
        float dollar2=intent.getFloatExtra("DOLLAR",0.0f);
        float euro2=intent.getFloatExtra("EURO",0.0f);
        float won2=intent.getFloatExtra("WON",0.0f);

        Log.i(TAG,"oneCreate: dollar2="+dollar2);
        Log.i(TAG,"oneCreate: euro2="+euro2);
        Log.i(TAG,"oneCreate: won2="+won2);

        //获取控件对象
        dollar = findViewById(R.id.input_dollar);
        euro = findViewById(R.id.input_euro);
        won = findViewById(R.id.input_won);

        //放入控件中
        dollar.setText(String.valueOf(dollar2));
        euro.setText(String.valueOf(euro2));
        won.setText(String.valueOf(won2));
    }
    public void save(View btn){
        //获取新输入的数据
        float newDollar=Float.parseFloat(dollar.getText().toString());
        float newEuro=Float.parseFloat(euro.getText().toString());
        float newWon=Float.parseFloat(won.getText().toString());

        Log.i(TAG,"oneCreate: newDollar="+newDollar);
        Log.i(TAG,"oneCreate: newEuro="+newEuro);
        Log.i(TAG,"oneCreate: newWon="+newWon);

        //放入intent中
        Intent ret=getIntent();
        Bundle bdl=new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
        ret.putExtras(bdl);
        setResult(2,ret);

        finish();
    }

}