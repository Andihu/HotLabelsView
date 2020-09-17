package com.hujian.hotlabelsview.labels;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2015-2020
 * FileName: WallTiles
 * Author: hujian
 * Date: 2020/9/16 12:15
 * History:
 * <author> <time> <version> <desc>
 */
public class WallTilesView<T> extends ViewGroup {
    private static final String TAG = "WallTilesView";
    private static final int CENTER=1;
    private static final int TOP=2;
    private static final int BOTTOM=3;
    private static int POSITION = CENTER;

    private List<T> dates;
    private int mHorizontalSpace = 0;
    private int mVerticalSpace = 0;
    private int mMaxLine = -1;
    private int mMaxCounts = -1;
    private int mOpen;
    List<ViewDescription> children = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    public WallTilesView(Context context) {
        this(context, null);
    }

    public WallTilesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WallTilesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDates(List<T> dates, ViewProvider viewProvider) {
        this.dates = dates;
        children.clear();
        addItem(viewProvider);
    }

    public void setHorizontalSpace(int horizontalSpace) { mHorizontalSpace = dp2sp(getContext(),horizontalSpace); }

    public void setVerticalSpace(int verticalSpace) { mVerticalSpace = dp2sp(getContext(),verticalSpace); }

    public void setMaxCounts(int maxCounts) { mMaxCounts = maxCounts; }

    public void setMaxLine(int maxLine) { mMaxLine = maxLine; }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void addItem(ViewProvider viewProvider) {
        for (final T date : dates) {
            View view = viewProvider.getView(date);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onClick(date);
                    }
                }
            });
            viewProvider.bindView(this,view,date);
            addView(view);
            children.add(new ViewDescription());
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int left = 0; // 当前的左边距离
        int top = 0; // 当前的上边距离
        int beforeBottom = 0;//上一层控件的下边距.
        final int count = getChildCount(); // tag的数量
        int totalHeight = 0; // WRAP_CONTENT时控件总高度
        int totalWidth = 0; // WRAP_CONTENT时控件总宽度
        boolean isHH = false;//是否换行
        int lineCount = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //第一行的时候
            if (i == 0) {
                totalHeight = child.getMeasuredHeight();
                beforeBottom = 0;
            }
            /**
             * 换行条件
             * 确保item 最小间距为 mMinHorizontalSpace 时，剩余空间不足以提供放置控件，则换行
             */
            left = (left == 0 ? 0 : left + mHorizontalSpace);
            // 换行
            if (left + child.getMeasuredWidth()> getMeasuredWidth()) {

                beforeBottom = setViewPosition(lineCount);

                lineCount ++;
                if ((mMaxLine > 0 && lineCount > (mMaxLine-1)) || (mMaxCounts > 0 && i > mMaxCounts)) {
                    continue;
                }
                isHH = true;
                left = 0;
                top = beforeBottom == 0 ? 0 : (beforeBottom + mVerticalSpace); // 上一层控件的底部作为当前控件的顶部
                totalHeight = top + child.getMeasuredHeight();
            }
            //取当前高度跟之前值中的最大值
            beforeBottom = Math.max(child.getMeasuredHeight(), beforeBottom) + ((isHH) ? child.getMeasuredHeight() + mVerticalSpace : 0);
            isHH = false;
            children.get(i).setLeft(left);
            children.get(i).setRight(left + child.getMeasuredWidth());
            children.get(i).setTop(top);
            children.get(i).setBottom(top + child.getMeasuredHeight());
            children.get(i).setView(child);
            children.get(i).setPosition(lineCount);
            children.get(i).setWight(child.getMeasuredWidth());
            children.get(i).setHeight(child.getMeasuredHeight());
            left += child.getMeasuredWidth();
            if (left > totalWidth) { // 当宽度为WRAP_CONTENT时，取宽度最大的一行
                totalWidth = left;
            }

        }

        int height = 0;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        } else {
            height = totalHeight;
        }

        int width = 0;
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = totalWidth;
        }
        setMeasuredDimension(width, height);
    }

    private int setViewPosition(int lineCount) {
        List<ViewDescription> descriptions = new ArrayList<>();
        for (ViewDescription child : children) {
            if (child.position==lineCount){

                descriptions.add(child);
            }
        }
        int maxHeight = 0;
        int top = 0;
        for (ViewDescription description : descriptions) {
            top = description.top;
            if (description.getHeight()>=maxHeight){
                maxHeight = description.getHeight();
            }
        }
        for (ViewDescription child : children) {
            if (child.position==lineCount){
                if (child.getHeight()<maxHeight){
                    switch (POSITION){
                        case TOP:
                            break;
                        case CENTER:
                            child.setTop(top+maxHeight/2-child.getHeight()/2);
                            child.setBottom(top+maxHeight/2+child.getHeight()/2);
                            break;
                        case BOTTOM:
                            child.setTop(top+maxHeight-child.getHeight());
                            child.setBottom(top+maxHeight);
                            break;
                    }
                }
            }
        }
        return top+maxHeight;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (ViewDescription child : children) {
            if (child.getView() != null) child.getView().layout(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
        }
    }

    public interface ViewProvider<T> {
        View getView(T t);

        void bindView(View parent,View view, T t);
    }

    public interface OnItemClickListener<T> {
        void onClick(T t);
    }

    public static int dp2sp(Context context,float dpVal) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics()));
    }
}
