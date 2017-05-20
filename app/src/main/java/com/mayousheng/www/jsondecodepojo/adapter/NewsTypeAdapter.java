package com.mayousheng.www.jsondecodepojo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.pojo.NewsType;
import com.shandao.www.baseholder.BaseHolder;
import com.shandao.www.baseholder.ViewDesc;

import java.util.ArrayList;

/**
 * Created by marking on 2017/5/20.
 */

public class NewsTypeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NewsType> data;
    private int selectItem;

    public NewsTypeAdapter(Context context, ArrayList<NewsType> data) {
        this.context = context;
        this.data = data;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public NewsType getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsTypeHolder newsTypeHolder;
        if (convertView == null) {
            newsTypeHolder = new NewsTypeHolder();
            convertView = newsTypeHolder.newRootView(context, R.layout.item_news_type, parent);
            convertView.setTag(newsTypeHolder);
        } else {
            newsTypeHolder = (NewsTypeHolder) convertView.getTag();
        }
        newsTypeHolder.inViewBind(getItem(position));
        if (position == selectItem) {
            newsTypeHolder.title.setBackgroundColor(context.getResources().getColor(R.color.gray));
        } else {
            newsTypeHolder.title.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }


    private class NewsTypeHolder extends BaseHolder<NewsType> {

        @ViewDesc(viewId = R.id.title)
        public TextView title;

        @Override
        public void inViewBind(NewsType newsType) {
            title.setText(newsType.title);
        }
    }

}
