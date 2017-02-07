package com.x.chwang.demolist.main;

import android.app.Activity;
import android.widget.Button;

/**
 * Created by xwangch on 16/8/2.
 */
public interface MyAdapter {
    void initData(Button btn, String flag, int position);

    void listClickListener(int position, String flag, Activity activity);
}
