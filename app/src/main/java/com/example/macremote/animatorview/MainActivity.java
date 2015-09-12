package com.example.macremote.animatorview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    AnimatorView mAnimatorView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        mAnimatorView = new AnimatorView(this);
        setContentView(mAnimatorView);
    }
}
