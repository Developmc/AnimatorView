package com.example.macremote.animatorview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.macremote.animatorview.R;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button histogram ,circleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        histogram = (Button)findViewById(R.id.histogram) ;
        histogram.setOnClickListener(this);

        circleBar = (Button)findViewById(R.id.circleBar) ;
        circleBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {

            case R.id.histogram:
                startActivity(new Intent(MainActivity.this,HistogramAct.class));
                break;
            case R.id.circleBar:
                startActivity(new Intent(MainActivity.this,CircleBarAct.class));
                break;
        }
    }
}
