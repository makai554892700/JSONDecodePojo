package com.mayousheng.www.jsondecodepojo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.pojo.News;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;
import com.shandao.www.baseholder.BaseHolder;
import com.shandao.www.baseholder.ViewDesc;

import java.util.ArrayList;

/**
 * Created by marking on 2017/4/11.
 */

public class NewsAdapter extends BaseAdapter {

    private static final int TYPE_FOOT = 0;
    private static final int TYPE_BODY = 1;
    private static final int PAGE_NUM = 10;//每次请求数据条数

    private Context context;
    private String footStr;
    private ArrayList<News> datas = new ArrayList<News>();
    private boolean isInRefresh;
    private int page = 0;
    private boolean haveMore = true;
    private EventBus eventBus;

    public NewsAdapter(Context context, EventBus eventBus) {
        this.context = context;
        this.eventBus = eventBus;
        footStr = context.getString(R.string.load1);
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
                NewsHolder newsHolder;
                if (convertView == null) {
                    newsHolder = new NewsHolder();
                    convertView = newsHolder.newRootView(context, R.layout.item_news, parent);
                    convertView.setTag(newsHolder);
                } else {
                    newsHolder = (NewsHolder) convertView.getTag();
                }
                newsHolder.inViewBind((News) item);
                return convertView;
            case TYPE_FOOT:
                FootHolder footHolder;
                if (convertView == null) {
                    footHolder = new FootHolder();
                    convertView = footHolder.newRootView(context, R.layout.item_foot, parent);
                    convertView.setTag(footHolder);
                } else {
                    footHolder = (FootHolder) convertView.getTag();
                }
                footHolder.inViewBind((String) item);
                loadData(page);
                return convertView;
        }
        return null;
    }

    public void loadData(final int position) {
        if (isInRefresh) {
            return;
        }
        isInRefresh = true;
        footStr = context.getString(R.string.load1);
        InfoUtils.getNewsInfo("wxnew", position, PAGE_NUM, new ArrayListBack<News>() {
            @Override
            public void onFail(int status, String message) {
                isInRefresh = false;
            }

            @Override
            public void onResult(final ArrayList<News> data) {
                if (data != null) {
                    haveMore = data.size() == PAGE_NUM;
                    footStr = haveMore ? context.getString(R.string.load0) : context.getString(R.string.load2);
                    if (position == 0) {
                        datas.clear();
                    }
                    datas.addAll(data);
                    if (eventBus != null) {
                        eventBus.refreshUI();
                    }
                }
                page++;
                isInRefresh = false;
            }
        });
    }

    public void refreshData() {
        loadData(0);
    }

    public interface EventBus {
        void refreshUI();
    }

    private class FootHolder extends BaseHolder<String> {

        @ViewDesc(viewId = R.id.title)
        public TextView title;

        @Override
        public void inViewBind(String item) {
            title.setText(item);
        }

    }

    private class NewsHolder extends BaseHolder<News> {

        @ViewDesc(viewId = R.id.title)
        public TextView title;
        @ViewDesc(viewId = R.id.desc)
        public TextView desc;
        @ViewDesc(viewId = R.id.time)
        public TextView time;
        @ViewDesc(viewId = R.id.img)
        public ImageView img;

        @Override
        public void inViewBind(News item) {
            title.setText(item.title);
            desc.setText(item.description);
            time.setText(item.ctime);
            img.setImageResource(R.mipmap.ic_launcher);
            img.setTag(item.picUrl);
            new ShowImageUtils().loadImage(context, item.picUrl, img);
        }
    }
}
