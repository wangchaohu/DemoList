package com.x.chwang.demolist.viewdrag;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.x.chwang.demolist.R;

/**
 * Created by xwangch on 16/8/4.
 */
public class ViewDragLayout extends LinearLayout implements View.OnClickListener{

    private ViewDragHelper mViewDragHelper;
    private LinearLayout head_lay;   //头布局
    private LinearLayout contentView;  //隐藏的内容
    private Button btn_assess;   //我要评价按钮
    private ImageView img;   //头布局上的图片
    private int dragRange;   //contentView的高度
    private int flag = 2;  //控制向上还是向下

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
        /**第二个参数设置滑动灵敏度*/
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, dragCallBack);
    }

    private ViewDragHelper.Callback dragCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == head_lay;
        }

        /**监听位置的移动,移动listview*/
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            contentView.layout(0, top + head_lay.getHeight(), getWidth(), top + head_lay.getHeight() + dragRange);
        }

        /**设置垂直方向的移动距离*/
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int topBound = getHeight() - dragRange - head_lay.getHeight();
            int bottomBound = getHeight() - head_lay.getHeight();
            final int newHeight = Math.min(Math.max(topBound, top), bottomBound);
            return newHeight;
        }

        /**返回一个可垂直运动的范围*/
        @Override
        public int getViewVerticalDragRange(View child) {
            return dragRange;
        }

        /**在子view不再活动是的时候回调
         * 滑动的控制
         * */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
//            super.onViewReleased(releasedChild, xvel, yvel);
            if(yvel >= 0 && releasedChild.getTop() > dragRange/2.0f){
                smoothToButtom();
            }else if(yvel > 0){
                smoothToButtom();
            }else {
                smoothToTop();
            }
        }

        /**
         * 状态发生改变时回调
         * @see #STATE_IDLE
         * @see #STATE_DRAGGING
         * @see #STATE_SETTLING
         * */
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        head_lay = (LinearLayout) findViewById(R.id.head_lay);
        contentView = (LinearLayout) findViewById(R.id.contentview);
        btn_assess = (Button) findViewById(R.id.assess);
        img = (ImageView) findViewById(R.id.img);
        btn_assess.setOnClickListener(this);
        head_lay.setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        dragRange = contentView.getMeasuredHeight();
    }

    /**重新布局,隐藏listView*/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        head_lay.layout(0, getHeight() - head_lay.getHeight(), getWidth(), getHeight());
        contentView.layout(0, getHeight(), getWidth(), getHeight() + dragRange);
    }

    /**拦截悬停事件*/
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        if(action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP){
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    //滑到上方
    private void smoothToTop(){
        if(mViewDragHelper.smoothSlideViewTo(head_lay, getPaddingLeft(), getHeight() - dragRange - head_lay.getHeight())){
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }
    //滑下来
    private void smoothToButtom(){
        if(mViewDragHelper.smoothSlideViewTo(head_lay, getPaddingLeft(), getHeight() - head_lay.getHeight())){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        if(mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    //点击滑动监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.assess:
                Toast.makeText(getContext(), "谢谢您的评价", Toast.LENGTH_SHORT).show();
                break;
            case R.id.head_lay:
            {
                if(flag % 2 == 0){
                    img.setImageResource(R.drawable.charge_down);
                    smoothToTop();
                    flag = 1;
                }else if(flag % 2 == 1){
                    img.setImageResource(R.drawable.charge_up);
                    smoothToButtom();
                    flag = 2;
                }
                break;
            }
        }
    }
}
