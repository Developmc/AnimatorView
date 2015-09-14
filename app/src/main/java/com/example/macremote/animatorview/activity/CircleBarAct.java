package com.example.macremote.animatorview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.macremote.animatorview.R;
import com.example.macremote.animatorview.view.CircleBarView;

/**1.该view在设置属性之后时候会有数字和圆圈不断增长的效果
 * 2.该view在按下和放开状态下显示不同的样式
 * Created by macremote on 2015/9/14.
 */
public class CircleBarAct extends Activity {
    private CircleBarView circleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circle);

        circleBar = (CircleBarView) findViewById(R.id.circle);
        circleBar.setSweepAngle(360);
        circleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击开始动画
//                circleBar.startCustomAnimation();
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {
                circleBar.setText("360");
            }
        }, 2000);
    }
}
