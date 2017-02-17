package com.x.chwang.demolist.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.x.chwang.demolist.R;
import com.x.chwang.demolist.base.BaseActivity;
import com.x.chwang.demolist.customlottie.CustomLottieActivity;
import com.x.chwang.demolist.lottie.LottieActivity;
import com.x.chwang.demolist.viewdrag.ViewDragActivity;
import com.x.chwang.demolist.write.WriteActivity;

import java.util.Arrays;
import java.util.List;


/**
 * Created by xwangch on 16/8/2.
 */
public class RecycleActivity extends BaseActivity {

    private List<String> chwang_s = Arrays.asList("Lottie简单使用","Lottie自定义动画","ViewDrag使用","读写文件");
    private List chwang_c = Arrays.asList(LottieActivity.class, CustomLottieActivity.class, ViewDragActivity.class, WriteActivity.class);

    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_recycleview_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecycleViewAdapter(this));
    }

    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyViewHolder>{
        /**
         * 如果想要添加一个按钮，则在contentData中添加一个按钮名称即可，
         * 以及在skipClass中添加一个要跳转的类名
         */
        private Activity activity;

        public MyRecycleViewAdapter(Activity activity) {
            this.activity = activity;

        }

        @Override
        public int getItemCount() {
            return chwang_s.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecycleActivity.this).inflate(R.layout.layout_item_recycleview_main, parent, false);
            return new MyViewHolder(view);
        }




        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.getBtn().setText(chwang_s.get(position));
             holder.getBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RecycleActivity.this, (Class) chwang_c.get(position)));
                }
            });
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private Button btn_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn_item = (Button)itemView.findViewById(R.id.button);
        }
        public Button getBtn() {
            return btn_item;
        }
    }
}
