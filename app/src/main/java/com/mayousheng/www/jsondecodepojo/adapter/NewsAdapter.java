package com.mayousheng.www.jsondecodepojo.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.pojo.News;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.CacheUtils;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;
import com.shandao.www.baseholder.BaseHolder;
import com.shandao.www.baseholder.ViewDesc;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by marking on 2017/4/11.
 */

public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

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
    public static String[] URLS;
    private int start, end;
    private ShowImageUtils showImageUtils;
    private boolean isFirstIn = true;

    public NewsAdapter(Context context, EventBus eventBus, ListView listView) {
        this.context = context;
        this.eventBus = eventBus;
        CacheUtils.init(getDiskCacheDir(context, CacheUtils.CACHE_PATH), getAppVersion(context));//初始化CachUtils
        showImageUtils = new ShowImageUtils(listView);
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
                Log.e("-----1", "onFail message=" + message);
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
                    URLS = new String[datas.size()];
                    int i = 0;
                    for (News news : datas) {
                        URLS[i++] = news.picUrl;
                    }
                    if (eventBus != null) {
                        eventBus.refreshUI(position == 0);
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

    public void updateData(boolean refreshImg) {
        notifyDataSetChanged();
        if (refreshImg) {
            showImageUtils.loadImage(start, end);
        }
    }

    public interface EventBus {
        void refreshUI(boolean refreshImg);
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
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 1;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            showImageUtils.loadImage(start, end);
        } else {
            showImageUtils.stopAllTasks();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        start = firstVisibleItem;
        end = firstVisibleItem + visibleItemCount;
        if (isFirstIn && URLS != null) {
            isFirstIn = false;
            start = firstVisibleItem;
            end = firstVisibleItem + visibleItemCount;
            showImageUtils.loadImage(start, end);
        }
    }
}
