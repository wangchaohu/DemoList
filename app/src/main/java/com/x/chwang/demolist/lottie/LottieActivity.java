package com.x.chwang.demolist.lottie;

import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.x.chwang.demolist.R;
import com.x.chwang.demolist.base.BaseActivity;

public class LottieActivity extends BaseActivity{
    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lottie);
        LottieAnimationView view = (LottieAnimationView) findViewById(R.id.lottieView);
        view.setAnimation("hello-world.json");
        view.loop(false);
    }
}