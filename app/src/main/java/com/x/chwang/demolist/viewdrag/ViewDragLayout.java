package com.x.chwang.demolist.viewdrag;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.x.chwang.demolist.R;


/**
 * Created by xwangch on 16/8/4.
 */
public  abstract class ViewDragLayout extends LinearLayout implements View.OnClickListener{

    private ViewDragHelper mViewDragHelper;
    private ViewGroup head_lay;   //头布局
    private ViewGroup contentView;  //隐藏的内容
    private int dragRange;   //contentView的高度
    private int flag = 2;  //控制向上还是向下
    private Button btn;

    public ViewDragLayout(Context context) {
        this(context, null);
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViewDrag();
    }

    private void initViewDrag(){
        Log.d("wch", "initViewDrag: 1");
        mViewDragHelper = ViewDragHelper.create(this, dragCallBack);
    }

    private ViewDragHelper.Callback dragCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            Log.d("wch", "tryCaptureView: ");
            return child == head_lay;
        }

        /**监听位置的移动,移动listview*/
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            Log.d("wch", "onViewPositionChanged: ");
            head_lay.layout(0, top + head_lay.getHeight(), getWidth(), top + head_lay.getHeight() + dragRange);
        }

        /**设置垂直方向的移动距离*/
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            Log.d("wch", "clampViewPositionVertical: ");
            int topBound = getHeight() - dragRange - head_lay.getHeight();
            int buttomBound = getHeight() - head_lay.getHeight();
            final int newHeight = Math.min(Math.max(topBound, top), buttomBound);
            return newHeight;
        }

        /**返回一个可垂直运动的范围*/
        @Override
        public int getViewVerticalDragRange(View child) {
            Log.d("wch", "getViewVerticalDragRange: ");
            return dragRange;
        }

        /**在子view不再活动是的时候回调
         * 滑动的控制
         *
         * 用于手势滑动
         * */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            Log.d("wch", "onViewReleased: ");
            super.onViewReleased(releasedChild, xvel, yvel);
            if(yvel >= 0 && head_lay.getTop() > dragRange/2.0f){
                smoothToButtom();
            }else if(yvel > 0){
                smoothToButtom();
            }else {
                smoothToTop();
            }
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d("wch", "onFinishInflate: 2");

        initLayout();
        //获取控件
//        head_lay = (LinearLayout) findViewById(R.id.head_lay);
    }

    /**设置头与尾*/
    public  void setLayout(ViewGroup headLayout, ViewGroup contentLayout){
        if (null != headLayout){
        head_lay = headLayout;
        }
        if (null != contentLayout){
            contentView = contentLayout;
        }
    }

    /**初始化控件*/
    public abstract void initLayout();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("wch", "onMeasure: 3");
        /**
         * 获取滑动的高度
         *
         * 分为三种情况：只有head，只有contentview，两个都有
         * */
        //只有contentView
        if (null == head_lay && null != contentView){
            dragRange = contentView.getMeasuredHeight();
        }else {
            dragRange = head_lay.getMeasuredHeight();
        }

    }

    /**重新布局,隐藏listView*/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("wch", "onLayout: 4");
        //只有一个headLayout
        if (null != head_lay && null == contentView) {
            head_lay.layout(0, getHeight(), getWidth(), getHeight() + dragRange);        //隐藏的内容
        }
        //headLayout与contentView都有
        if (null != head_lay && null != contentView){
            head_lay.layout(0, getHeight() - head_lay.getHeight(), getWidth(), getHeight());    //显示的内容
            contentView.layout(0, getHeight(), getWidth(), getHeight() + dragRange);
        }
        //只有contentView
        if (null == head_lay && null != contentView){
            contentView.layout(0, getHeight(), getWidth(), getHeight() + dragRange);
        }
    }

    /**拦截悬停事件*/
//    @Override
//    public boolean onInterceptHoverEvent(MotionEvent event) {
//        Log.d("wch", "onInterceptHoverEvent: ");
//        final int action = MotionEventCompat.getActionMasked(event);
//        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
//            mViewDragHelper.cancel();
//            return false;
//        }
//        return mViewDragHelper.shouldInterceptTouchEvent(event);
//    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("wch", "onTouchEvent: ");
//        mViewDragHelper.processTouchEvent(event);
//        return true;
//    }

    //滑到上方
    private void smoothToTop(){
        Log.d("wch", "smoothToTop: ");
        if(mViewDragHelper.smoothSlideViewTo(head_lay, getPaddingLeft(), getHeight() - dragRange - head_lay.getHeight())){
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }
    //滑下来
    private void smoothToButtom(){
        Log.d("wch", "smoothToButtom: ");
        if(mViewDragHelper.smoothSlideViewTo(head_lay, getPaddingLeft(), getHeight() - head_lay.getHeight())){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        Log.d("wch", "computeScroll: 5");
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    //点击滑动监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
            {
                if(flag % 2 == 0){

                    smoothToTop();
                    flag = 1;
                }else if(flag % 2 == 1){
                    smoothToButtom();
                    flag = 2;
                }
                break;
            }
        }
    }
}
