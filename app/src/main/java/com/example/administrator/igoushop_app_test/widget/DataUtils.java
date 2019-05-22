package com.example.administrator.igoushop_app_test.widget;

import com.example.administrator.igoushop_app_test.pojos.Color;
import com.example.administrator.igoushop_app_test.pojos.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016-08-26.
 */
public class DataUtils  {
    public static  List<Size> toArray(Set<Size> sizes) {
        List<Size> list = new ArrayList<>();
        if(sizes!=null) {
            for (Size s : sizes) {
                list.add(s);
            }
            return list;
        }
        return null;
    }

    public static  List<Color> toArrayFromColor(Set<Color> colors) {
        List<Color> list = new ArrayList<>();
        if(colors!=null) {
            for (Color color : colors) {
                list.add(color);
            }
            return list;
        }
        return null;
    }
}
