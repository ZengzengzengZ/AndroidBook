package com.example.secondprogram;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyTask implements Runnable {

    private static final String TAG ="MyTask";
    Handler handler;

    public void setHandler(Handler handler){
        this.handler = handler;
    }
    @Override
    public  void  run() {
        Log.i(TAG, "run:....");
        //耗时操作
        //获取网络数据
        //Bundle bdl = new Bundle();
        List<String> ret = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "title" + doc.title());
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                if (tds.size() > 0) {
                    String str = tds.get(0).text();
                    String val = tds.get(5).text();
                    Log.i(TAG, "run:td1=" + str + "=>" + val);
                    ret.add(str + "=>" + val);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回信息到主线程
        Message msg = handler.obtainMessage(9, ret);
        handler.sendMessage(msg);
    }
}