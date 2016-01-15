package com.recycle.xiaoxiaoyin.pagerecyclerview;

import java.io.Serializable;

/**
 * Created by xiaoxiaoyin on 16/1/13.
 */
public class ClassItem implements Serializable {

    public static final int GRID_VERTICAL = 0;
    public static final int LINEAR_VERTICAL = 2;
    public static final int START_VERTICAL = 4;
    public static final int GRID_HORIZONTAL = 1;
    public static final int LINEAR_HORIZONTAL = 3;
    public static final int START_HORIZONTAL = 5;

    public Class<?> aClass;
    public String content;
    public int type;

    public ClassItem(Class<?> aClass, String content, int type) {
        this.aClass = aClass;
        this.content = content;
        this.type = type;
    }
}
