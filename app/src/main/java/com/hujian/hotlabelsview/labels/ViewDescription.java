package com.hujian.hotlabelsview.labels;

import android.view.View;

/**
 * Copyright (C), 2015-2020
 * FileName: ViewDescription
 * Author: hujian
 * Date: 2020/9/16 16:52
 * History:
 * <author> <time> <version> <desc>
 */
public  class ViewDescription{
    View view;
    int left;
    int top;
    int right;
    int bottom;
    int position ;
    int height;
    int wight;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWight() {
        return wight;
    }

    public void setWight(int wight) {
        this.wight = wight;
    }

    public View getView() { return view; }

    public void setView(View view) { this.view = view; }

    public int getLeft() { return left; }

    public void setLeft(int left) { this.left = left; }

    public int getTop() { return top; }

    public void setTop(int top) { this.top = top; }

    public int getRight() { return right; }

    public void setRight(int right) { this.right = right; }

    public int getBottom() { return bottom; }

    public void setBottom(int bottom) { this.bottom = bottom; }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ViewDescription() {}

    public ViewDescription(View view, int left, int top, int right, int bottom) {
        this.view = view;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    public String toString() {
        return "ViewDescription{" + "view=" + view + ", left=" + left + ", top=" + top + ", right=" + right + ", bottom=" + bottom + ", position=" + position + ", height=" + height + ", wight=" + wight + '}';
    }
}
