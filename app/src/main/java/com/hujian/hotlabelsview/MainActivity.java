package com.hujian.hotlabelsview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hujian.hotlabelsview.labels.WallTilesView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WallTilesView wallTilesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wallTilesView=findViewById(R.id.wall_tiles_view);
        initView();
    }

    private void initView() {
        List<String> dates = new ArrayList<>();

        dates.add("数据");
        dates.add("数据数据数据数据数据");
        dates.add("数据数据");
        dates.add("数据数据");
        dates.add("数据数据数");
        dates.add("数");
        dates.add("数据数据数据数据");
        dates.add("数据数据");

        wallTilesView.setHorizontalSpace(24);
        wallTilesView.setVerticalSpace(24);

        wallTilesView.setDates(dates, new WallTilesView.ViewProvider<String>() {
            @Override
            public View getView(String data) {
                TextView textView =new TextView(MainActivity.this);
                textView.setText(data);
                textView.setPadding(10,10,10,10);
                textView.setBackgroundResource(R.drawable.text_bg);
                return textView;
            }
        });
        wallTilesView.setOnItemClickListener(new WallTilesView.OnItemClickListener<String>() {
            @Override
            public void onClick(String o) {
                Toast.makeText(MainActivity.this,o,Toast.LENGTH_SHORT).show();
            }
        });
    }
}