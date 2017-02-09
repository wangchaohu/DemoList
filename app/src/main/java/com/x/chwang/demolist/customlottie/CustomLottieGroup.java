package com.x.chwang.demolist.customlottie;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.model.LottieComposition;
import com.x.chwang.demolist.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lapsen_wang on 2017/2/9/0009.
 */
public class CustomLottieGroup extends FrameLayout {
    private final Map<String, LottieComposition> compositionMap = new HashMap<>();
    private final List<View> views = new ArrayList<>();

    private final char[] lapsen = {'L','A','P','S','E','N'};

    @Nullable
    private LottieAnimationView cursorView;

    public CustomLottieGroup(Context context) {
        super(context);
        init();
    }

    public CustomLottieGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomLottieGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LottieComposition.fromAssetFileName(getContext(), "BlinkingCursor.json",
                new LottieComposition.OnCompositionLoadedListener() {
                    @Override
                    public void onCompositionLoaded(LottieComposition composition) {
                        cursorView = new LottieAnimationView(getContext());
                        CustomLottieGroup.LayoutParams layoutParams = new CustomLottieGroup.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        cursorView.setLayoutParams(layoutParams);
                        cursorView.setComposition(composition);
                        cursorView.loop(true);
                        cursorView.playAnimation();
                        addView(cursorView);
                    }
                });

        for (int i = 0; i < lapsen.length; i++){
            try {
                Thread.sleep(2000);
                start(i);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void start(int i){
        String letter = "" + Character.toUpperCase(lapsen[i]);
        final String fileName =    letter + ".json";
        if (compositionMap.containsKey(fileName)) {
            addComposition(compositionMap.get(fileName));
        }
        else
        {
            LottieComposition.fromAssetFileName(getContext(), fileName,
                    new LottieComposition.OnCompositionLoadedListener() {
                        @Override
                        public void onCompositionLoaded(LottieComposition composition) {
                            compositionMap.put(fileName, composition);
                            addComposition(composition);
                        }
                    });
        }
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
        if (index == -1) {
            views.add(child);
        } else {
            views.add(index, child);
        }
    }

    private void removeLastView() {
        if (views.size() > 1) {
            int position = views.size() - 2;
            removeView(views.get(position));
            views.remove(position);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (views.isEmpty()) {
            return;
        }
        int currentX = getPaddingTop();
        int currentY = getPaddingLeft();

        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            if (!fitsOnCurrentLine(currentX, view)) {
                currentX = getPaddingLeft();
                currentY += view.getMeasuredHeight();
            }
            currentX += view.getWidth();
        }

        setMeasuredDimension(getMeasuredWidth(),
                currentY + views.get(views.size() - 1).getMeasuredHeight() * 2 - 80);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (views.isEmpty()) {
            return;
        }
        int currentX = getPaddingTop();
        int currentY = getPaddingLeft();

        for (int i = 0; i < views.size(); i++) {
            View view = views.get(i);
            if (!fitsOnCurrentLine(currentX, view)) {
                currentX = getPaddingLeft();
                currentY += view.getMeasuredHeight();
            }
            view.layout(currentX, currentY, currentX + view.getMeasuredWidth(),
                    currentY + view.getMeasuredHeight());
            currentX += view.getWidth();
        }
    }

    //动画文字
    private void addComposition(LottieComposition composition) {
        LottieAnimationView lottieAnimationView = new LottieAnimationView(getContext());
        CustomLottieGroup.LayoutParams layoutParams = new CustomLottieGroup.LayoutParams(
                100,100,TEXT_ALIGNMENT_CENTER
        );
        layoutParams.setMarginStart(100);
        lottieAnimationView.setLayoutParams(layoutParams);
        lottieAnimationView.setComposition(composition);
        lottieAnimationView.playAnimation();
        if (cursorView == null) {
            addView(lottieAnimationView);
        } else {
            int index = indexOfChild(cursorView);
            addView(lottieAnimationView, index);
        }
    }

    private boolean fitsOnCurrentLine(int currentX, View view) {
        return currentX + view.getMeasuredWidth() < getWidth() - getPaddingRight();
    }
}
