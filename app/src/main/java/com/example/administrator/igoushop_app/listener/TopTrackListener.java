package com.example.administrator.igoushop_app.listener;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016-08-17.
 */
public class TopTrackListener extends RecyclerView.OnScrollListener {

    private static final String TAG = TopTrackListener.class.getSimpleName();

    private int mLastDy = 0;
    private int mTotalDy = 0;
    //设置全局View的对象
    private View mTargetView = null;
    //创建 ObjectAnimator 对象
    private ObjectAnimator mAnimator = null;
    //设置 隐藏 和显示的状态
    private boolean isAlreadyHide = false, isAlreadyShow = false;

    public TopTrackListener(View target) {
        if (target == null) {
            throw new IllegalArgumentException("target shouldn't be null");
        }
        mTargetView = target;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                final float transY = mTargetView.getTranslationY();
                int distance = -mTargetView.getBottom();

                if (transY == 0 || transY == distance) {
                    return;
                }
                if (mLastDy > 0) {
                    mAnimator = animateHide(mTargetView);
                } else {
                    mAnimator = animateShow(mTargetView);
                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                break;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        /**
         * 组件向上滑动时，dy 为负值  向下 滑动时 dy为 正值
         */
        //  mTotalDy 与 滑动的距离相减
        mTotalDy -= dy;
        // 取得 滑动的 距离
        mLastDy = dy;
        // 取得 组件  在Y轴 滑动的 距离
        final float transY = mTargetView.getTranslationY();
        // 新的 滑动 值
        float newTransY;
        //distance 距离 getBottom是View滑动时底部距离父容器顶部的距离，并不是距离父容器底部  取 负数
        int distance = -mTargetView.getBottom();
        //如果 mTotalDy 与 distance 值相等  组件滑动距离大于 0
        if (mTotalDy >= distance && dy > 0) {
            return;
        }
        //隐藏 时 dy为负值 不成立
        if (isAlreadyHide && dy > 0) {
            return;
        }
        //如果组件 显示 dy 为正值 不成立
        if (isAlreadyShow && dy < 0) {
            return;
        }
        // newTransY 归 0
        // 向上 transY -dy  = transY+dy
        // 向下  newTransY 为 0
        newTransY = transY - dy;
        // 向下
        //向上  如果 newTransY < distance  则 newTransY = distance 进行隐藏组件
        if (newTransY < distance) {
            newTransY = distance;
        } else if (newTransY == distance) {
            return;
        } else if (newTransY > 0) {
            newTransY = 0;
        } else if (newTransY == 0) {
            return;
        }

        mTargetView.setTranslationY(newTransY);
        isAlreadyHide = newTransY == distance;
        isAlreadyShow = newTransY == 0;

    }

    private ObjectAnimator animateHide (View targetView) {
        int distance = -targetView.getBottom();
        return animationFromTo(targetView, targetView.getTranslationY(), distance);
    }

    private ObjectAnimator animateShow (View targetView) {
        return animationFromTo(targetView, targetView.getTranslationY(), 0);
    }

    private ObjectAnimator animationFromTo (View view, float start, float end) {
        String propertyName = "translationY";
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, propertyName, start, end);
        animator.start();
        return animator;
    }

}
