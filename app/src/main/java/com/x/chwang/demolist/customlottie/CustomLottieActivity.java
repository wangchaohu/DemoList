package com.x.chwang.demolist.customlottie;

import android.os.Bundle;

import com.x.chwang.demolist.R;
import com.x.chwang.demolist.base.BaseActivity;


/**
 * Created by lapsen_wang on 2017/2/9/0009.
 */

public class CustomLottieActivity extends BaseActivity {

    private CustomLottieGroup footView;

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customlottie);
        init();
    }

    private void init() {

        footView = (CustomLottieGroup) findViewById(R.id.customLottieGroup);
    }

}
