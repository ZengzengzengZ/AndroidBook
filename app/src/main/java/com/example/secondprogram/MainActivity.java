package com.example.secondprogram;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;

public class MainActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "MainActivity";
    EditText rmb;
    TextView result;
    float dollarRate = 0f;
    float euroRate = 0f;
    float wonRate = 0f;

    Handler handler;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rmb = findViewById(R.id.input);
        result = findViewById(R.id.result);

        //创建新的sharedpreference
        sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);
        String updateStr = sharedPreferences.getString("update_str", "");

        Log.i(TAG, "onCreate: dollar=" + dollarRate);
        Log.i(TAG, "onCreate: updateStr=" + updateStr);

        //获取当前日期
        LocalDate today = LocalDate.now();
        //比较日期
        if (updateStr.equals(today.toString())) {
            Log.i(TAG, "onCreate: 日期相等，不从网络中获取数据");
        } else {
            //开启线程
            Thread t = new Thread(this);
            t.start();
        }

        //创建Handler对象
        //创建Handler对象
        handler = new Handler() {
            @Override
            public void handleMessage (@NonNull Message msg){
                if (msg.what == 7) {
                    String val = (String) msg.obj;
                    Log.i(TAG, "handleMessage: val=" + val);
                    Toast.makeText(MainActivity.this,"数据已更新",Toast.LENGTH_SHORT).show();
                    //result.setText(val);
                    //保存获取的汇率数据
                    SharedPreferences sp=getSharedPreferences("myRate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor esitor=sp.edit();
                    editor.putFloat("dollar_rate",dollarRate);
                    editor.putFloat("won_rate",wonRate);
                    editor.putFloat("euro_rate",euroRate);
                    editor.apply();
                }
                super.handleMessage(msg);
            }
        };
    }

    public void Click(View btn) {

        float r = 0.0f;
        if (btn.getId() == R.id.btn1) {
            r = dollarRate;
        } else if (btn.getId() == R.id.btn2) {
            r = euroRate;
        } else {
            r = wonRate;
        }

        //提醒用户输入
        String str = rmb.getText().toString();
        if (str != null && str.length() > 0) {
            float inputvalue = Float.parseFloat(rmb.getText().toString());
            r = r * inputvalue;
            result.setText(String.format("%.2f", r));
        } else {
            Toast.makeText(this, "请输入人民币数值", Toast.LENGTH_LONG).show();
        }
    }

    public void openConfig(View btn) {
        Log.i(TAG, "openConfig：");
        //打开新的窗口
        Intent config = new Intent(this, ConfigActivity.class);
        config.putExtra("DOLLAR", dollarRate);
        config.putExtra("EURO", euroRate);
        config.putExtra("WON", wonRate);

        Log.i(TAG, "openConfig: DOLLAR=" + dollarRate);
        Log.i(TAG, "openConfig: EURO=" + euroRate);
        Log.i(TAG, "openConfig: WON=" + wonRate);

        //startActivity(config);
        startActivityForResult(config, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar", 0.0f);
            euroRate = bundle.getFloat("key_euro", 0.0f);
            wonRate = bundle.getFloat("key_won", 0.0f);
        }
    }


    public void run() {
        Log.i(TAG, "run:...");
        //获取网络数据
        URL url = null;

//        try{
//
//        }
//       catch(MalformedURLException e){
        //   e.printStackTrace()
        //  Log.e(TAG, "run: ex="+e.toString());
//        }
//        catch(IOException e){
//            e.printStackTrace();
//            Log.e(TAG, "run: ex="+e.toString());
//        }
//            Log.i(TAG, "run:after 3s");

        Document doc = null;
        Bundle bd = new Bundle();
        try {
            doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: " + doc.title());
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for (Element tr: trs  ) {
                Elements tds=tr.getElementsByTag("td");
                if(tds.size()>0){
                    String str=tds.get(0).text();
                    String val=tds.get(5).text();
                    if("美元".equals(str)){
                        dollarRate=100/Float.parseFloat(val);
                    }
                    else if("韩元".equals(str)){
                        wonRate=100/Float.parseFloat(val);
                    }
                    else if("欧元".equals(str)){
                        euroRate=100/Float.parseFloat(val);
                    }
                    Log.i(TAG, "run: td1="+tds.get(0).text()+"=>"+tds.get(5).text());
                }
            }

            Element ee=doc.select("body > section > div > div > article > table > tbody > tr:nth-child(27) > td:nth-child(6)").first();
            Log.i(TAG, "run: 美元="+ee.text());
            Element d2=table.selectFirst("tbody > tr:nth-child(8) > td:nth-child(6)");
            Log.i(TAG, "run: 韩元="+d2.text());
            Element d3=table.selectFirst("tbody > tr:nth-child(14) > td:nth-child(6)");
            Log.i(TAG, "run: 欧元="+d3.text());
        }catch(IOException e){
            e.printStackTrace();
        }
        //返回消息到主线程
        Message msg=handler.obtainMessage(7);
        //msg.what=7;
        msg.obj="Hello from run()";
        handler.sendMessage(msg);
    }




    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        while(true){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0) break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}


