package com.cookandroid.mydiary;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // Adapter에 사용되는 데이터 개수 리턴.
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // 하나 선택
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // 어떤 형태로
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = convertView.findViewById(R.id.imgEmotion) ;
        TextView titleTextView = convertView.findViewById(R.id.textTitle) ;
        TextView descTextView = convertView.findViewById(R.id.textDate) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIconWeather());
        titleTextView.setText(listViewItem.getTitle());
        descTextView.setText(listViewItem.getDate());

        return convertView;
    }

    public void addItem(Drawable icon, String title, String date) {
        ListViewItem item = new ListViewItem();

        item.setIconWeather(icon);
        item.setTitle(title);
        item.setDate(date);

        listViewItemList.add(item);
    }
}
