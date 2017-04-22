package com.mayousheng.www.jsondecodepojo.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.holder.CommonFootHolder;
import com.mayousheng.www.jsondecodepojo.holder.NewsBodyHolder;
import com.mayousheng.www.jsondecodepojo.pojo.News;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

import java.util.ArrayList;

/**
 * Created by marking on 2017/4/11.
 */

public class NewsAdapter extends BaseAdapter {

    private static final int TYPE_FOOT = 0;
    private static final int TYPE_BODY = 1;
    private static final int PAGE_NUM = 10;//每次请求数据条数

    private Activity activity;
    private String footStr;
    private ArrayList<News> datas = new ArrayList<News>();
    private boolean ifFootRefreshed = true;
    private boolean isInRefresh;
    private int page = 0;
    private boolean haveMore = true;

    public NewsAdapter(Activity activity) {
        this.activity = activity;
        footStr = activity.getString(R.string.load0);
        load(0);
    }

    @Override
    public int getCount() {
        return 1 + (datas == null ? 0 : datas.size());
    }

    @Override
    public Object getItem(int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_FOOT:
                return footStr;
            default:
                return (datas == null || datas.size() <= position) ? null : datas.get(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return TYPE_FOOT;
        } else {
            return TYPE_BODY;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        Object item = getItem(position);
        switch (type) {
            case TYPE_BODY:
                NewsBodyHolder bodyHolder;
                if (convertView == null) {
                    bodyHolder = new NewsBodyHolder(activity, R.layout.item_news, parent);
                    convertView = bodyHolder.getView();
                    convertView.setTag(bodyHolder);
                } else {
                    bodyHolder = (NewsBodyHolder) convertView.getTag();
                }
                News news = (News) item;
                bodyHolder.title.setText(news.title);
                bodyHolder.desc.setText(news.description);
                bodyHolder.time.setText(news.ctime);
                bodyHolder.img.setTag(news.picUrl);
                new ShowImageUtils().loadImage(activity, news.picUrl, bodyHolder.img);
                return convertView;
            case TYPE_FOOT:
                CommonFootHolder footHolder;
                if (convertView == null) {
                    footHolder = new CommonFootHolder(activity, R.layout.item_foot, parent);
                    convertView = footHolder.getView();
                    convertView.setTag(footHolder);
                } else {
                    footHolder = (CommonFootHolder) convertView.getTag();
                }
                footHolder.title.setText(item.toString());
                if (ifFootRefreshed) {
                    ifFootRefreshed = false;
                } else {
                    onFoot();
                }
                return convertView;
        }
        return null;
    }

    public void onFoot() {
        load(page);
    }

    public void load(final int position) {
        if (isInRefresh) {
            return;
        }
        isInRefresh = true;
        updateDataView(null, activity.getString(R.string.load1));
        InfoUtils.getNewsInfo("wxnew", position, PAGE_NUM, new ArrayListBack<News>() {
            @Override
            public void onFail(int status, String message) {
                isInRefresh = false;
            }

            @Override
            public void onResult(final ArrayList<News> data) {
                if (data != null) {
                    haveMore = data.size() == PAGE_NUM;
                    footStr = haveMore ? activity.getString(R.string.load0) : activity.getString(R.string.load2);
                    if (position == 0) {
                        datas.clear();
                    }
                    datas.addAll(data);
                    updateDataView(null, null);
                }
                page++;
                isInRefresh = false;
            }
        });
    }

    public void updateData() {
        load(0);
    }

    public void updateDataView(ArrayList<News> datas, String footStr) {
        if (datas != null) {
            this.datas = datas;
        }
        if (footStr != null) {
            this.footStr = footStr;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

}
