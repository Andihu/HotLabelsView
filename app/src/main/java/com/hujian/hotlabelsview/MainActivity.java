package com.hujian.hotlabelsview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hujian.hotlabelsview.labels.WallTilesView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final List<Dates> dates = new ArrayList<>();
        dates.add(new Dates(1,"东风破"));
        dates.add(new Dates(2,"https://oimageb5.ydstatic.com/image?id=3588881765011216151&product=adpublish&w=520&h=347"));
        dates.add(new Dates(1,"那只花儿"));
        dates.add(new Dates(1,"四面楚歌"));
        dates.add(new Dates(2,"https://oimageb5.ydstatic.com/image?id=3588881765011216151&product=adpublish&w=520&h=347"));
        dates.add(new Dates(1,"春天里"));
        dates.add(new Dates(1,"春里"));
        dates.add(new Dates(2,"https://oimageb5.ydstatic.com/image?id=3588881765011216151&product=adpublish&w=520&h=347"));
        dates.add(new Dates(1,"春"));
        dates.add(new Dates(1,"春里"));
        dates.add(new Dates(2,"https://oimageb5.ydstatic.com/image?id=3588881765011216151&product=adpublish&w=520&h=347"));
        dates.add(new Dates(1,"春里"));

        dates.add(new Dates(1,"四面楚歌"));
        dates.add(new Dates(1,"春天里"));
        dates.add(new Dates(2,"https://oimageb5.ydstatic.com/image?id=3588881765011216151&product=adpublish&w=520&h=347"));
        dates.add(new Dates(1,"四面楚歌"));
        dates.add(new Dates(1,"春天里"));
        dates.add(new Dates(2,"https://oimageb5.ydstatic.com/image?id=3588881765011216151&product=adpublish&w=520&h=347"));
        dates.add(new Dates(1,"里"));
        dates.add(new Dates(1,"春里"));
        dates.add(new Dates(2,"https://oimageb5.ydstatic.com/image?id=3588881765011216151&product=adpublish&w=520&h=347"));
        dates.add(new Dates(1,"天里"));
        dates.add(new Dates(1,"春天里春天里"));
        wallTilesView.setHorizontalSpace(10);
        wallTilesView.setVerticalSpace(10);

        wallTilesView.setDates(dates, new WallTilesView.ViewProvider<Dates>() {
            @Override
            public View getView(Dates data) {

                if (data.type==1){
                    return (TextView) LayoutInflater.from(MainActivity.this).inflate(R.layout.text_layout,null);
                }else {
                    return (ImageView) LayoutInflater.from(MainActivity.this).inflate(R.layout.image_layout,null);
                }
            }

            @Override
            public void bindView(View parent, View view, Dates s) {
                if (s.type==1){
                    TextView textView = (TextView) view;
                    textView.setText(s.data);
                }else {
                    ImageView imageView = (ImageView) view;
                    Glide.with(MainActivity.this).load(s.data).into(imageView);
                }
            }
        });
        wallTilesView.setOnItemClickListener(new WallTilesView.OnItemClickListener<String>() {
            @Override
            public void onClick(String o) {
                Toast.makeText(MainActivity.this,o,Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class Dates{
        int type;

        public Dates(int type, String data) {
            this.type = type;
            this.data = data;
        }

        String data;
    }
}