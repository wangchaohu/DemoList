package com.x.chwang.demolist.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.x.chwang.demolist.R;
import com.x.chwang.demolist.base.BaseActivity;


/**
 * Created by xwangch on 16/8/13.
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome_main);
        pause();
    }

    private void pause(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    startActivity(new Intent(WelcomeActivity.this, RecycleActivity.class));
                    finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
