package com.x.chwang.demolist.lottie;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.x.chwang.demolist.R;
import com.x.chwang.demolist.base.BaseActivity;

public class LottieActivity extends BaseActivity{

    private LottieAnimationView lottieAnimationView;
     private Button play, pause, cancel;
    @Override
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lottie);
        init();
    }

    private void init(){

        /**初始化控件*/
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieView);
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        cancel = (Button) findViewById(R.id.cancel);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始动画
                lottieAnimationView.playAnimation();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //暂停动画
                lottieAnimationView.pauseAnimation();   //将动画初始至开头
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieAnimationView.cancelAnimation();   //点击暂停之后，再点击播放，会从暂停的位置继续播放
            }
        });
        /**初始化动画*/
        lottieAnimationView.setAnimation("hello-world.json");
        lottieAnimationView.loop(true);

        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Toast.makeText(LottieActivity.this,"onAnimationStart",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(LottieActivity.this,"onAnimationEnd，动画总时长为" + lottieAnimationView.getDuration() + "s",Toast.LENGTH_SHORT).show();
            }

            /**此方法未进行回调，playAnimator,cancelAnimator,pauseAnimator,回调的都是onAnimationEnd方法*/
            @Override
            public void onAnimationCancel(Animator animation) {
                Toast.makeText(LottieActivity.this,"onAnimationCancel",Toast.LENGTH_SHORT).show();
            }

            /**当loop=true的时候才会回调此方法*/
            @Override
            public void onAnimationRepeat(Animator animation) {
                Toast.makeText(LottieActivity.this,"onAnimationRepeat",Toast.LENGTH_SHORT).show();
            }
        });

        /**使用自定义动画和速率时
         *
         * 若你此时点击了paly按钮
         * 则会重新生成一个动画覆盖自定义的动画
         * 重新生成的动画会从自定义动画的地方开始播放
         * */
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0f, 1f, 0f)
                .setDuration(10000);
        valueAnimator1.setRepeatCount(3);  //重复循环3次
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setProgress((float)animation.getAnimatedValue());
            }
        });
//        valueAnimator1.start();

        /**动画的组合使用*/
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f, 0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setProgress((float)animation.getAnimatedValue());
            }
        });
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(lottieAnimationView,"alpha",0.0f,1.0f,0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimator,alphaAnimator);
        animatorSet.setDuration(10000);
        animatorSet.start();



    }
}