package com.mayousheng.www.jsondecodepojo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseAdapter;
import com.mayousheng.www.jsondecodepojo.pojo.NewsPojo;
import com.mayousheng.www.jsondecodepojo.utils.ShowImageUtils;

import java.util.List;

/**
 * Created by marking on 2017/4/11.
 */

public class NewsAdapter extends BaseAdapter<NewsPojo> {

    public NewsAdapter(Context context, List<NewsPojo> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_news, parent, false);
            holder = new Holder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        NewsPojo newsPojo = getItem(position);
        if (newsPojo != null) {
            holder.title.setText(newsPojo.title);
            holder.desc.setText(newsPojo.description);
            holder.time.setText(newsPojo.ctime);
            holder.img.setTag(newsPojo.picUrl);
            new ShowImageUtils().loadImage(newsPojo.picUrl, holder.img);
        }
        return convertView;
    }

    private class Holder {
        private TextView title;
        private TextView desc;
        private TextView time;
        private ImageView img;
    }

}
