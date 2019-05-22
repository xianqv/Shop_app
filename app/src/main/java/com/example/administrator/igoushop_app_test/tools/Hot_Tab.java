package com.example.administrator.igoushop_app_test.tools;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.igoushop_app_test.R;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by Administrator on 2016-06-21.
 */
public class Hot_Tab extends LinearLayout {
    private TextView textView1;
    private TextView textView2;
    private onSegmentViewClickListener listener;
    public Hot_Tab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Hot_Tab(Context context) {
        super(context);
        init();
    }

    private void init() {
        textView1 = new TextView(getContext());
        textView2 = new TextView(getContext());
        textView1.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView2.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView1.setText("品牌街");
        textView2.setText("买家秀");
        XmlPullParser xrp = getResources().getXml(R.drawable.seg_text_color_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor(csl);
            textView2.setTextColor(csl);
        } catch (Exception e) {
        }
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView1.setPadding(3, 6, 3, 6);
        textView2.setPadding(3, 6, 3, 6);
        setSegmentTextSize(16);
        textView1.setBackgroundResource(R.drawable.hot_tab_left);
        textView2.setBackgroundResource(R.drawable.hot_tab_right);
        textView1.setSelected(true);
        this.removeAllViews();
        this.addView(textView1);
        this.addView(textView2);
        this.invalidate();

        textView1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView1.isSelected()) {
                    return;
                }
                textView1.setSelected(true);
                textView2.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView1, 0);
                }
            }
        });
        textView2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView2.isSelected()) {
                    return;
                }
                textView2.setSelected(true);
                textView1.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView2, 1);
                }
            }
        });
    }
    public void setSegmentTextSize(int dp) {
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {
        this.listener = listener;
    }
    public void setSegmentText(CharSequence text,int position) {
        if (position == 0) {
            textView1.setText(text);
        }
        if (position == 1) {
            textView2.setText(text);
        }
    }

    public static interface onSegmentViewClickListener{
        public void onSegmentViewClick(View v, int position);
    }
}
