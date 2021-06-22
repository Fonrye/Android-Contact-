package com.example.addressbook;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: AddressBook
 * @Package: com.example.addressbook
 * @QQ: 1025377230
 * @Author: Fonrye
 * @CreateDate: 2021/4/7 23:14
 * @Email: fonrye@163.com
 * @Version: 1.0
 */
public class MySimpleAdapter extends SimpleAdapter {
    private ArrayList<Map<String, String>> mData;

    public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data,
                           int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mData = (ArrayList<Map<String, String>>) data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int mPosition = position;
        return super.getView(position, convertView, parent);
    }
}
