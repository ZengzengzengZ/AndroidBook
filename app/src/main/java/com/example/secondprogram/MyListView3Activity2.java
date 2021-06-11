package com.example.secondprogram;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MyListView3Activity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list_view32);
        ListView listview=findViewById(R.id.mylist3);
        ProgressBar progressBar=findViewById(R.id.progressBar);

        ArrayList<HashMap<String,String>> listItems =new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map=new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail","detail"+i);
            listItems.add(map);
        }

//        SimpleAdapter listItemAdapter=new SimpleAdapter(this,
//                listItems,//listItems数据源
//        R.layout.list_item,//ListItem布局实现
//        new String[]{"ItemTitle","ItemDetail"},
//        new int[]{R.id.itemTitle,R.id.itemDetail}
//        );
//        listview.setAdapter(listItemAdapter);
//        listview.setVisibility(View.VISIBLE);

        listview.setOnItemClickListener(this);
        //获取网络数据
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                if(msg.what==11){
                    ArrayList<Rate> listItems = (ArrayList<Rate>) msg.obj;
                    RateAdapter adapter = new RateAdapter(MyListView3Activity2.this,
                            android.R.layout.simple_list_item_1,listItems);
                    listview.setAdapter(adapter);
                    //显示隐藏
                    listview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

//                    SimpleAdapter listItemAdapter=new SimpleAdapter(MyListView3Activity2.this,
//                            listItems,//listItems数据源
//                            R.layout.list_item,//ListItem布局实现
//                            new String[]{"ItemTitle","ItemDetail"},
//                            new int[]{R.id.itemTitle,R.id.itemDetail}
//                    );
                }
                super.handleMessage(msg);
            }
        };

//        MyTask task = new MyTask();
//        task.setHandler(handler);
//        Thread t = new Thread(task);
//        t.start();//task.run()
        RateTask task=new RateTask();
        task.setHandler(handler);
        Thread t = new Thread(task);
        t.start();//task.run()

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}